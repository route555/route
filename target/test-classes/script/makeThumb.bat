@echo off

set INPUT_FILE="D:\max\git\echoe\echo-cms\src\test\resources\test.jpg"
set OUT_FILE_PREFIX=%2
set OUT_FORMAT=%3
set OUTPUT_FILE=%4

COPY %INPUT_FILE% %OUTPUT_FILE%