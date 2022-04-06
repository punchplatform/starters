ARG PYTHON_VERSION_TAG="3.6.8-slim"
FROM python:${PYTHON_VERSION_TAG} AS build-with-binaries
WORKDIR /starter
RUN DEBIAN_FRONTEND=noninteractive apt-get update &&\
    apt-get install --no-install-suggests --no-install-recommends --yes make gettext-base zip &&\
    rm -rf /var/lib/apt/lists/* &&\
    apt-get clean

FROM build-with-binaries AS build-with-venv
ARG SOURCES="custom_node"
    # python:
ENV PYTHONFAULTHANDLER=1 \
    PYTHONUNBUFFERED=1 \
    PYTHONHASHSEED=random \
    PYTHONDONTWRITEBYTECODE=1 \
    # pip:
    PIP_DISABLE_PIP_VERSION_CHECK=1 \
    PIP_DEFAULT_TIMEOUT=100
WORKDIR /starter
COPY INFO INFO
COPY tools tools
COPY metadata metadata
COPY Makefile Makefile
COPY ${SOURCES} ${SOURCES}
COPY poetry.lock poetry.lock
COPY poetry.toml poetry.toml
COPY pyproject.toml pyproject.toml
RUN make artifact
