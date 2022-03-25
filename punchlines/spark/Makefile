all: spark
FORCE: ;

spark: FORCE
	docker run \
		-v ${PWD}/punchline_spark.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-spark:8.0-dev \
		/punchline.yaml