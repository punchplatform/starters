all: build run
FORCE: ;

build: FORCE
	docker build -t punchline-starter:dev .

run: FORCE
	docker run -it punchline-starter:dev

apply: FORCE
	kubectl apply -f deploy/deployment.yaml
