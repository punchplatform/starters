#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from elastalert.enhancements import BaseEnhancement

class ComplexEnhancement(BaseEnhancement):

    # The enhancement is run against every match
    # The match is passed to the process function where it can be modified in any way
    # ElastAlert will do this for each enhancement linked to a rule
    def process(self, match):
        try:
            if "standalone" in match["platform"]["id"] :
                description = "This is a standalone platform"
                match['description'] = description
        except:
            print("platform id is not a valid key")
