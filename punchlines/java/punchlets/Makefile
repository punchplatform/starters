all: fileresource arkoon geoip stdgroks customgroks

.PHONY: fileresource

fileresource:
	docker run -it --rm \
		-v $(CURDIR)/fileresource/punchlet.punch:/punchlet.punch \
		-v $(CURDIR)/fileresource/punchline.yaml:/punchline.yaml \
		-v $(CURDIR)/fileresource/color_codes.json:/color_codes.json \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

.PHONY: arkoon

arkoon:
	docker run -it --rm \
		-v $(CURDIR)/arkoon/punchlet.punch:/punchlet.punch \
		-v $(CURDIR)/arkoon/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

.PHONY: parsing-error

parsing-error:
	docker run -it --rm \
		-v $(CURDIR)/parsing-error/punchlet.punch:/punchlet.punch \
		-v $(CURDIR)/parsing-error/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

.PHONY: geoip

geoip:
	docker run -it --rm \
		-v $(CURDIR)/geoip/punchlet.punch:/punchlet.punch \
		-v $(CURDIR)/geoip/punchline.yaml:/punchline.yaml \
		-v $(CURDIR)/geoip/belgium6.csv.gz:/belgium6.csv.gz \
		-v $(CURDIR)/geoip/belgium4.csv.gz:/belgium4.csv.gz \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

.PHONY: stdgroks

stdgroks:
	docker run -it --rm \
		-v $(CURDIR)/stdgroks/punchlet.punch:/punchlet.punch \
		-v $(CURDIR)/stdgroks/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

.PHONY: customgroks

customgroks:
	docker run -it --rm \
		-v $(CURDIR)/customgroks/punchlet.punch:/punchlet.punch \
		-v $(CURDIR)/customgroks/punchline.yaml:/punchline.yaml \
		-v $(CURDIR)/customgroks/mypatterns.grok:/mypatterns.grok \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml
