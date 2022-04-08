#!/usr/bin/env bash -ec -o pipefail

include INFO
include tools/INTERNAL
include tools/TUTORIAL

export

##@ Bare environment

.PHONY: metadata

metadata: prerequisites ## Generate metadata.yml resolved based on INFO
	envsubst < ${METADATA_TEMPLATE_FILE} > ${OUTPUT_METADATA_YML}

.PHONY: artifact

artifact: build metadata ## Generate final archive with metadata and pex
	zip -j ${OUTPUT_ARCHIVE} ${OUTPUT_JAR_ARCHIVE} ${OUTPUT_METADATA_YML}

.PHONY: clean

clean: ## Clean this repository
	$(MVN) -Drevision=${VERSION} clean

##@ Dockerised environment

.PHONY: docker-build

docker-build: clean docker-clean ## Generate a zip archive of this application using docker
	@rm -rf target
	@mkdir -p target
	@docker build . \
		-t "${JAR_IMG}" \
		--build-arg "SOURCES=${SOURCES}" \
		--build-arg "VERSION=${VERSION}" \
		--build-arg "JAVA_VERSION_TAG=${JAVA_VERSION_TAG}"
	@docker run \
		-v ${MAVEN_LOCAL_REPOSITORY}:/mvnhome \
		--name "${CONTAINER_NAME}" \
		"${JAR_IMG}" \
		bash -c "make artifact MAVEN_LOCAL_REPOSITORY=/mvnhome"
	@docker cp "${CONTAINER_NAME}":/starter/target . > /dev/null 2>&1
	@docker rm -f "${CONTAINER_NAME}" > /dev/null 2>&1
