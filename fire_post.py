import requests
import json
from datetime import date, timedelta

class FirePost():

    def fire_check(self, input_list):

        api_url = 'https://hawking.sv.cmu.edu:9016/opennex/getAPIResult'
        headers = {'content-type': 'application/json'}
        request = {
            'date1': input_list[0],
            'date2': input_list[1],
            'ulx': input_list[2],
            'uly': input_list[3],
            'lrx': input_list[4],
            'lry': input_list[5],
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

    #Verifies a group of fires given a list of lists
    #That is, it checks if there was a fire in the last 24 hours
    #Takes list given by teamfuego_db, number of days to look in the past, and length of a side (as a float)
    def fire_verify(self, list, days, length):
        list_toReturn = []
        for item in list:
            today = date.today()
            first_day = today - timedelta(days)
            today_format = today.strftime("%Y%j")
            first_format = first_day.strftime("%Y%j")

            half_length = length / 2.0
            lat = item[1]
            lng = item[2]

            fire_present = self.fire_check([first_format, today_format,
                                      lng - half_length, lat + half_length,
                                      lng + half_length, lat - half_length])
            list_toReturn.append(fire_present)
        return list_toReturn



