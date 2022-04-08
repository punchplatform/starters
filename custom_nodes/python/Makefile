#!/usr/bin/env bash -ec -o pipefail

include INFO
include tools/INTERNAL
include tools/TUTORIAL

export

##@ Bare environment

.PHONY: artifact

artifact: clean metadata package ## Generate final archive with metadata and pex
	zip -j ${OUTPUT_ARCHIVE} ${OUTPUT_PEX_ARCHIVE} ${OUTPUT_METADATA_YML} 

.PHONY: metadata

metadata: prerequisites ## Generate metadata.yml resolved based on INFO
	envsubst < ${METADATA_TEMPLATE_FILE} > ${OUTPUT_METADATA_YML}

.PHONY: lint

lint: docker-clean ## Apply codestyle and lint code
	@docker build . \
		-t "${TOOL_IMG}" \
		-f tools/Dockerfile.tools \
		--build-arg "SOURCES=${SOURCES}" \
		--build-arg "VERSION=${VERSION}" \
		--build-arg "PYTHON_VERSION_TAG=${PYTHON_VERSION_TAG}" \
		--quiet
	@docker run --name "${CONTAINER_NAME}" "${TOOL_IMG}"
	@docker cp "${CONTAINER_NAME}":/${SOURCES} .
	@docker rm -f "${CONTAINER_NAME}"

.PHONY: clean

clean: clean-package clean-pyc clean-test ## Clean generated files in this repository
	rm -rf target
	rm -rf .venv
	rm -rf dist
	rm -rf .cache
	rm -rf .venv

##@ Dockerised environment

.PHONY: docker-build

docker-build: clean docker-clean ## Generate a zip archive of this application using docker
	@rm -rf target
	@mkdir -p target
	@docker build . \
		-t "${PEX_IMG}" \
		--build-arg "SOURCES=${SOURCES}" \
		--build-arg "VERSION=${VERSION}" \
		--build-arg "PYTHON_VERSION_TAG=${PYTHON_VERSION_TAG}"
	@docker run --name "${CONTAINER_NAME}" "${PEX_IMG}"
	@docker cp "${CONTAINER_NAME}":/starter/target .
	@docker rm -f "${CONTAINER_NAME}"

.PHONY: docker-poetry

docker-poetry: ## Enter a poetry shell to update poetry.lock and pyproject.toml
	docker build . -f tools/Dockerfile.poetry-shell -t "docker-poetry"
	docker rm -f docker-poetry &> /dev/null || true
	docker run --rm -it --privileged --name docker-poetry "docker-poetry" bash

.PHONY: docker-poetry-commit

docker-poetry-commit: ## (requires docker-poetry to be active) Commit poetry.lock and pyproject.toml to your local filesystem
	docker exec -it docker-poetry bash -c "cat /pyproject.toml" > pyproject.toml
	docker exec -it docker-poetry bash -c "cat /poetry.lock" > poetry.lock