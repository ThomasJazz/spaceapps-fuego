from fire_post import FirePost
from teamfuego_db import DB_Read

test_db = DB_Read()
fire_info = test_db.get_lists()
test = FirePost()
print(test.fire_verify(fire_info, 2, 2))