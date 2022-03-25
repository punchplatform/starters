all: java
FORCE: ;

java: FORCE
	docker run \
		-v ${PWD}/punchline_java.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml