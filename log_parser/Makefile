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
GROUP_PATH := $(shell echo ${GROUP_ID//./\/})
DOT:= .
DASH:= /
GROUP_PATH= $(subst $(DOT),$(DASH),$(GROUP_ID))

.PHONY: all
all: $(TARGET_ARTIFACT)

$(TARGET_TEST): $(SRCS)
	docker run -v $(CURDIR):/workdir/ ghcr.io/punchplatform/puncher:8.1-dev -v -T /workdir
	@mkdir -p $(TARGET_DIR)
	touch $(TARGET_TEST)

$(TMP_METADATA): $(METADATA) $(TARGET_METADATA) INFO
	@mkdir -p $(TMP_DIR)
	envsubst < $(TARGET_METADATA) > $(TMP_METADATA)

$(TMP_DATA): $(SRCS)
	@mkdir -p $(TMP_DIR)
	cd $(SRC_DIRS); rm -f $(TMP_DATA) ; zip -r $(TMP_DATA) *; cd ..; 
	
$(TARGET_ARTIFACT): $(TARGET_TEST) $(TMP_METADATA) $(TMP_DATA)
	cd $(TMP_DIR); rm -f $(TARGET_DIR)/*.zip  ; zip -r $(TARGET_ARTIFACT) *; cd -

.PHONY: package
package: $(TARGET_ARTIFACT)

.PHONY: test
test: ## Recursively executes all unit and log files tests you included in your repository.
	docker run -v $(CURDIR):/workdir/ ghcr.io/punchplatform/puncher:8.1-dev -T /workdir

.PHONY: clean ## Clean the repository
clean:
	rm -rf target

.PHONY: upload
upload:  ## Upload the generated parser artifact to the default kooker artifact server. Check the INFO file.
	curl -XPOST "$(ARTIFACT_SERVER_URL)/v1/artifacts/upload" -F artifact="@$(TARGET_ARTIFACT)" -F override=true

.PHONY: interactive
interactive: ## Start the puncher image in interactive mode for you to check the content of the puncher image.
	docker run -it --entrypoint /bin/bash -v $(CURDIR):/workdir/ ghcr.io/punchplatform/puncher:8.1-dev

.PHONY: local-install
local-install: ## Install the artifact locally. This will require the 'usr/share/punch/artifacts' root folder be created already.
	mkdir -p /usr/share/punch/artifacts/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}
	cp target/*.zip /usr/share/punch/artifacts/${GROUP_PATH}/${ARTIFACT_ID}/${VERSION}

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

