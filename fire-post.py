import requests
import json

class FirePost():

    def __init__(self, input_list):
        self.input_list = input_list

    def main(self):

        api_url = 'https://hawking.sv.cmu.edu:9016/opennex/getAPIResult'
        headers = {'content-type': 'application/json'}
        request = {
            'date1': self.input_list[0],
            'date2': self.input_list[1],
            'ulx': self.input_list[2],
            'uly': self.input_list[3],
            'lrx': self.input_list[4],
            'lry': self.input_list[5],
            'serviceId': 23,
            'userId': 58,
            'purpose': "Testing API"
        }

        request = json.dumps(request)
        r = requests.post(url=api_url, headers=headers, data=request)
        text = r.text
        test = text.split('\":\"')[1]
        if test == "Y":
            return True
        else:
            return False

#Need to get
test = FirePost([2018021, 2018022, -98, 20, -94, 16, 23, 58, 'Testing API'])
print(test.main())


