#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from elastalert.ruletypes import RuleType
from typing import List


class RulesParameter(object):
    SUSPECT_NAMES: str = "suspect_names"


class DetectSuspectName(RuleType):

    required_options = set([RulesParameter.SUSPECT_NAMES])

    def add_data(self, data: List[dict]) -> None:
        for _element in data:
            element: dict = _element
            name: str = element.get("name", "N/A")
            if name in self.rules[RulesParameter.SUSPECT_NAMES]:
                self.add_match(element)

    def get_match_str(self, match) -> str:
        return str(match)

    def garbage_collect(self, timestamp):
        pass
