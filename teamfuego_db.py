import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore


cred = credentials.Certificate('spaceapps-team-fuego-firestore-key.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

# fetch example
users_ref = db.collection(u'reports_test') # pre-pending u for Unicode?
docs = users_ref.get()
items = []
for doc in docs:
    print(u'{} => {}'.format(doc.id, doc.to_dict()))
    this_dict = doc.to_dict()
    items.append(doc.to_dict())

print(items)
