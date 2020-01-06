#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import sys
from airflow import DAG
from datetime import timedelta
from dags.dag_template.job_info import job_info
from plugins.punchplatform_plugin.operators.punch_execute_job_operator import PunchExecuteJobOperator


dag = DAG(
    'boilerplate',
    description='Example on how to use airflow with the punchplatform',
    catchup=False,
    default_args=job_info,
    schedule_interval=timedelta(minutes=1))

conf1 = PunchExecuteJobOperator(
    job_name='generate_me_first.pl',
    job_info=job_info,
    job_type='spark',
    cleanup=False,
    dag=dag)

conf2 = PunchExecuteJobOperator(
    job_name='generate_me_second.pl',
    job_info=job_info,
    job_type='spark',
    cleanup=False,
    dag=dag)

conf1 >> conf2