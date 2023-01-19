#!/usr/bin/env bash -ec -o pipefail

.PHONY: run

run: ## Run punchline in docker
	docker run -it --rm \
		-v $(CURDIR)/punchline_python.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-python:8.1-dev \
		/punchline.yaml

.PHONY: apply

apply: ## Apply punchline to kube
	kubectl apply -f punchline_python.yaml

.PHONY: logs

logs: ## View logs of punchline in kube
	kubectl logs -f --tail -1 -l punchline-name=punchline-python

.PHONY: delete

delete: ## Delete punchline in kube
	kubectl delete -f punchline_python.yaml --ignore-not-found=true
