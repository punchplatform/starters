#!/usr/bin/env python3
# -*- coding: utf-8 -*-


import boto3
import yaml
from typing import List
import hashlib

from elastalert.loaders import RulesLoader

class S3RuleLoaderParams(object):
    """Constant and configuration parameters that will be used in config.yaml"""

    # Parameters
    BUCKET: str = "s3.bucket"
    RULES_DIRECTORY: str = "s3.rules_directory"
    ENDPOINT: str = "s3.endpoint"

    # Security parameters

    ACCESS_KEY: str = "s3.access_key"
    SECRET_KEY: str = "s3.secret_key"
    SESSION_TOKEN: str = "s3.session_token"


class S3Loader(RulesLoader):
    """Load a rule from an elasticsearch index
    2 modes are available, either parameter rule_from_field from field is used or not.
    In case rule_from_field is used, we exepect the rule.yaml file content to be a single line valid yaml string.
    Else, each line of the document is the content of the yaml file
    """

    required_globals = frozenset(
        [S3RuleLoaderParams.BUCKET,  S3RuleLoaderParams.ENDPOINT, S3RuleLoaderParams.ACCESS_KEY, S3RuleLoaderParams.SECRET_KEY]
    )

    def __init__(self, conf: dict) -> None:
        super(S3Loader, self).__init__(conf)
        self.client = S3Loader.S3_client_init(conf)
        self.cache: dict = {}

    @staticmethod
    def S3_client_init(conf: dict) -> boto3:
        """Initializes an s3 client for fetching rules"""
        return boto3.client(
                            service_name="s3",
                            endpoint_url=conf.get(S3RuleLoaderParams.ENDPOINT, ""),
                            aws_access_key_id=conf.get(S3RuleLoaderParams.ACCESS_KEY, ""),
                            aws_secret_access_key=conf.get(S3RuleLoaderParams.SECRET_KEY, ""),
                            aws_session_token=conf.get(S3RuleLoaderParams.SESSION_TOKEN, ""),
                        )

    def get_names(self, conf: dict, use_rule=None) -> List[str]:
        """
        Return a list of rule names that can be passed to `get_yaml` to retrieve.
        :param dict conf: Configuration dict
        :param str use_rule: Limit to only specified rule
        :return: A list of rule names
        :rtype: list
        """
        """List of rules available"""
        myRulesName: List[str] = []
        rulesList : dict = self.client.list_objects(Bucket=conf.get(S3RuleLoaderParams.BUCKET, ""), Prefix=conf.get(S3RuleLoaderParams.RULES_DIRECTORY, "")+"/")
        if "Contents" in rulesList:
            for rule in rulesList['Contents']: 
                response : object = self.client.get_object(Bucket=conf.get(S3RuleLoaderParams.BUCKET, ""), Key=rule.get('Key'))

                rulefile: dict = yaml.safe_load(response.get("Body"))
                rulename: str = rulefile.get("name")

                self.cache[rulename] = str(rulefile).encode('utf-8')

                myRulesName.append(rulename)

            return myRulesName
        raise FileNotFoundError(conf.get(S3RuleLoaderParams.RULES_DIRECTORY) + " directory does not exist in your bucket")

    def get_hashes(self, conf: dict, use_rule=None) -> dict:
        """
        Discover and get the hashes of all the rules as defined in the conf.
        :param dict conf: Configuration
        :param str use_rule: Limit to only specified rule
        :return: Dict of rule name to hash
        :rtype: dict
        """
        """Elastalert reloads periodically rules, a hash is calculated on the latest rule retrieved
        if calculated hash matches, rule will not be reloaded
        """
        myRulesName: list[str] = self.get_names(conf)
        hashes: dict = {}
        for _name in myRulesName:
            name: str = _name
            hashes[name] = hashlib.sha1(self.cache[name]).hexdigest()
        return hashes

    def get_yaml(self, rule: str):
        """
        Get and parse the yaml of the specified rule.
        :param str filename: Rule to get the yaml
        :return: Rule YAML dict
        :rtype: dict
        """
        """return a yaml object, which is the rule that we will be using in memory"""
        if rule in self.cache:
            return yaml.load(self.cache[rule], Loader=yaml.FullLoader)
        raise FileNotFoundError("yaml file could not be retrieved from s3")
