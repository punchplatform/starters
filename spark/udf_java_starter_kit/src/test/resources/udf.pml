{
  job:
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
              truncate: true
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
