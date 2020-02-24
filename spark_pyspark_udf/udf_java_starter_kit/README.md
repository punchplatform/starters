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

#### Using the UDF function hello world

#### before using our custom udf function 

Let's run the configuration below:

```hjson
{
    tenant: default
    channel: default
    version: "6.0"
    runtime: spark
    dag: [
        {
            type: dataset_generator
            component: dummy
            settings: {
                input_data: [
                    { 
                        field1: "[1, 2, 3, 4]"
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
            type: sql
            component: processor
            settings: {
                register_udf: [
                    {
                        class_name: org.thales.punch.udf.starter.kit.StrToArrayString
                        function_name: punch_convertor
                        schema_ddl: ARRAY<STRING>
                    }
                ]
                statement_list: [
                    {
                        output_table_name: processed
                        statement: SELECT punch_convertor(field1) from dummy_data
                    }
                ]
            }
            subscribe: [
                {
                    stream: data
                    component: dummy
                }
            ]
            publish: [
                {
                    stream: processed
                }
            ]
        }
        {
            type: show
            component: stdout
            settings: {
                truncate: false
            }
            subscribe: [
                {
                    component: processor
                    stream: processed
                }
            ]
        }
    ]
}
```

Output result:

```sh
# note: $(pwd) is root directory of this readme
punchlinectl start -p $(pwd)/before_udf_helloworld.punchline

--[[
__________                    .__    .____    .__               
\______   \__ __  ____   ____ |  |__ |    |   |__| ____   ____  
 |     ___/  |  \/    \_/ ___\|  |  \|    |   |  |/    \_/ __ \ 
 |    |   |  |  /   |  \  \___|   Y  \    |___|  |   |  \  ___/ 
 |____|   |____/|___|  /\___  >___|  /_______ \__|___|  /\___  >
                     \/     \/     \/        \/       \/     \/ 
--]]_______ _________  _ 
[__ |__]|__||__/|_/  
___]|   |  ||  \| \_ 
                   
Show node result: processor_processed

| field1       |

| [1, 2, 3, 4] |

root
 |-- field1: string (nullable = true)

[
  {
    "name": "processor_processed",
    "title": "SHOW"
  }
]
```

Now let's build this package

```sh
mvn clean install
```

Afterwards we're going to install our jar to the correct directory with the help of `punchpkg`

```sh
# note $(pwd) is root directory of this readme
punchpkg spark install-dependencies $(pwd)/target/punchplatform-udf-starter-kit-1.0-jar-with-dependencies.jar

# let's check if our jar is properly installed
punchpkg spark list-dependencies 
{
    "custom_jars": [
        "punchplatform-udf-starter-kit-1.0-jar-with-dependencies.jar"
    ],
    "main_jars": [
        "punchplatform-analytics-uber-6.0.0-SNAPSHOT-jar-with-dependencies.jar",
        "punchplatform-analytics-plugins-udf-6.0.0-SNAPSHOT-jar-with-dependencies.jar"
    ]
}
```

```hjson
# extract of what has changed in after_udf_helloworld.punchline compared to before_udf_helloworld.punchline

{
    type: sql
    component: processor
    settings: {
        register_udf: [
            {
                class_name: org.thales.punch.udf.starter.kit.StrToArrayString
                function_name: punch_convertor
                schema_ddl: ARRAY<STRING>
            }
        ]
        statement_list: [
            {
                output_table_name: processed
                statement: SELECT punch_convertor(field1) from dummy_data
            }
        ]
    }
...
}
```

let's run the new configuration

```sh
punchlinectl start -p after_udf_helloworld.punchline 
--[[
__________                    .__    .____    .__               
\______   \__ __  ____   ____ |  |__ |    |   |__| ____   ____  
 |     ___/  |  \/    \_/ ___\|  |  \|    |   |  |/    \_/ __ \ 
 |    |   |  |  /   |  \  \___|   Y  \    |___|  |   |  \  ___/ 
 |____|   |____/|___|  /\___  >___|  /_______ \__|___|  /\___  >
                     \/     \/     \/        \/       \/     \/ 
--]]_______ _________  _ 
[__ |__]|__||__/|_/  
___]|   |  ||  \| \_ 
                   
Show node result: processor_processed

| UDF:punch_convertor(field1) |

| WrappedArray(1, 2, 3, 4)    |

root
 |-- UDF:punch_convertor(field1): array (nullable = true)
 |    |-- element: string (containsNull = true)

[
  {
    "name": "processor_processed",
    "title": "SHOW"
  }
]
```

# Conclusion

As you can see, we developped an UDF function with the ability to translate a string representing an array to an array of string !

Much more can be done, for instance, integrating parsing logic as UDFs!

