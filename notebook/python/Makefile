#!/usr/bin/env bash -ec -o pipefail

include INFO

export

##@ Build pex artifact

prepare: ## Prepare environnement to build pex
	@mkdir -p $(OUTPUT_DIR)
	@if [ ! -e "$(VENV)/bin/activate" ] ; then python3 -m venv $(VENV) ; fi
	@$(PIP) install -U setuptools'<50' wheel pip
	@$(PIP) install -U pex

build-pex: prepare ## Build pex artifact
	# Build pex file
	@$(PEX) -D $(PEX_DIR) -r $(PEX_REQUIREMENTS) -o $(PEX_OUTPUT_FILE)
	# Render metadata.yml
	envsubst < $(PEX_OUTPUT_METADATA).template > $(PEX_OUTPUT_METADATA)
	# Zip file and metadata
	zip -j $(PEX_OUTPUT_ARCHIVE) $(PEX_OUTPUT_FILE) $(PEX_OUTPUT_METADATA)

upload-pex: ## Upload pex artifact to artifact server
	curl -XPOST "$(ARTIFACT_SERVER_UPLOAD_URL)" -F artifact="@$(PEX_OUTPUT_ARCHIVE)" -F override=true

##@ Build notebook artifact

build-notebook: ## Build notebook artifact
	# Render metadata.yml
	envsubst < $(NOTEBOOK_OUTPUT_METADATA).template > $(NOTEBOOK_OUTPUT_METADATA)
	cp $(NOTEBOOK_FILE) $(NOTEBOOK_OUTPUT_FILE)
	# Zip file and metadata
	@zip -j $(NOTEBOOK_OUTPUT_ARCHIVE) $(NOTEBOOK_OUTPUT_FILE) $(NOTEBOOK_OUTPUT_METADATA)

upload-notebook: ## Upload notebook artifact to artifact server
	curl -XPOST "$(ARTIFACT_SERVER_UPLOAD_URL)" -F artifact="@$(NOTEBOOK_OUTPUT_ARCHIVE)" -F override=true

##@ Create punchline.yaml from template

punchline: 
	@cat test/punchline.yaml.template | \
	 sed "s|PUNCHLINE_IMG|$(PUNCHLINE_IMG)|g" | \
	 sed "s|RESOURCECTL_IMG|$(RESOURCECTL_IMG)|g" | \
	 sed "s|GROUP_ID|$(GROUP_ID)|g" | \
	 sed "s|NOTEBOOK_ARTIFACT_ID|$(NOTEBOOK_ARTIFACT_ID)|g" | \
	 sed "s|NOTEBOOK_VERSION|$(NOTEBOOK_VERSION)|g" | \
	 sed "s|PEX_ARTIFACT_ID|$(PEX_ARTIFACT_ID)|g" | \
	 sed "s|PEX_VERSION|$(PEX_VERSION)|g" \
	 > test/punchline.yaml


##@ Execute punchline Docker

run: punchline ## Test your artifact with java engine using docker
	docker run --rm -it \
		-v $(PEX_OUTPUT_FILE):/usr/share/punch/$(PEX_DEPENDENCY_LOCATION) \
		-v $(NOTEBOOK_OUTPUT_FILE):/usr/share/punch/$(NOTEBOOK_DEPENDENCY_LOCATION) \
		-v $(CURDIR)/test/punchline.yaml:/data/punchline.yaml \
		$(PUNCHLINE_IMG) /data/punchline.yaml

##@ Execute punchline Kube

apply: punchline## Apply test punchline to kube
	kubectl apply -f test/punchline.yaml

logs: ## View logs of test punchline
	kubectl logs -f --tail -1 -l punchline-name=punchline-python-notebook


delete: ## Delete test punchline
	kubectl delete -f test/punchline.yaml --ignore-not-found=true

##@ Clean

clean: punchline ## Clean this repository
	rm -rf $(OUTPUT_DIR) $(VENV) .venv .cache 
	rm -f $(PEX_OUTPUT_FILE) $(PEX_OUTPUT_METADATA) $(NOTEBOOK_OUTPUT_FILE) $(NOTEBOOK_OUTPUT_METADATA)
	-find . -name '*.pyc' -exec rm -f {} +
	-find . -name '*.pyo' -exec rm -f {} +
	-find . -name '*~' -exec rm -f {} +
	-find . -name '__pycache__' -exec rm -fr {} +

help:
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
