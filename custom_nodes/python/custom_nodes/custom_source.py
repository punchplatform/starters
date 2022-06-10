#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pandas
from api.datasets import OutputDatasets
from api.nodes import Source

__author__ = "RT"


class CustomSource(Source):
    """Generate a Pandas DataFrame from a list of values."""

    def __init__(self, data: list) -> None:
        self.data: list = data
        if len(self.data) <= 0:
            raise ValueError("No data to inject")

    def execute(self, output_datasets: OutputDatasets) -> None:
        output: pandas.DataFrame = pandas.DataFrame(self.data)
        output_datasets.put(output)
