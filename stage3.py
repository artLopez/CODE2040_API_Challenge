import requests
import json

# Arturo Lopez

# stage3.py
# Abstract: Stage 3 deals with the API
# sends a dictionary with the prefix and
# array keys. The goal is to return an
# array containing only the strings that
# do not contain the prefix.


# finds all the valid string that do not contain the prefix and
# store the strings into an array which is sent back to the API
def findValidString(prefix,StringArray):
    # Array that stores all the string without the prefix
    finalArray = []
    #for loop checks if the string does not have the prefix to input the values
    for string in StringArray:
        if(string[:3] != prefix):
            finalArray.append(string)
    return finalArray

#return array to the API
def sendArray(finalArray):
    access = json.dumps({'token':'dLcGAO9jnJ','array': finalArray})
    sent = requests.post('http://challenge.code2040.org/api/validateprefix', data = access)


def main():
    #string that contains the prefix when received.
    prefix = ''
    #request for the prefix and array dictionary
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post('http://challenge.code2040.org/api/prefix', data = access)
    stringArray = r.json()['result']['array']
    prefix = r.json()['result']['prefix']
    #print out the prefix
    print(prefix)
    #returns the array with valid strings
    finalArray = findValidString(prefix,stringArray)
    #return the finalArray to the API
    sendArray(finalArray)

main()
