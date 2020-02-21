# Prerequisite

-   A quick look of our API documentation: `https://doc.punchplatform.com/doc/punchplatform-pyspark/html/index.html`
-   Brainstorm your use-case with our existing nodes: `https://doc.punchplatform.com/Reference_Guide/Data_Analytics/Nodes/Elastic_Input.html`
-   Python3.6+ installed: `https://doc.punchplatform.com/Tutorials/Pyenv.html`
-   A dave-standalone installed: `https://punchplatform.com/download-nightly-dave-standalone/`
-   Python3-venv (or equivalent)

# Quick Start

Note that we will be using `punchpkg`...

```sh
# Use punchpkg pyspark info to get install dir of pyspark

    >   punchpkg pyspark info

# Let's try running our template_node

# To begin, we will make our node available to our shells and editor

    >  eval "$(_PUNCHPKG_COMPLETE=source punchpkg)"     # for auto completion
    > punchpkg pyspark link-external-nodes $(pwd)     #  note: pwd here is rootdir of this README.txt
    > punchpkg pyspark list-external-nodes    # check if node was linked properly
    > punchpkg pyspark install-dependencies $(pwd)/complex_algorithm_dependencies   # install custom dependencies needed by your module (note: if the given module is not available on PyPI, please convert your module to PEX and use the same command on your PEX file !)
    > punchlinectl -p $(pwd)/full_job.pl -r pyspark

|  _ \  _   _  _ __    ___ | |__  | |    (_) _ __    ___ 
| |_) || | | || '_ \  / __|| '_ \ | |    | || '_ \  / _ \
|  __/ | |_| || | | || (__ | | | || |___ | || | | ||  __/
|_|     \__,_||_| |_| \___||_| |_||_____||_||_| |_| \___|
                                                         
 ____          _    _                   
|  _ \  _   _ | |_ | |__    ___   _ __  
| |_) || | | || __|| '_ \  / _ \ | '_ \ 
|  __/ | |_| || |_ | | | || (_) || | | |
|_|     \__, | \__||_| |_| \___/ |_| |_|
        |___/                           

using nodes from ./nodes sources
Hello punch

Execution took 0.18007254600524902 seconds
```

Let's try for now to add some autocompletion to our favorite IDE

```sh
# Grab our punchline_python.whl file and install it using pip install in a virtualenv
# Note when using pip install some_modules. Be sure to track added modules in a seperate file.
# i.e don't mix our installed dependencies with your since this would generate big PEX files...
```

# Coding/deploying your custom node

Follow the getting started on writing your custom node: `https://doc.punchplatform.com/doc/punchplatform-pyspark/html/getting_started.html`

# Making your node available to our shells/PL Editor

```sh
# In case your node uses some custom modules like: pandas
# You should provide a text file named as your module. 
# The text file should include only the custom modules your node is using
punchpkg pyspark install-dependencies full/path/to/text_file/custom_modules

>   punchpkg pyspark install-dependencies complex_algorithm_dependencies

# Check if your custom module is properly installed
# A json document will be outputted on stdout, search for the key custom_pex_dependencies
# Within this key, you will see custom_modules
punchpkg pyspark list-dependencies

# Check the current module
punchpkg pyspark info

# Installing your custom node from full path (use tab for autocompletion)
punchpkg pyspark install-node </tab></tab>

>   punchpkg pyspark install-node $(pwd)/algorithms

# List installed nodes
punchpkg pyspark list-nodes

# Executing a node
# either use our PL editor or use our shell punchlinectl
punchlinectl -p full/path/to/job.pl -r pyspark

>   punchlinectl -p full_job.pl -v -r pyspark

 ____                       _      _      _
|  _ \  _   _  _ __    ___ | |__  | |    (_) _ __    ___ 
| |_) || | | || '_ \  / __|| '_ \ | |    | || '_ \  / _ \
|  __/ | |_| || | | || (__ | | | || |___ | || | | ||  __/
|_|     \__,_||_| |_| \___||_| |_||_____||_||_| |_| \___|
                                                         
 ____          _    _                   
|  _ \  _   _ | |_ | |__    ___   _ __  
| |_) || | | || __|| '_ \  / _ \ | '_ \ 
|  __/ | |_| || |_ | | | || (_) || | | |
|_|     \__, | \__||_| |_| \___/ |_| |_|
        |___/                           

using nodes from ./nodes sources
Hello punch

Execution took 0.18007254600524902 seconds
```
