#!/usr/bin/env bash -ec -o pipefail

include INFO

export

##@ Build artifact

.PHONY: prepare

prepare: ## Prepare build
	@mkdir -p $(OUTPUT_DIR)
	# Install poetry in .venv-poetry
	@if [ ! -e "$(VENV)/bin/activate" ] ; then python3 -m venv $(VENV) ; fi
	@$(PIP) install -U setuptools'<50' wheel pip
	@$(PIP) install -U poetry pex
	@$(POETRY) run pip install -U pip setuptools'<50' wheel
	# Install and download custom node dependencies in .venv
	@$(POETRY) update
	@$(POETRY) install

.PHONY: build

build: prepare ## Build artifact
	# Build custom node using poetry and pex
	@$(POETRY) export --without-hashes -o $(OUTPUT_DIR)/requirements.txt
	@$(POETRY) build
	@mkdir -p dist/src
	@cp -r custom_nodes dist/src/
	@$(PEX) -D dist/src -r $(OUTPUT_DIR)/requirements.txt -o $(OUTPUT_FILE)
	# Render metadata.yml
	envsubst < metadata.yml > $(OUTPUT_METADATA)
	# Zip file and metadata
	zip -j $(OUTPUT_ARCHIVE) $(OUTPUT_FILE) $(OUTPUT_METADATA)

.PHONY: docker-build

docker-build: ## Build artifact in a Docker environment
	@docker build . -t "$(BUILD_IMG)" --build-arg "VERSION=$(VERSION)"
	@docker run --name "$(BUILD_CONTAINER)" "$(BUILD_IMG)"
	@docker cp "$(BUILD_CONTAINER)":/starter/target .
	@docker rm -f "$(BUILD_CONTAINER)"

##@ Clean

.PHONY: clean

clean: ## Clean this repository
	rm -rf $(OUTPUT_DIR) $(VENV) .venv dist .cache
	@docker rm -f "$(BUILD_CONTAINER)"
	-find . -name '*.pyc' -exec rm -f {} +
	-find . -name '*.pyo' -exec rm -f {} +
	-find . -name '*~' -exec rm -f {} +
	-find . -name '__pycache__' -exec rm -fr {} +

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
	kubectl logs -f --tail -1 -l punchline-name=custom-nodes-python

.PHONY: delete

delete: ## Delete test punchline
	kubectl delete -f test/punchline.yaml --ignore-not-found=true

.PHONY: help

help:
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
