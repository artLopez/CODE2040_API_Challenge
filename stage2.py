import requests
import json

#Arturo Lopez
#
#stage2.py
#
# Abstract: The post request retrieves the needle and
# haystack dictionary. The needle is a value that is contain
# in the haystack dictionary. The goal is to locate the needle's
# value in the haystack's dictionary and send back the post request
# with the index of the needle.


#method to find the index the needle is located inside the dictionary hackstack.
def findIndex(searchStack,needle):
    #counter for the index
    counter = 0
    for indx in searchStack['result']['haystack']:
        #when the needle is found in the haystack return the counter(index)
        if(indx == needle):
            return counter
        counter += 1

#method to send the index back
def sendIndex(index):
    access = json.dumps({'token':'dLcGAO9jnJ','needle': index})
    sent = requests.post('http://challenge.code2040.org/api/validateneedle', data = access)

def main():
    #variable declaration for the index and the string needle,
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
    #post request sent back with the needle's index in the haystack dictionary
    sendIndex(index)

main()