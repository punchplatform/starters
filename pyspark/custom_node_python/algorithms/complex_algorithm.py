#!/usr/bin/env python3
# coding: utf-8

from punchline_python.core.holders.input_holder import InputHolder
from punchline_python.core.holders.output_holder import OutputHolder
from punchline_python.core.node import AbstractNode
from typing import List
import redis


class ComplexAlgorithm(AbstractNode):

    # uncomment below if we want this node to take as input a single dataframe
    #  @AbstractNode.declare_dataframe_input()
    # We are expecting this node to publish one dataframe as output
    @AbstractNode.declare_dataframe_output()
    # We make use of the decorator design pattern to declare our node parameters...
    @AbstractNode.declare_param(name="param1", required=False, default="TEST")
    # We expect that this node subscribe to a stream and is going to output a stream of data
    @AbstractNode.declare_map_dataframe_input()
    @AbstractNode.declare_map_dataframe_output()
    def __init__(self) -> None:
        super().__init__()
        # Decorators on this constructor are used to by our job editor

    def complex_logic(self, param1: str) -> str:
        return "Hello {}".format("punch")

    def execute(self, input_data: InputHolder, output_data: OutputHolder) -> None:
        """ This method is executed by the engine
        You have access:
         * to subscribed node data: input_data
         * to publish data of any type: output_data
        """
        results: List[str] = self.complex_logic(self.settings.get("param1"))  # do something with your list...
        output_data.set(results)  # here we submit it to the next node !
