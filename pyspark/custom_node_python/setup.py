#!/usr/bin/env python3
# -*- coding: utf-8 -*-


from setuptools import setup, find_packages

setup(
    name="nodes",
    version="1.0",
    packages=find_packages(),
    include_package_data=True,
    author="punchplatform",
    author_email="contact@punchplatform.com",
    description="boilerplate for custom nodes",
    python_requires='>=3.6',
    install_requires=[
        "pex==2.1.15",  # this is important do not remove
        "requests==2.24.0",
        "redis"
    ]
)
