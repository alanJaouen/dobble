#!/bin/bash


if type -p javac; then
    echo found javac executable in PATH
    _java=javac
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/javac" ]];  then
    echo found java executable in JAVA_HOME     
    _java="$JAVA_HOME/bin/javac"
else
    echo "no java"
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F ' ' '{print $0}')
    echo version "$version"
    if [[ "$version" > "1.5" ]]; then
        echo version is more than 1.5
        echo "verification de ant"

		if [ -e "./ant/bin/ant" ]
		then
			echo "compilation et generation du jar"
			ant/bin/ant install
		else
			echo "necessite ant"
			open http://ant.apache.org/bindownload.cgi

		fi
    else         
        echo "version is less than 1.5"
		open http://www.oracle.com/technetwork/java/javase/downloads/index.html
    fi
fi


