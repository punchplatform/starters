{
  tenant_name:"my_tenant"
  runtime_id:"292001762275371029"
  job: [
        {
            type: dataset_generator
            component: input
            publish: [
                {
                    stream: data
                }
            ]
            settings: {
                input_data: [
                    {
                        Message: "[1, 2, 3]"
                    }
                    {
                        Message: "[1, 2, 3, 5]"
                    }
                    {
                        Message: "[1, 2, 3, 99, 5.0]"
                    }
                    {
                        Message: "[0.3, 2, 3]"
                    }
                ]
            }
        }
        {
            type: sql
            component: sql
            settings: {
                register_udf: [
                    {
                        function_name: myOwnFunc
                        class_name: org.thales.punch.pl.plugins.udf.functions.KmeansSqdist
                        schema_ddl: Double
                    }
                    {
                        function_name: myOwnFunc2
                        function_definition: udf_example.udf0_example.test_random
                        schema_ddl: Integer
                    }
                ]
                statement_list: [
                    {
                        output_table_name: data
                        statement: SELECT myOwnFunc2(), punch_str_to_array_double(Message) FROM input_data
                    }
                    {
                        output_table_name: other
                        statement: SELECT 1
                    }
                ]
            }
            publish: [
                {
                    stream: data
                }
            ]
            subscribe: [
                {
                    component: input
                    stream: data
                }
            ]
        }
        {
            type: show
            component: show
            subscribe: [
                {
                    component: sql
                    stream: data
                }
                {
                    component: sql
                    stream: other
                }
            ]
        }
    ]
    settings: {
        spark.additional.pex: udf.pex
    }
}