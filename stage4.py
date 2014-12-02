import requests
import json
import iso8601
import calendar
import datetime

def findIntervalTime(interval,years,months,days,hours,minutes,seconds):
    minutes, seconds = divmod(interval,60)
    hours, minutes = divmod(minutes,60)
    days, hours = divmod(hours,24)
    months,days = divmod(days,30)
    years, months = divmod(months,12)
    return years,months,days,hours,minutes,seconds

def findDate(year,month,day,date):
    ctr = 0
    for num in date:
        if(ctr == 0 and num != '-'):
            year += num
        elif(ctr == 1 and num != '-'):
            month += num
        elif(ctr == 2 and num != '-'):
            day += num
        else:
            ctr += 1

    year  = int(year)
    month = int(month)
    day   = int(day)
    
    return year,month,day

def findTime(hrs,minutes,sec,time):
    ctr = 0
    for num in time:
        if(ctr == 0 and num != ':'):
            hrs += num
        elif(ctr == 1 and num != ':'):
            minutes += num
        elif(ctr == 2 and num != ':'):
            sec += num
        else:
            ctr += 1

    hrs     = int(hrs)
    minutes = int(minutes)
    sec     = int(sec)
    
    return hrs, minutes,sec

def findFinalDate(timeChange,dateList):
    if(31 < timeChange[2] + dateList[2]):
        dateList[1] += 1
        dateList[2] = timeChange[2] + dateList[2] - 31
        if(dateList[1] == 12):
            dateList[1] = 1
            dateList[0] += 1
    else:
        dateList[2] += timeChange[2]
    
    
    if(12 < timeChange[1] + dateList[1]):
        dateList[0] += 1
        dateList[1] = timeChange[1] + dateList[1] - 12
    else:
        dateList[1] += timeChange[1]
    
    dateList[0] += timeChange[0]

def findFinalTime(timeChange,dateList,timeList):
    if(60 < timeChange[2] + timeList[2]):
        timeList[1] += 1
        timeList[2] = timeChange[2] + timeList[2] - 60
    else:
        timeList[2] += timeChange[2]
    
    if(59 < timeChange[1] + timeList[1]):
        timeList[0] += 1
        timeList[1] = timeChange[1] + timeList[1] - 59
    else:
        timeList[1] += timeChange[1]
    
    if(24 <= timeChange[0] + timeList[0]):
        dateList[2] += 1
        timeList[0] =  timeChange[0] + timeList[0] - 24
        if(dateList[2] == 31):
            dateList[2] = 1
            dateList[1] += 1
        if(dateList[1] == 13):
            dateList[1] = 1
            dateList[0] += 1
    else:
        timeList[0] += timeChange[0]

def leapYear_30Month(dateChange,dateList,timeChange,timeList):
    years       = 0
    months      = 0
    days_left   = 0
    days        = 0
    total_years = 0
    last_year   = dateChange[0] + dateList[0]
    
    print dateList[0], last_year
    
    for leapYear in range(dateList[0],last_year):
        print leapYear
        days -= 3
        total_years += 1
        if(calendar.isleap(leapYear)):
            print 'Is leap year', leapYear
            days += 1

    print 'total years', total_years
    days += total_years * -4
    print days
    
    if(1 > dateList[2] + days):
        days_left = dateList[2] + days
        if(days_left > -31):
            dateList[1] -= 1
            if(dateList[1] == 0):
                dateList[1] = 12
                dateList[0] -= 1
        else:
            days_left *= -1
            months = days_left / 31
            days_left = (days_left * -1) + 31
            if(dateList[1] - months < 1):
                dateList[1] = (dateList[1] - months) + 12
                dateList[0] -= 1
                dateList[2] -= days_left
    else:
        dateList[2] -= days

#sets the print format
def printFormat(dateList,timeList):
    final = []
    year  = str(dateList[0])
    month = str(dateList[1])
    day   = str(dateList[2])
    
    hour  = str(timeList[0])
    minute = str(timeList[1])
    seconds = str(timeList[2])
    
    if(len(month) == 1):
        month = "0" + month
    if(len(day)== 1):
        day = "0" + day
    if(len(hour) == 1):
        hour = "0" + hour
    if(len(minute) == 1):
        minute = "0" + minute
    if(len(seconds) == 1):
        seconds = "0" + seconds
    
    solution = year + "-" + month + "-" + day + "T" + hour+ ":" + minute + ":" + seconds + ".000Z"
    print(solution)
    sendDate(solution)


#send the data
def sendDate(date):
    access = json.dumps({'token':'dLcGAO9jnJ','datestamp': date})
    sent = requests.post('http://challenge.code2040.org/api/validatetime', data = access)
    print(sent.status_code)

def main():
    #variable declaration
    years   = 0
    months  = 0
    days    = 0
    hours   = 0
    minutes = 0
    seconds = 0
    
    finalMonth = " "
    finalDay   = " "
    finalYear  = " "
    finalHrs   = " "
    finalMin   = " "
    finalSec   = " "
    date       = " "
    time       = " "
    solution   = " "
    
    #acquire the datestamp
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post('http://challenge.code2040.org/api/time', data = access)
    
    #prints out the data
    print 'Date', r.json()['result']['datestamp']
    print 'Interval', r.json()['result']['interval']
    interval = r.json()['result']['interval']
    datestamp = r.json()['result']['datestamp']
    
    print str(datetime.timedelta(seconds=interval))
    
    date = datestamp[:10]
    print 'Date' ,date
    
    time = datestamp[11:19]
    print 'Time', time
    
    #find the years,months,days,hours,minutes,seconds given the interval
    years,months,days,hours,minutes,seconds = findIntervalTime(interval,years,months,days,hours,minutes,seconds)
    #acquire the new year,month,day after fixing the dates
    finalYear, finalMonth, finalDay = findDate(finalYear,finalMonth,finalDay,date)
    #acquire the new time after fixing the time
    finalHrs,finalMin,finalSec = findTime(finalHrs,finalMin,finalSec,time)
    
    #put into an array to make it easier to read and provides cleaner code
    dateChange = [years,months,days]
    dateList   = [finalYear,finalMonth,finalDay]
    timeChange = [hours,minutes,seconds]
    timeList   = [finalHrs,finalMin,finalSec]
    
    #print out the data again
    print 'years','months', 'days'
    print dateChange
    print 'years','months', 'days'
    print dateList
    print 'hours','minutes','seconds'
    print timeChange
    print 'hours','minutes','seconds'
    print timeList
    print '--------------'
    #work with leap years and 31 & 30 months
    leapYear_30Month(dateChange,dateList,timeChange,timeList)
    #find the final date and time
    findFinalDate(dateChange,dateList)
    findFinalTime(timeChange,dateList,timeList)
    
    #print out information
    print dateList
    print timeList
    #printin the iso8601.stamp
    printFormat(dateList,timeList)

main()
