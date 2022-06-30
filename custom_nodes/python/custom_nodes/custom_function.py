#!/usr/bin/env python
# -*- coding: utf-8 -*-

from punch_api.datasets import InputDatasets, OutputDatasets
from punch_api.nodes import Function

__author__ = "RT"


class CustomFunction(Function):
    """Generate a Pandas DataFrame from a list of values."""

    def __init__(self, show: bool) -> None:
        self.show: bool = show

    def execute(self, input_datasets: InputDatasets, output_datasets: OutputDatasets) -> None:
        if self.show:
            for name, table in input_datasets.items():
                print("Showing table " + name)
                print(table)
        output_datasets.put(input_datasets.get_first())
