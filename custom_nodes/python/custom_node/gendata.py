#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json

from typing import List
from typing import Optional

import pandas

from api.holders.input_holder import InputHolder
from api.holders.output_holder import OutputHolder
from api.node import AbstractNode
from api.node_api import NodeType


__author__ = "RT"


class GeneratorSource(AbstractNode):
    """Generate a pandas dataset from an inline json object.
    Schema on json data will be inferred
    """

    @AbstractNode.declare_list_object_param(name="messages", required=True)
    @AbstractNode.declare_string_param(name="format", required=False)
    @AbstractNode.declare_dataframe_output()
    @AbstractNode.set_group(NodeType.input_node_python())
    def __init__(self) -> None:
        super().__init__()

    @staticmethod
    def __empty_dataframe():
        return pandas.DataFrame({0: []})

    def execute(self, input_data: InputHolder, output_data: OutputHolder) -> None:
        data: List = self.settings["messages"]
        data_format: Optional[str] = self.settings.get("format")

        if data_format is not None and data_format.casefold() == "json".casefold():
            if len(data) == 1:
                data = json.loads(data[0])
            else:
                if len(data) == 0:
                    msg = "A value in json format is required"
                else:
                    msg = "Can not generate multiple messages in json format, give only one"
                raise RuntimeError(msg)

        if len(data) > 0:
            output = pandas.DataFrame(data)
        else:
            output = GeneratorSource.__empty_dataframe()
        output_data.set(output)
