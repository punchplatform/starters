#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from pymongo import MongoClient
from elastalert.loaders import RulesLoader
import yaml

class ComplexRuleLoader(RulesLoader):
    def __init__(self, conf):
        super(ComplexRuleLoader, self).__init__(conf)
        self.client = MongoClient(conf['mongo_url'])
        self.db = self.client[conf['mongo_db']]
        self.cache = {}

    def get_names(self, conf, use_rule=None):
        if use_rule:
            return [use_rule]

        rules = []
        self.cache = {}
        for rule in self.db.rules.find():
            self.cache[rule['name']] = yaml.load(rule['yaml'])
            rules.append(rule['name'])

        return rules

    def get_hashes(self, conf, use_rule=None):
        if use_rule:
            return [use_rule]

        hashes = {}
        self.cache = {}
        for rule in self.db.rules.find():
            self.cache[rule['name']] = rule['yaml']
            hashes[rule['name']] = rule['hash']

        return hashes

    def get_yaml(self, rule):
        if rule in self.cache:
            return self.cache[rule]

        self.cache[rule] = yaml.load(self.db.rules.find_one({'name': rule})['yaml'])
        return self.cache[rule]
