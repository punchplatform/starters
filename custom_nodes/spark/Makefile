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

.PHONY: lint

lint: google-formatter ## Format all java files to google code style: ignores ./build, .mvn directories: use GOOGLE_FORMATTER_ARGS=--dry-run to list files to lint
	-JAVA_HOME=${JAVA_HOME} \
	 FORMATTER_JAR_PATH=${FORMATTER_JAR_PATH} \
		find . -name '*.java' \
			! -path "./build/*" \
			! -path "./.mvn/*" \
			! -path "./metadata/*" \
			! -path "./tools/*" \
			-exec $(GOOGLE_FORMATTER) ${GOOGLE_FORMATTER_ARGS} {} +

.PHONY: clean

clean: ## Clean this repository
	$(MVN) -Drevision=${VERSION} clean
	rm -rf ${OUTPUT_DIR} ${BUILD_DIR}

##@ Dockerised environment

.PHONY: docker-build

docker-build: clean docker-clean ## Generate a zip archive of this application using docker
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