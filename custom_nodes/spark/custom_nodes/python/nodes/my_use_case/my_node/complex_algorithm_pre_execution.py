#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from pyspark.sql.session import SparkSession


class ComplexAlgorithmPreExecution(object):
    """You can initialize spark session object with additional values
    """

    __spark_session: SparkSession

    def __init__(self) -> None:
        self.__spark_session = SparkSession.builder.getOrCreate()
        self.pre()

    def pre(self) -> None:
        # do something with your spark session
        print("configuration example pre punchline execution")
