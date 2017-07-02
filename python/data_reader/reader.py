from ibmseti.ibmseti.compamp import Compamp
from ibmseti.ibmseti.dsp import time_bins
from ibmseti.ibmseti.dsp import frequency_bins
from ibmseti.ibmseti.dsp import compamp_to_spectrogram

rawdata = open('sample-data.archive-compamp', "rb")
aca = Compamp(rawdata.read())
rawdata.close()

time_bins = time_bins(aca.header())
freq_bins = frequency_bins(aca.header())

spec = compamp_to_spectrogram(aca)
header = aca.header()

counta = 0
countb = 0
data_out = open("out.txt", "w")

header_str = str(header)
header_str = header_str + "\n"
data_out.write(header_str)

line = ""

for a in spec:

    countb = 0
    for b in a:
        countb = countb + 1

        line = line + str(b)
        line = line + ";"

    counta = counta+1
    line = line + "\n"
    data_out.write(line)
    line = ""

data_out.close()

print(header)
print("number of half frames : " + counta.__str__())
print("vals per half frame : " + countb.__str__())






