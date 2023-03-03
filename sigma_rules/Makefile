include INFO
export

TARGET_DIR := $(CURDIR)/target
TMP_DIR := $(TARGET_DIR)/tmp
TMP_METADATA = $(TMP_DIR)/metadata.yml
TMP_DATA = $(TMP_DIR)/$(ARTIFACT_ID)-$(VERSION).zip
SRC_DIRS := $(CURDIR)/src/main
TARGET_ARTIFACT = $(TARGET_DIR)/$(ARTIFACT_ID)-$(VERSION)-artifact.zip
TARGET_METADATA := metadata/metadata.yml
SRCS := $(shell find $(SRC_DIRS) -type f)
TARGET_TEST := $(TARGET_DIR)/.tested
DOT:= .
DASH:= /
GROUP_PATH= $(subst $(DOT),$(DASH),$(GROUP_ID))
DATA_DIR = $(TARGET_DIR)/data

.PHONY: all
all: clean package

.PHONY: clean
clean: ## Clean the repository
	@rm -rf $(TARGET_DIR)
	@echo Working directory successfully cleaned.

## Prepare artifact metadata
$(TMP_METADATA): $(METADATA) INFO
	@mkdir -p $(TMP_DIR)
	@envsubst < $(TARGET_METADATA) > $(TMP_METADATA)

## Convert sigmarule to punchrule
$(DATA_DIR):
	@mkdir -p $(DATA_DIR)
	@docker run \
		-v $(SRC_DIRS):/rules/input \
		-v $(DATA_DIR):/rules/output \
		--user=root \
		$(IMG_SIGMARULE_CONVERTER) \
		--input_folder /rules/input/ --output_folder /rules/output/

## Prepare artifact data
$(TMP_DATA): $(DATA_DIR)
	@mkdir -p $(TMP_DIR)
	@cd $(DATA_DIR); zip -qq -r $(TMP_DATA) *; cd - > /dev/null

.PHONY: package
package: $(TMP_METADATA) $(TMP_DATA) ## Package metadata and data into an artifact

	@cd $(TMP_DIR); rm -f $(TARGET_ARTIFACT) ; zip -qq -r $(TARGET_ARTIFACT) *; cd - > /dev/null
	@echo Artifact successfully created.

.PHONY: upload
upload:  ## Upload the generated parser artifact to the default kooker artifact server. Check the INFO file.
	@curl -XPOST "$(ARTIFACT_SERVER_URL)/v1/artifacts/upload" -F artifact="@$(TARGET_ARTIFACT)" -F override=true
	@echo Artifact successfully uploaded.

.PHONY: local-install
local-install: ## Install the artifact locally.
	@mkdir -p ~/.m2/repository/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}
	@cp $(TMP_DATA) ~/.m2/repository/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}
	@echo Artifact successfully installed locally.

.PHONY: version 
version: ## Get artifact version
	@echo $(VERSION)

.PHONY: help
help:
	@echo Punch Parser Artifact Makefile help
	@echo
	@echo Simply type in \'make\' to build the artifact. The additional helper rules described below 
	@echo are also available to simplify day to day development.  
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
