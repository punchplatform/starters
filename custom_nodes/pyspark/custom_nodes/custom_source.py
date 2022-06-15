#!/usr/bin/env python
# -*- coding: utf-8 -*-

from api.datasets import OutputDatasets
from api.nodes import Source
from pyspark.sql import SparkSession, DataFrame

__author__ = "RT"


class CustomSource(Source):
    """Generate a Pandas DataFrame from a list of values."""

    def __init__(self, data: list) -> None:
        self.data: list = data
        if len(self.data) <= 0:
            raise ValueError("No data to inject")

    def execute(self, output_datasets: OutputDatasets) -> None:
        output: DataFrame = SparkSession.builder.getOrCreate().createDataFrame(self.data)
        output_datasets.put(output)
