from fire_post import FirePost
from teamfuego_db import DB_Read

def main(days, length):
    test_db = DB_Read()
    fire_info = test_db.get_lists()
    test = FirePost()
    #To modify this,
    print(test.fire_verify(fire_info, days, length))


def run():
    main(2, 2)


run()