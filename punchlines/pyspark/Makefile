#!/usr/bin/env bash -ec -o pipefail

.PHONY: run

run: ## Run punchline in docker
	docker run -it --rm \
		--entrypoint sparkctl-client \
		-v $(CURDIR)/punchline_pyspark.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-pyspark:8.0-dev \
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
