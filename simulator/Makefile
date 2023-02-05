#!/usr/bin/env bash -ec -o pipefail

.PHONY: testit
testit: ## execute the simple print punchline in docker
	docker run -p 8880:8880 -it --rm \
		-v $(CURDIR)/testit/punchline_testit.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/punchline.yaml

.PHONY: apply
logcollection: ## Start (apply) the logcollection punchlines to your kubernetes cluster
	kubectl apply -f logcollection

.PHONY: delete
delete: ## Stop (delete) the logcollection punchlines in kubernetes
	kubectl delete -f logcollection --ignore-not-found=true

.PHONY: help
help:
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
	@echo
