all: pyspark
FORCE: ;

pyspark: FORCE
	docker run -it --rm \
		--entrypoint sparkctl-client \
		-v ${PWD}/punchline_pyspark.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-pyspark:8.0-dev \
		--job /punchline.yaml