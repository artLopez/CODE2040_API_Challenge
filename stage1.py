import requests
import json
from collections import deque

# Arturo Lopez
#
# stage1.py
# Abstact: The post request receives a
# String in order to reverse the String.
# Afterwards, a post request is sent
# to validate the reversed string.


#revereses the string using deque from collections
def reverseString(iterable):
    d = deque()
    d.extendleft(iterable)
    return ''.join(d)

def main():
    #request to obtain the String
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post("http://challenge.code2040.org/api/getstring", data = access)

    #prints out the string before reversing
    text = r.json()['result']
    print 'String before reversing: ',text
    
    #prints out the reveresed string after the function is called
    text = reverseString(text)
    print 'String after reversing: ',text
    
    #sends a post request for the reversed string for verification
    send = json.dumps({'token':'dLcGAO9jnJ','string': text})
    returnString = requests.post('http://challenge.code2040.org/api/validatestring', data = send)

main()