#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from typing import List

from elastalert.alerts import Alerter


class AlertParameter(object):
    STDOUT_COMMAND: str = "stdout_command"


class Stdout(Alerter):

    required_options = {AlertParameter.STDOUT_COMMAND}

    def alert(self, matches: List[dict]) -> None:
        for _match in matches:
            match: dict = _match
            if self.rule[AlertParameter.STDOUT_COMMAND]:
                print(match)

    def get_info(self) -> dict:
        return {
            "type": "ComplexAlert",
            "stdout_command": self.rule[AlertParameter.STDOUT_COMMAND],
            "message": "alert has been sent",
        }
