all: fileresource arkoon geoip stdgroks customgroks
FORCE: ;

fileresource: FORCE
	docker run -it --rm \
		-v ${PWD}/fileresource/punchlet.punch:/punchlet.punch \
		-v ${PWD}/fileresource/punchline.yaml:/punchline.yaml \
		-v ${PWD}/fileresource/color_codes.json:/color_codes.json \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml

arkoon: FORCE
	docker run -it --rm \
		-v ${PWD}/arkoon/punchlet.punch:/punchlet.punch \
		-v ${PWD}/arkoon/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml
parsing-error: FORCE
	docker run -it --rm \
		-v ${PWD}/parsing-error/punchlet.punch:/punchlet.punch \
		-v ${PWD}/parsing-error/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml
geoip: FORCE
	docker run -it --rm \
		-v ${PWD}/geoip/punchlet.punch:/punchlet.punch \
		-v ${PWD}/geoip/punchline.yaml:/punchline.yaml \
		-v ${PWD}/geoip/belgium6.csv.gz:/belgium6.csv.gz \
		-v ${PWD}/geoip/belgium4.csv.gz:/belgium4.csv.gz \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml

stdgroks: FORCE
	docker run -it --rm \
		-v ${PWD}/stdgroks/punchlet.punch:/punchlet.punch \
		-v ${PWD}/stdgroks/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml

customgroks: FORCE
	docker run -it --rm \
		-v ${PWD}/customgroks/punchlet.punch:/punchlet.punch \
		-v ${PWD}/customgroks/punchline.yaml:/punchline.yaml \
		-v ${PWD}/customgroks/mypatterns.grok:/mypatterns.grok \
		ghcr.io/punchplatform/punchline-java:8.0.0 \
		/punchline.yaml
