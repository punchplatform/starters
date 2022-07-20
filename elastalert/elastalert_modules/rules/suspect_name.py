#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from typing import List

from elastalert.ruletypes import RuleType


class RulesParameter(object):

    # Parameters
    SUSPECT_NAMES: str = "suspect_names"

    # Consts
    NAME: str = "name"
    NA: str = "N/A"


class SuspectName(RuleType):

    required_options = set([RulesParameter.SUSPECT_NAMES])

    def add_count_data(self, counts) -> None:
        pass

    def add_terms_data(self, terms) -> None:
        pass

    def add_aggregation_data(self, payload) -> None:
        pass

    def add_data(self, data: List[dict]) -> None:
        """Iterate over hits.hits return by elastic and check for a match in name field"""
        for _element in data:
            element: dict = _element
            name: str = element.get(RulesParameter.NAME, RulesParameter.NA)
            if name in self.rules[RulesParameter.SUSPECT_NAMES]:
                self.add_match(element)

    def get_match_str(self, match) -> str:
        return str(match)

    def garbage_collect(self, timestamp) -> None:
        pass
