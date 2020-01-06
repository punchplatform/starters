{
    job: [
        {
            type: dataset_generator
            component: input
            settings: {
                input_data: [
                    {
                        from: "{{ from }}"
                        to: "{{ to }}"
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
            type: show
            component: show
            settings: {
             truncate: false
            }
            subscribe: [
                {
                    component: input
                    stream: data
                }
            ]
        }
    ]
}