all: fileresource arkoon geoip stdgroks customgroks generator
FORCE: ;

fileresource: FORCE
	docker run \
		-v ${PWD}/fileresource/punchlet.punch:/punchlet.punch \
		-v ${PWD}/fileresource/punchline.yaml:/punchline.yaml \
		-v ${PWD}/fileresource/color_codes.json:/color_codes.json \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

arkoon: FORCE
	docker run \
		-v ${PWD}/arkoon/punchlet.punch:/punchlet.punch \
		-v ${PWD}/arkoon/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml
geoip: FORCE
	docker run \
		-v ${PWD}/geoip/punchlet.punch:/punchlet.punch \
		-v ${PWD}/geoip/punchline.yaml:/punchline.yaml \
 		-v /Users/dimi/.m2/repository/com/github/punchplatform/punch-punchlang-resources/8.0-dev/punch-punchlang-resources-8.0-dev.zip:/opt/punch/repository/com/github/punchplatform/punch-punchlang-resources/8.0-dev/punch-punchlang-resources-8.0-dev.zip \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

stdgroks: FORCE
	docker run \
		-v ${PWD}/stdgroks/punchlet.punch:/punchlet.punch \
		-v ${PWD}/stdgroks/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

customgroks: FORCE
	docker run \
		-v ${PWD}/customgroks/punchlet.punch:/punchlet.punch \
		-v ${PWD}/customgroks/punchline.yaml:/punchline.yaml \
		-v ${PWD}/customgroks/mypatterns.grok:/mypatterns.grok \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

artifact: FORCE
	docker run \
		-v ${PWD}/artifact/punchline.yaml:/punchline.yaml \
		-v ${HOME}/\.m2/repository/io/github/punchplatform/punch-sample-parser/8.0.0/punch-sample-parser-8.0.0-artifact.zip:/usr/share/punch/artifacts/io/github/punchplatform/punch-sample-parser/8.0.0/punch-sample-parser-8.0.0-artifact.zip \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

generator: FORCE
	docker run \
		-v ${PWD}/generator/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml
