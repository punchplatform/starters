#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from pyspark.sql.session import SparkSession


class ComplexAlgorithmnPostExecution(object):
    """You can run some code after your punchline execution has finished
    """

    __spark_session: SparkSession

    def __init__(self) -> None:
        self.__spark_session = SparkSession.builder.getOrCreate()
        self.post()

    def post(self) -> None:
        # do something with your spark session
        print("configuration example post punchline execution")
