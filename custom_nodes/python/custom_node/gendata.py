#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json
from enum import Enum

import pandas
from api.datasets import OutputDatasets
from api.nodes import Source


__author__ = "RT"


class GeneratorDataFormat(Enum):
    raw: str = "raw"
    json: str = "json"


class GeneratorSource(Source):
    """Generate a Pandas DataFrame from a list of values."""

    def __init__(self, messages: list, format: str = "raw") -> None:
        self.data: list = messages
        self.data_format: GeneratorDataFormat = GeneratorDataFormat(format)

    def execute(self, output_datasets: OutputDatasets) -> None:
        output: pandas.DataFrame = pandas.DataFrame({0: []})
        if len(self.data) > 0:
            if self.data_format == GeneratorDataFormat.json:
                output = pandas.DataFrame(json.loads(self.data[0]))
            else:
                output = pandas.DataFrame(self.data)
        output_datasets.put(output)
