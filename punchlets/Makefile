xall: fileresource arkoon geoip stdgroks customgroks stdgroks2

.PHONY: fileresource

fileresource:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

.PHONY: arkoon

arkoon:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

.PHONY: parsing-error

parsing-error:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

.PHONY: geoip

geoip:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

.PHONY: stdgroks

stdgroks:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

.PHONY: stdgroks2

stdgroks2:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

.PHONY: customgroks

customgroks:
	docker run -it --rm \
		-v $(CURDIR)/$@:/files \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml
