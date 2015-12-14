#!/usr/bin/python
"""usage: movInfo file
       movInfo test.mp4
"""

import sys, os, subprocess, re

FFMPEG="ffmpeg"

RECOM_VIDEO = [256, 320, 512, 640, 768, 960, 1024, 1280, 1600, 1920, 2048]
RECOM_AUDIO = [64, 96, 128, 192, 256, 320]


def getRate(recom, val):
	ret = 0
	for rate in recom:
		if (( rate <= val ) and ( rate - val ) <= 0):
			ret = rate
	return ret	


def getRateAll():
	dura = int(bitrate)

	for ar in RECOM_AUDIO:
		if ( dura > ar ):
			v = dura - ar
			vr = getRate(RECOM_VIDEO, v)

			if ( vr == 0 ):
				 break

			print "recommended bitrate=" + str(vr + ar) + ", video=" + str(vr) + ", audio=" + str(ar)


def printRecomData(recom, rate, isVideo):
	for r in recom:
		if ( isVideo == 1 ):
			print "recommended bitrate=" + str(r + int(rate)) + ", video=" + str(r) + ", audio=" + rate
		else:
			print "recommended duratoin=" + str(r + int(rate)) + ", video=" + rate + ", audio=" + str(r)


def recomAnalysis():
	recomVideoRate = [0, 0]
	recomAudioRate = [0, 0]

	print "video=" + videoFmt + ", audio=" + audioFmt

	if ( videoRate == "0"  ) and ( audioRate != "0" ):
		recomVideoRate[0] = int(bitrate) - int(audioRate)
		recomVideoRate[1] = getRate(RECOM_VIDEO, recomVideoRate[0])

		printRecomData(recomVideoRate, audioRate, 1)
	elif ( videoRate != "0"  ) and ( audioRate == "0" ):
		recomAudioRate[0] = int(bitrate) - int(videoRate)
		recomAudioRate[1] = getRate(RECOM_AUDIO, recomAudioRate[0])

		printRecomData(recomAudioRate, videoRate, 0)
	else:
		getRateAll()


#print __doc__

if len(sys.argv) != 2:
	print __doc__
	raise SystemExit

cmd = FFMPEG + " -i " + sys.argv[1]
proc = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, universal_newlines=True, stderr=subprocess.PIPE)

p4duration = re.compile("Duration: ([0-9]+):([0-9]+):([0-9]+).([0-9]+),");
p4bitrate = re.compile("bitrate: ([0-9]*) kb/s");
p4video = re.compile("Video: ([0-9a-zA-Z,]*)\s(.*),");
p4videoresolution = re.compile("Video: (.*)\s([0-9]+x[0-9]+)\s*(.*),");
p4videorate = re.compile(", ([0-9]*) kb/s");
p4videofps = re.compile(", ([0-9.]*) tbr");
p4audio = re.compile("Audio: ([0-9a-zA-Z]*),");
p4audiorate = re.compile(", ([0-9]*) kb/s");

duration = 0
bitrate = "0"
videoFmt = "unknown"
videoResolution = "unknown"
videoRate = "0"
videoFps = "0"
audioFmt = "unknown"
audioRate = "0"

for line in proc.stderr:
	m = p4duration.search(line)
	if m:
		duration = int(m.group(1)) * 3600 + int(m.group(2)) * 60 + int(m.group(3)) 
		m = p4bitrate.search(line)
		if m:
			bitrate = m.group(1)	
		continue

	m = p4video.search(line)
	if m:
		videoFmt = m.group(1)

		m = p4videoresolution.search(line)
		if m:
			videoResolution = m.group(2)	

		m = p4videorate.search(line)
		if m:
			videoRate = m.group(1)

		m = p4videofps.search(line)
		if m:
			videoFps = m.group(1)
		continue

	m = p4audio.search(line)
	if m:
		audioFmt = m.group(1)

		m = p4audiorate.search(line)
		if m:
			audioRate = m.group(1)
		continue

#print "duration[s],bitrate[kb/s],videoResolution,videoRate[kb/s],fps[tbr],audio[kb/s]"
print str(duration) + "," + bitrate + "," + videoResolution + "," + videoRate + "," + videoFps + "," + audioRate
