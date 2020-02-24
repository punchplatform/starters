# PunchPlatform UDFs Integration

This repository is intended to be used as a starting point for developing your own Spark UDF.
These UDF can be used inside our PunchPlatform Sql Node

Note, although the UDF is coded using java, you should be able to use it within the pyspark provided by punch by installing a UDF jar with `install-dependencies` command.

## Prerequisite

-   Basic knowledge on Spark DataTypes/Scala DataTypes
-   Basic knowledge on Spark API: UDF

## Demonstration

The UDF function in this repository takes as input a String which follows the following pattern:

>   "[1.0, 2, 5.5, 8.6]"

this UDF produces as output:

> WrappedArray(1.0, 2, 5.5, 8.6)

## Installation

#### Making your jar available to PunchPlatform environment (with punchpkg)

```sh
YOUR_STANDALONE_VERSION=6.0.0
SPARK_VERSION=2.4.3
STARTER_KIT_VERSION=1.0

# at the same level of this README.md
mvn clean install

punchpkg spark install-dependencies $(pwd)/target/punchplatform-udf-starter-kit-${STARTER_KIT_VERSION}-jar-with-dependencies.jar
```

#### Making your jar available to PunchPlatform environment (without punchpkg)

```sh
YOUR_STANDALONE_VERSION=6.0.0
SPARK_VERSION=2.4.3
STARTER_KIT_VERSION=1.0

# at the same level of this README.md
mvn clean install

mkdir -p $PUNCHPLATFORM_CONF_DIR/../external/spark-${SPARK_VERSION}-bin-hadoop2.7/punchplatform/analytics/job/custom/
cp target/punchplatform-udf-starter-kit-${STARTER_KIT_VERSION}-jar-with-dependencies.jar $PUNCHPLATFORM_CONF_DIR/../external/spark-${SPARK_VERSION}-bin-hadoop2.7/punchplatform/analytics/job/custom/
```

#### Using the UDF function within a pipeline

1.  Start your standalone
    ```sh
    punchplatform-standalone.sh --start
    ```
2.  You should run with `punchctl` our `apache_httpd`
    ```sh
    punchctl start --channel apache_httpd
    ````
3.  Use our punchplatform-log-injector.sh to fill your elasticsearch index
    ```sh
    punchplatform-log-injector.sh -c $PUNCHPLATFORM_CONF_DIR/resources/injector/mytenant/apache_httpd_injector.json
    ```
4.  Go to src/test/resources
    ```sh
    cd src/test/resources
    ```

    We are going to execute with `punchlinectl` the below configuration pipeline.

    ```java
    {
    tenant: default
    channel: default
    version: "6.0"
    runtime: spark
    dag:
    [
        {
            type: elastic_batch_input
            component: input
            settings: 
            {
                cluster_name: es_search
                nodes: [
                    localhost
                ]
                index: mytenant-events-*
                output_columns : [
                    {
                    field: init.host.ip
                    alias : init_host_ip
                    type : string
                    }
                    {
                    field: session.out.byte
                    alias : session_out_byte
                    type : integer
                    }
                ]
            }
            publish: [
                {
                    stream: data
                }
            ]
        }
        {
            type: file_model_input
            component: model_loader
            settings: 
            {
                file_path: model.bin
            }
            publish: [ 
                { 
                stream: model
                } 
            ]
            subscribe: []
        }
        {
            type: mllib_model
            component: executor
            settings: {}
            publish: [
                { 
                stream: data 
                }
            ]
            subscribe: [
            {
                component: input
                stream: data
                alias: input_data
            }
            {
                component: model_loader
                stream: model
                alias: model
            }
            ]
        }
        {
            type: sql
            component: sql
            settings: {
                register_udf: [
                    {
                    function_name: myStrToArrayStr
                    class_name: org.thales.punch.udf.starter.kit.StrToArrayString
                    schema_ddl: ARRAY<STRING>
                    }
                ]
                statement_list: [
                    {
                    output_table_name: extract_analytics_json
                    statement: SELECT json_tuple(output,'analytics') AS output_analytics, init_host_ip, session_out_byte, init_host_ip_tokenized, init_host_ip_hashed, full_vector, features AS source_features, prediction AS source_prediction FROM executor_data
                    }
                    {
                    output_table_name: features_prediction_as_string
                    statement: SELECT json_tuple(output_analytics, 'features', 'prediction'), init_host_ip, init_host_ip_tokenized, session_out_byte, init_host_ip_hashed, source_features, source_prediction FROM extract_analytics_json
                    }
                    {
                    output_table_name: cast_columns_to_proper_types
                    statement: SELECT myStrToArrayStr(c0) AS output_features, CAST(c1 AS INTEGER) AS output_prediction, init_host_ip, init_host_ip_tokenized, session_out_byte, init_host_ip_hashed, source_features, source_prediction FROM features_prediction_as_string
                    }
                ]
            }
            subscribe: [
                {
                    component: executor
                    stream: data
                }
            ]
            publish: [
                {
                    stream: extract_analytics_json
                }
                {
                    stream: features_prediction_as_string
                }
                {
                    stream: cast_columns_to_proper_types
                }
            ]
        }
        {
            type: show
            component: show
            settings: {
                show_schema: true
                truncate: false
            }
            subscribe: [
                {
                    component: sql
                    stream: cast_columns_to_proper_types
                }
            ]
        }
    ]
    spark_settings:
        {
        spark.files: ./model.bin
        spark.executor.memory: 1g
        spark.executor.cores: "2"
        spark.executor.instances: "2"
        }
    }
    ```
5.  let's execute:
    ```sh
    punchlinectl start -p udf.pml -v
    ```
    Expected output:
    ```python
    Show node result: sql_cast_columns_to_proper_types

    | output_features | output_prediction | init_host_ip   | init_host_ip_tokenized | session_out_byte | init_host_ip_hashed | source_features | source_prediction |

    | WrappedArr...   | 0                 | 128.186.66.249 | WrappedArr...          | 7199             | (1000,[233...       | [0.0,0.0,0...   | 0                 |
    | WrappedArr...   | 0                 | 189.216.177.97 | WrappedArr...          | 15068            | (1000,[240...       | [0.0,0.0,0...   | 0                 |
    | WrappedArr...   | 0                 | 189.7.247.162  | WrappedArr...          | 3065             | (1000,[331...       | [0.0,0.0,0...   | 0                 |

    root
    |-- output_features: array (nullable = true)
    |    |-- element: string (containsNull = true)
    |-- output_prediction: integer (nullable = true)
    |-- init_host_ip: string (nullable = true)
    |-- init_host_ip_tokenized: array (nullable = true)
    |    |-- element: string (containsNull = true)
    |-- session_out_byte: integer (nullable = true)
    |-- init_host_ip_hashed: vector (nullable = true)
    |-- source_features: vector (nullable = true)
    |-- source_prediction: integer (nullable = false)

    [
    {
        "name": "sql_cast_columns_to_proper_types",
        "title": "SHOW"
    }
    ]
    ```
