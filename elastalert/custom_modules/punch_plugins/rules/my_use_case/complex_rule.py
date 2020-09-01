#!/usr/bin/env python3
# -*- coding: utf-8 -*-


import dateutil.parser

from elastalert.ruletypes import RuleType

# elastalert.util includes useful utility functions
# such as converting from timestamp to datetime obj
from elastalert.util import ts_to_dt  # NOQA


class ComplexRule(RuleType):

    # By setting required_options to a set of strings
    # You can ensure that the rule config file specifies all
    # of the options. Otherwise, ElastAlert will throw an exception
    # when trying to load the rule.
    required_options = set(["time_start", "time_end", "kafka_topic_name"])

    # add_data will be called each time Elasticsearch is queried.
    # data is a list of documents from Elasticsearch, sorted by timestamp,
    # including all the fields that the config specifies with "include"
    def add_data(self, data):
        for document in data:
            
            try: 
                # To access config options, use self.rules
                if document["kafka"]["topic"] in self.rules["kafka_topic_name"]:
                    # Convert the timestamp to a time object
                    login_time = document["@timestamp"].time()
                    # Convert time_start and time_end to time objects
                    time_start = dateutil.parser.parse(self.rules["time_start"]).time()
                    time_end = dateutil.parser.parse(self.rules["time_end"]).time()
                    # If the time falls between start and end
                    if login_time > time_start and login_time < time_end:
                        # To add a match, use self.add_match
                        self.add_match(document)
            except:
                print("kafka topic is not a valid key")

    # The results of get_match_str will appear in the alert text
    def get_match_str(self, match):
        return "%s logged in between %s and %s" % (
            match["kafka"]["topic"],
            self.rules["time_start"],
            self.rules["time_end"],
        )

    # garbage_collect is called indicating that ElastAlert has already been run up to timestamp
    # It is useful for knowing that there were no query results from Elasticsearch because
    # add_data will not be called with an empty list
    def garbage_collect(self, timestamp):
        pass
