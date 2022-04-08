all: spark
FORCE: ;

spark: FORCE
	docker run -it --rm \
		--entrypoint sparkctl-client \
		-v ${PWD}/punchline_spark.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-spark:8.0.0 \
		--job /punchline.yaml