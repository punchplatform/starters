#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from elastalert.enhancements import BaseEnhancement

class ComplexEnhancement(BaseEnhancement):

    # The enhancement is run against every match
    # The match is passed to the process function where it can be modified in any way
    # ElastAlert will do this for each enhancement linked to a rule
    def process(self, match):
        if 'domain' in match:
            url = "http://who.is/whois/%s" % (match['domain'])
            match['domain_whois_link'] = url
