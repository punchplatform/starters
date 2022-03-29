all: build run
FORCE: ;

build: FORCE
	mvn clean install

run: FORCE
	docker run -it \
        -v ${PWD}/target/parsers-1.0.0.zip:/usr/share/punch/artifacts/com/mycompany/parsers/1.0.0/parsers-1.0.0.zip \
        -v ${PWD}/punchline.yaml:/data/punchline.yaml \
        --network=host \
        ghcr.io/punchplatform/punchline-java:8.0-dev \
        /data/punchline.yaml

apply: FORCE
	kubectl apply -f my_service.yaml
	curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/parsers-1.0.0-artefact.zip -F override=true
	kubectl apply -f punchline.yaml
	cat simulator.json | kubectl run -i simulator --image ghcr.io/punchplatform/simulator:8.0-dev -- -c - --host my-parser-input.default

logs: FORCE
	kubectl logs -f --tail -1 -l punchline-name=my-parser

delete: FORCE
	kubectl delete pod simulator
	kubectl delete -f my_service.yaml
	kubectl delete -f punchline.yaml