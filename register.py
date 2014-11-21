import requests
import json

def main():
    #request for registration
    access = json.dumps({'email':'arlopez@csumb.edu',
                        'github':'https://github.com/artLopez/CODE2040_API_Challenge'})
        
    r = requests.post('http://challenge.code2040.org/api/register', data = access)
    print(r.json()['result']
main()