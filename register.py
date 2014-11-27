import requests
import json

# Register for CODE2040 API Challenge using my email and URL for my github repository
# Arturo,Lopez

def main():
    #request for registration
    access = json.dumps({'email':'arlopez@csumb.edu',
                        'github':'https://github.com/artLopez/CODE2040_API_Challenge'})
        
    r = requests.post('http://challenge.code2040.org/api/register', data = access)
    print(r.json()['result'])
main()