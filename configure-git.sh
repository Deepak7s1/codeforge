#!/bin/bash

echo "Configure current GIT repository clone with some recommended values."

exec_config() {
	while [ $# -ne 0 ]; do
		echo "#######"
		echo $1
		`$1`
		shift
	done
}
		

AUTOSETUPREBASE="git config --global branch.autosetuprebase always"
REBASEMASTER="git config branch.master.rebase true"
PUSHHEAD="git config remote.origin.push HEAD"
AUTOCRLF="git config --global core.autocrlf input"
DEFAULTTRACKING="git config --global push.default tracking"

exec_config "$AUTOSETUPREBASE" \
            "$REBASEMASTER" \
            "$PUSHHEAD" \
            "$AUTOCRLF" \
            "$DEFAULTTRACKING"
