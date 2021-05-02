import requests
import time
import telepot
from telepot.loop import MessageLoop
from apscheduler.schedulers.background import BackgroundScheduler

GBOT = telepot.Bot('1399371607:AAFoJqXLtMrQOon55LResG903vClU3-KNgA')
jobs = []
sched = BackgroundScheduler()
sched.start()

def message():
    r = requests.get('https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,EUR')
    t = time.localtime()

    ret = "Date, {BTC-USD, BTC-EUR}\n"
    ret += "%04d/%02d/%02d %02d:%02d:%02d, " % (t.tm_year, t.tm_mon, t.tm_mday, t.tm_hour, t.tm_min, t.tm_sec) 
    ret += r.text
   
    return ret

def send(cid):
    GBOT.sendMessage(cid, message())

def handle(msg):
    global jobs
    content_type, noUse, chat_id = telepot.glance(msg)
    
    if content_type == 'text':
        if msg['text'] == 'get price info': 
            if jobs.count(chat_id)==0:
                send(chat_id)
                sched.add_job(send, 'interval', minutes=1, id="%d"%chat_id, args = [chat_id])
                jobs.append(chat_id)

        elif msg['text'] == 'stop':
            if jobs.count(chat_id):
                sched.remove_job("%d"%chat_id)
                jobs.remove(chat_id)
                GBOT.sendMessage(chat_id, "Stopped")

        else:
            GBOT.sendMessage(chat_id, "Please enter 'get price info' or 'stop'")


def main():
    
    MessageLoop(GBOT, handle).run_as_thread()

    for i in range (2*60*60):
        time.sleep(1)

if __name__ == "__main__":
    main()
