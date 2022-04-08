all: python
FORCE: ;

python: FORCE
	docker run -it --rm \
		-v ${PWD}/punchline_python.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-python:8.0-dev \
		/punchline.yaml