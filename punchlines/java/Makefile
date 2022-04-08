all: java
FORCE: ;

java: FORCE
	docker run -it --rm \
		-v ${PWD}/punchline_java.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml