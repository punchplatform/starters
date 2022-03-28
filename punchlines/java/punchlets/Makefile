all: fileresource arkoon geoip stdgroks customgroks
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
parsing-error: FORCE
	docker run \
		-v ${PWD}/parsing-error/punchlet.punch:/punchlet.punch \
		-v ${PWD}/parsing-error/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml
geoip: FORCE
	docker run \
		-v ${PWD}/geoip/punchlet.punch:/punchlet.punch \
		-v ${PWD}/geoip/punchline.yaml:/punchline.yaml \
		-v ${PWD}/geoip/belgium6.csv.gz:/belgium6.csv.gz \
		-v ${PWD}/geoip/belgium4.csv.gz:/belgium4.csv.gz \
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
