#!/bin/bash

if [ $# != 4 ]; then
	echo "usage: makeThumb input start_time[s] thumbnail_size output"; 
	echo "       makeThumb test.mp4 1 50x50 /tmp/test.jpg"; 
	exit -1;
fi

INPUT_FILE=$1
START_POS=$2
THUMBNAIL_SIZE=$3
OUTPUT_FILE=$4

ffmpeg -loglevel quiet -i "$INPUT_FILE" -an -ss "$START_POS" -s $THUMBNAIL_SIZE -qscale 1 -r 0.5 -vframes 1 -y $OUTPUT_FILE 2>/dev/null