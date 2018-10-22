import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

class DB_Read():
    cred = credentials.Certificate('spaceapps-team-fuego-firestore-key.json')
    firebase_admin.initialize_app(cred)


    def get_lists(self):
        db = firestore.client()
        # fetch example
        users_ref = db.collection(u'reports_test') # pre-pending u for Unicode?
        docs = users_ref.get()
        dicts = []
        for doc in docs:
            #print(u'{} => {}'.format(doc.id, doc.to_dict()))
            dicts.append(doc.to_dict())

        lists = []
        for dict in dicts:
            list = []
            list.append(dict['creator'])
            list.append(dict['lng'])
            list.append(dict['lat'])
            lists.append(list)

        return(lists)