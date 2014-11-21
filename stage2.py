import requests
import json

#method to find the index the needle is located inside the dictionary hackstack.
def findIndex(searchStack,needle):
    counter = 0
    for indx in searchStack['result']['haystack']:
        if(indx == needle):
            return counter
        counter+=1
#method to send the index back
def sendIndex(index):
    access = json.dumps({'token':'dLcGAO9jnJ','needle': index})
    sent = requests.post('http://challenge.code2040.org/api/validateneedle', data = access)

def main():
    index = 0
    needle = ''
    #acquire the two dictionaries for haystack and needle
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post('http://challenge.code2040.org/api/haystack', data = access)
    searchStack = r.json()
    #print out the needle value
    needle = searchStack['result']['needle']
    print 'Needle', needle
    #returns the index to the integer index
    index = findIndex(searchStack,needle)
    print 'Index of the needle in the haystack:', index
    sendIndex(index)

main()