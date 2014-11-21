import requests
import json

#finds all the valid string that to do contain the prefix and store the string into an array
#which is sent back to the server.
def findValidString(prefix,StringArray):
    finalArray = []
    #for loop checks if the string does not have the prefix to input the values
    for string in StringArray:
        if(string[:3] != prefix):
            finalArray.append(string)
    return finalArray

#return array to the server
def sendArray(finalArray):
    access = json.dumps({'token':'dLcGAO9jnJ','array': finalArray})
    sent = requests.post('http://challenge.code2040.org/api/validateprefix', data = access)


def main():
    prefix = ''
    #request for the prefix and array dictionary
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post('http://challenge.code2040.org/api/prefix', data = access)
    stringArray = r.json()['result']['array']
    prefix = r.json()['result']['prefix']
    #print out prefix to make sure it is the correct prefix
    print(prefix)
    #returns the array to finalArray
    finalArray = findValidString(prefix,stringArray)
    #return the finalArray to the server
    sendArray(finalArray)

main()
