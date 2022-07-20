#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from re import search

from elastalert.enhancements import BaseEnhancement


class EnhancementMatcher(object):

    # NAME matcher
    MAO: str = "Mao"

    # Consts
    COUNTRY: str = "country"
    NAME: str = "name"
    USA: str = "USA"


class DiscoverCountry(BaseEnhancement):
    def process(self, match: dict) -> None:
        if search(EnhancementMatcher.MAO, match[EnhancementMatcher.NAME]):
            match[EnhancementMatcher.COUNTRY] = EnhancementMatcher.USA
