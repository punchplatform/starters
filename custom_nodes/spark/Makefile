all: build run
FORCE: ;

build: FORCE
	mvn clean install

run: FORCE
	docker run --rm -it --entrypoint sparkctl-client \
		-v ${PWD}/target/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar:/usr/share/punch/extlib/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar \
		-v ${PWD}/punchline.yaml:/data/punchline.yaml \
		ghcr.io/punchplatform/punchline-spark:8.0-dev \
		--job /data/punchline.yaml

apply: FORCE
	curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-spark-starter-kit-1.0.0-artifact.zip -F override=true
	kubectl apply -f punchline.yaml

logs: FORCE
	kubectl logs -f --tail -1 -l punchline-name=custom-nodes-spark

delete: FORCE
	kubectl delete -f punchline.yaml