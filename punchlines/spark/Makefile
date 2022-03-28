all: spark
FORCE: ;

spark: FORCE
	docker run -it --rm \
		-v ${PWD}/punchline_spark.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-spark:8.0-dev \
		/punchline.yaml