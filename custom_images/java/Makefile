#!/usr/bin/env bash -ec -o pipefail

IMG ?= punchline-starter:dev

.PHONY: build

build:
	docker build -t $(IMG) .

.PHONY: run

run:
	docker run -it $(IMG)

.PHONY: apply

apply:
	kubectl apply -f deploy/deployment.yaml

.PHONY: delete

delete:
	kubectl delete -f deploy/deployment.yaml
