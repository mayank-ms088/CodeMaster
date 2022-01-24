#!/usr/bin/env bash

# ****** BEGIN - Configuration *******
# Input/Output format
# '#' will be replaced by 1, 2, ...
IN="input.#.txt"
OUT="output.#.txt"
LANG=$1
OUTPUT_CMD=$2
CODEFILE=$3

# Limits
TL=$4 || 5 # Time limit (in seconds)

## ****** END - Configuration ********

#--------------------------------------------------

# Color Codes
red='\033[0;31m'
RED='\033[1;31m'
GREEN='\033[1;32m'
green='\033[0;32m'
blue='\033[0;34m'
BLUE='\033[1;34m'
cyan='\033[0;36m'
CYAN='\033[1;36m'
NC='\033[0m' # No Color

# Cleanup on exit
rm -f .overview .compiler_report .time_info ."$CODEFILE".out

trap '{ rm -f .overview .compiler_report .time_info .$CODEFILE.out; }' SIGINT SIGTERM EXIT

#if [ $# -ne 1 ]
#then
#  echo "Usage: $0 source_code"
#  echo "   e.g. $0 test.cpp"
#  echo "   use the above to grade file test.cpp"
#  exit 2
#fi

#Compiler Prep
COMPILER="$LANG $CODEFILE 2> .compiler_report"

# Language detection
#LANG=`echo $LANG | awk -F . '{print $NF}'`
#if [ "$LANG" == "cpp" ]
#then
#  COMPILER="$CPP $LANG 2> .compiler_report" # C++
#elif [ "$LANG" == "c" ]
#then
#  COMPILER="$C $LANG 2> .compiler_report" # C
#elif [ "$LANG" == "pas" ]
#then
#  COMPILER="$PAS $LANG 2> .compiler_report" # Pascal
#fi

# Compilation
echo -e " ${CYAN}* Compiling source code${NC}";
echo "$COMPILER" | sh



if [ $? -ne 0 ]
then
  echo -e " ${BLUE}COMPILATION_STATUS: ${RED}FAILURE${NC}";
  cat .compiler_report;
  exit 1;
fi

echo -e " ${BLUE}COMPILATION_STATUS: ${GREEN}SUCCESS${NC}";
echo

SYS_TIME_LIMIT=10
ulimit -t $SYS_TIME_LIMIT;

rm -rf .overview;
CORRECT=0
MAX_N=100

#RUNTIME STATUS
OK=" ${CYAN}CHECKER_STATUS: ${GREEN}OK${NC}"
WA=" ${CYAN}CHECKER_STATUS: ${RED}WA${NC}"
RE=" ${CYAN}CHECKER_STATUS: ${RED}RE${NC}"
TLE=" ${CYAN}CHECKER_STATUS: ${RED}TLE${NC}"
#TESTING---------------------------------------------------------------
for (( i=1; i<=$MAX_N; i++))
do

  TEST_CASE_IN=$(echo $IN | sed "s/#/$i/g")
  TEST_CASE_OUT=$(echo $OUT | sed "s/#/$i/g")

  # If i-th test case doesn't exist then stop here.
  if [ ! -e "$TEST_CASE_IN" ]
  then
    break
  fi
  echo -e "${BLUE}TESTCASE $i:${NC}";

  #fetching program time
  time -p (OUTPUT_CMD < $TEST_CASE_IN > .$CODEFILE.out) 2> .time_info;

  EX_CODE=$?;
  if [ $EX_CODE -eq 137 ] || [ $EX_CODE -eq 152 ]
  then
    echo -e "$TLE"
  elif [ $EX_CODE -ne 0 ]
  then
    echo -e "$RE"
  else
    PROG_TIME=$(cat .time_info | grep real | cut -d" " -f2);
    #cheking for time
    if [ "$PROG_TIME" \> "$TL" ]
    then
        echo -e "$TLE"
        break;
    fi
    #checking output
    diff --strip-trailing-cr ."$CODEFILE".out "$TEST_CASE_OUT" > /dev/null

    if [ $? -eq 0 ]
    then
      echo -e "$OK"
      CORRECT=$(expr $CORRECT + 1)
    else
      echo -e "$WA"
    fi
    echo -e " ${CYAN}TIME: ${GREEN}$PROG_TIME"
    echo -e " ${BLUE}OUTPUT:${NC}"
    cat ."$CODEFILE".out
  fi
  echo;
done
#N=$(expr "$i" - 1)
echo;
