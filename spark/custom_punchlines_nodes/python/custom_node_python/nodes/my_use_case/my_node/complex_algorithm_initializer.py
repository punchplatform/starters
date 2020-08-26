#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from pyspark.sql.session import SparkSession


class ComplexAlgorithmnInitializer(object):
    """You can initialize sparksession object with additional values
    """

    __spark_session: SparkSession

    def __init__(self) -> None:
        self.__spark_session = SparkSession.builder.getOrCreate()
        self.configure()

    def configure(self) -> None:
        # do something with your spark session
        print("configuration example before punchline execution")
