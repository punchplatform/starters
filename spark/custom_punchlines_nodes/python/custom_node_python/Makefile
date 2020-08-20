DIR=$(shell pwd)

ifeq (, $(shell which python3))
 $(error "No python3 installed, it is required. Make sure you also install python3 venv")
endif

help:
	@echo "clean                     - remove all build, test, coverage and Python artifacts"
	@echo "clean-pyc                 - remove Python file artifacts"
	@echo "clean-test                - remove test and coverage artifacts"
	@echo "inspect                   - linting and code formatter -> provide argument path=your_location"
	@echo "package                   - create a pex archive to dist folder with a given name; name=my_node-1.0.0.pex"
	@echo "update-dependencies       - requirements should be set in setup.py, in case new one is added; use this command to update your virtual environment"

.venv:
	$(info ************  CREATE PYTHON 3 .venv  VIRTUALENV  ************)
	@if [ ! -e "${DIR}/.venv/bin/activate" ] ; then python3 -m venv ${DIR}/.venv ; fi
	@. ${DIR}/.venv/bin/activate && pip install -U pip setuptools wheel flake8 black mypy

clean-pyc:
	$(info ************  CLEANING TEMPORARY FILES  ************)
	@-find ${DIR} -name '*.pyc' -exec rm -f {} +
	@-find ${DIR} -name '*.pyo' -exec rm -f {} +
	@-find ${DIR} -name '*~' -exec rm -f {} +
	@-find ${DIR} -name '__pycache__' -exec rm -fr {} +

clean-test:
	$(info ************  CLEANING TEST REPORTS  ************)
	@-rm -fr ${DIR}/.tox/
	@-rm -f ${DIR}/.coverage
	@-rm -fr ${DIR}/htmlcov/

clean: clean-pyc clean-test
	@$(info ************  CLEAN  ************)

flush: clean clean-pyc clean-test clean-package
	$(info ************  CLEANING PACKAGED DEPENDENCIES  ************)
	@rm -rf .venv

inspect: .venv
	$(info ************  INSPECT code for PEP and syntax errors   ************)
	@. ${DIR}/.venv/bin/activate && python -m flake8
	@. ${DIR}/.venv/bin/activate && python -m black $(path)

update-dependencies: .venv
	@. ${DIR}/.venv/bin/activate && pip install -e ${DIR}
	@. ${DIR}/.venv/bin/activate && pip install ${PUNCHPLATFORM_INSTALL_DIR}/lib/pyspark/wheel/*

package: .venv
	@. ${DIR}/.venv/bin/activate && pex . -o dist/$(name)


