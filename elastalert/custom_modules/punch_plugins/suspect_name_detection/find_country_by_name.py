#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from elastalert.enhancements import BaseEnhancement
from re import search


class EnhancementMatcher(object):

    MAO: str = "Mao"
    COUNTRY: str = "country"
    NAME: str = "name"


class FindCountryByName(BaseEnhancement):

    def process(self, match: dict) -> None:
        if search(EnhancementMatcher.MAO, match[EnhancementMatcher.NAME]):
            match[EnhancementMatcher.COUNTRY] = "USA"
