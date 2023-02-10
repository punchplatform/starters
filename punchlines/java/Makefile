#!/usr/bin/env bash -ec -o pipefail

.PHONY: run

run: ## execute the punchline in docker
	docker run -it --rm \
		-v $(CURDIR)/punchline_java.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/punchline.yaml

.PHONY: apply

apply: ## Start (apply) the punchline into your kubernetes cluster
	kubectl apply -f punchline_java.yaml

.PHONY: logs

logs: ## View the kubernetes punchline logs
	kubectl logs -f --tail -1 -l punchline-name=punchline-java

.PHONY: delete

delete: ## Stop (delete) the punchline in kubernetes
	kubectl delete -f punchline_java.yaml --ignore-not-found=true

.PHONY: help
help:
	@awk 'BEGIN {FS = ":.*##"; printf "\033[36m\033[0m\n"} /^[0-9a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-28s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
	@echo
