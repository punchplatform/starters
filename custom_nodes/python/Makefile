EXEC_NAME=custom_node.pex
DIR=$(shell pwd)

.PHONY: all package clean-package clean-pyc clean-test clean

all: clean package

venv:
	$(info ************  CREATE PYTHON 3 .venv  VIRTUALENV  ************)
	@if [ ! -e "${DIR}/.venv/bin/activate" ] ; then python3 -m venv ${DIR}/.venv ; fi
	@. ${DIR}/.venv/bin/activate && python3 -m pip install -U setuptools'<50' wheel pip
	@. ${DIR}/.venv/bin/activate && python3 -m pip install -U poetry pex
	@. ${DIR}/.venv/bin/activate && python3 -m poetry run pip install -U pip setuptools'<50' wheel
	@. ${DIR}/.venv/bin/activate python3 -m poetry install


package: venv
	@. ${DIR}/.venv/bin/activate && poetry build
	@. ${DIR}/.venv/bin/activate && python3 -m pex . -o $(BUILD_PATH)/$(name)


clean-package:
	$(info ************  CLEANING DISTRIBUTION  ************)
	rm -rf dist
	rm -rf target
	-rm -rf target/.distrib-built

clean-pyc:
	$(info ************  CLEANING TEMPORARY FILES  ************)
	-find . -name '*.pyc' -exec rm -f {} +
	-find . -name '*.pyo' -exec rm -f {} +
	-find . -name '*~' -exec rm -f {} +
	-find . -name '__pycache__' -exec rm -fr {} +

clean-test:
	$(info ************  CLEANING TEST REPORTS  ************)
	rm -fr .tox/
	rm -fr .coverage
	rm -fr htmlcov/
	rm -rf target/.distrib-tested

clean: clean-package clean-pyc clean-test
	$(info ************  CLEAN  ************)
	rm -rf target
	rm -rf .venv
	rm -rf dist
	rm -rf .cache
	rm -rf .venv


build:
	mvn clean install

run:
	docker run --rm -it \
        -v ${PWD}/target/punchline-python-starter-kit-1.0.0.pex:/usr/share/punch/extlib/punchline-python-starter-kit-1.0.0.pex \
        -v ${PWD}/punchline.yaml:/data/punchline.yaml \
        ghcr.io/punchplatform/punchline-python:8.0-dev \
        /data/punchline.yaml

apply:
	curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-python-starter-kit-1.0.0-artifact.zip -F override=true
	kubectl apply -f punchline.yaml

logs:
	kubectl logs -f --tail -1 -l punchline-name=custom-nodes-python

delete:
	kubectl delete -f punchline.yaml