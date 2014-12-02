import requests
import json
import datetime

#function sends returns the data 
def sendDate(date):
    access = json.dumps({'token':'dLcGAO9jnJ','datestamp': date})
    sent = requests.post('http://challenge.code2040.org/api/validatetime', data = access)
    print(sent.status_code)

def main():
    #acquire the datestamp
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post('http://challenge.code2040.org/api/time', data = access)
    
    #prints out the data
    print 'Date', r.json()['result']['datestamp']
    print 'Interval', r.json()['result']['interval']
    interval = r.json()['result']['interval']
    datestamp = r.json()['result']['datestamp']

    #parse the datestamp
    year     = int(datestamp[:4])
    month    = int(datestamp[5:7])
    day      = int(datestamp[8:10])
    hours    = int(datestamp[11:13])
    minutes  = int(datestamp[14:16])
    seconds  = int(datestamp[17:19])
    microSec = int(datestamp[20:23])
    #print the output of the parsed datestamp
    print year,month,day,hours,minutes,seconds,microSec

    #creates a datetime for the datestamp and adds the interval 
    currentTime = datetime.datetime(year,month,day,hours,minutes,seconds,microSec)
    updatedTime = currentTime + datetime.timedelta(seconds=interval)
    #updates the time with the isoformat and thens prints the time
    updatedTime = updatedTime.isoformat()
    print updatedTime
    #return the data in the sendDate function
    sendDate(updatedTime)

main()
