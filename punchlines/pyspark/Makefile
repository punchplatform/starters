#!/usr/bin/env bash -ec -o pipefail

.PHONY: run

run: ## Run punchline in docker
	docker run -it --rm \
		--entrypoint sparkctl-client \
		-v $(CURDIR)/punchline_pyspark.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-pyspark:8.1-dev \
		--job /punchline.yaml

.PHONY: apply
apply: ## Apply punchline to kube
	kubectl apply -f punchline_pyspark.yaml

.PHONY: logs
logs: ## View logs of punchline in kube
	kubectl logs -f --tail -1 -l punchline-name=punchline-pyspark

.PHONY: delete
delete: ## Delete punchline in kube
	kubectl delete -f punchline_pyspark.yaml --ignore-not-found=true

.PHONY: help
help:
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
	@echo
