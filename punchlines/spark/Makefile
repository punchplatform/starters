#!/usr/bin/env bash -ec -o pipefail

PUNCHLINE?=punchline_spark.yaml

.PHONY: run

run: ## Run punchline in docker
	docker run -it --rm \
		--entrypoint sparkctl-client \
		-v $(CURDIR)/$(PUNCHLINE):/punchline.yaml \
		ghcr.io/punchplatform/punchline-spark:8.1-dev \
		--job /punchline.yaml

.PHONY: apply

apply: ## Apply punchline to kube
	kubectl apply -f $(PUNCHLINE)

.PHONY: logs

logs: ## View logs of punchline in kube
	kubectl logs -f --tail -1 -l punchline-name=punchline-spark

.PHONY: delete

delete: ## Delete punchline in kube
	kubectl delete -f $(PUNCHLINE) --ignore-not-found=true
