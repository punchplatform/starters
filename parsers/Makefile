#!/usr/bin/env bash -ec -o pipefail

include INFO

export

##@ Build artifact

.PHONY: prepare

prepare: ## Prepare build
	@mkdir -p $(OUTPUT_DIR)

.PHONY: build

build: prepare ## Build artifact
	# Zip parser resources
	zip -r $(OUTPUT_FILE) sample
	# Render metadata.yml
	envsubst < metadata.yml > $(OUTPUT_METADATA)
	# Zip file and metadata
	zip -j $(OUTPUT_ARCHIVE) $(OUTPUT_FILE) $(OUTPUT_METADATA)

.PHONY: test

test: ## Test parser using the Puncher
	docker run -v $(CURDIR):/workdir/ ghcr.io/punchplatform/puncher:8.0-dev -T /workdir

##@ Clean

.PHONY: clean

clean: ## Clean this repository
	rm -rf $(OUTPUT_DIR)

##@ Execute artifact

.PHONY: run

run: ## Test your artifact with java engine using docker
	docker run --rm -it \
		-v $(OUTPUT_FILE):/usr/share/punch/$(DEPENDENCY_LOCATION) \
		-v $(CURDIR)/test/punchline.yaml:/data/punchline.yaml \
		$(PUNCHLINE_IMG) /data/punchline.yaml

.PHONY: upload

upload: ## Upload artifact to artifact server
	curl -XPOST "$(ARTIFACT_SERVER_UPLOAD_URL)" -F artifact="@$(OUTPUT_ARCHIVE)" -F override=true

.PHONY: apply

apply: ## Apply test punchline to kube
	kubectl apply -f test/punchline.yaml

.PHONY: logs

logs: ## View logs of test punchline
	kubectl logs -f --tail -1 -l punchline-name=punch-parsers

.PHONY: delete

delete: ## Delete test punchline
	kubectl delete -f test/punchline.yaml --ignore-not-found=true

.PHONY: help

help:
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
