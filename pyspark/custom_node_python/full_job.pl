{
	job: [
		{
			type: complex_algorithm
			component: step1
			publish: [
				{
					stream: data
				}
			]
			settings: {
				param1: 100
			}
		}
		{
			type: python_show
			component: just_print_to_stdout
			subscribe: [
				{
					component: step1
					stream: data
				}
			]
			settings: {
			}
		}
	]
}