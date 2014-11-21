import requests
import json
from collections import deque

#revereses the string using deque
def reverseString(iterable):
    d = deque()
    d.extendleft(iterable)
    return ''.join(d)

def main():
    #request to obtain the string to reverse
    access = json.dumps({'token':'dLcGAO9jnJ'})
    r = requests.post("http://challenge.code2040.org/api/getstring", data = access)

    #prints out the string before reversing
    text = r.json()['result']
    print 'String before reversing: ',text
    
    #prints out the reveresed string
    text = reverseString(text)
    print 'String after reversing: ',text
    
    #sends the reverses string back for varification 
    send = json.dumps({'token':'dLcGAO9jnJ','string': text})
    returnString = requests.post('http://challenge.code2040.org/api/validatestring', data = send)

main()