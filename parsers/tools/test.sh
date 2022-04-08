#!/bin/bash
# @author: Punch Team
# @desc: Installer script for the PunchPlatform Standalone.

function errecho () {
	echo "$*" >&2 ;
}
function defaultTputFGColorCode () {
    if [ -n "${DEFAULTFGCOLOR:-}" ] ; then
		echo $DEFAULTFGCOLOR
		return
    fi
	if [ "$TERM" == "xterm-256color" ]; then
		echo 255
	else
		echo 9
	fi
}
function tputColorCode () {
	colorName=$1
	tput_color_BLACK=0
	tput_color_RED=1
	tput_color_GREEN=2
	tput_color_YELLOW=3
	tput_color_BLUE=4
	tput_color_MAGENTA=5
	tput_color_CYAN=6
	tput_color_WHITE=7
	eval 'echo -n ${tput_color_'$colorName'}'
}
function FG () {
	if [ -z "${NOCOLOR:-}" ]; then
		colorName=${1:-DEFAULT}
		if [ $colorName == DEFAULT ] ; then
			tput setaf $(defaultTputFGColorCode)
		else
			tput setaf	 $(tputColorCode $colorName)
		fi
	fi
	shift
	if  [ $# -ne 0 ]; then
		echo -n "$@"
		# we are in the case where parameters to echo are provided explicitely
		# which pin the color is to be applied only to the provided parameters
		# therefore we restore automatically the default color at the end of the provided
		# parameters :
		if [ -z "${NOCOLOR:-}" ]; then
			tput setaf $(defaultTputFGColorCode)
		fi
	fi
}
function green () { echo $(FG GREEN "$*" ) ;}
function red () { errecho $(FG RED "$*" ) ;}
function yellow () { echo $(FG YELLOW "$*" ) ;}
function check_prerequisites() {
	
  mandatoryAvailableCommands="docker"
  for command in ${mandatoryAvailableCommands}; do
    if which "$command" &>/dev/null; then
    	green "$command installed."
    else
    	yellow "Command '$command' is missing. Tests are skipped";
    	exit 0;
    fi
  done
}

function main() {
  	docker run -v $PWD:/workdir/ ghcr.io/punchplatform/puncher:8.0.0 -T /workdir
  	exit $?
}
main $@

