#/bin/bash

if [ $# -ne 2 ]; then
   echo "Usage: ./run.sh <input dir> <output dir>"
   exit
fi

mvn exec:java -Dexec.mainClass="com.uxebu.swfparser.dump.ActionScriptBatchDump" -Dinput=$1 -Doutput=$2
