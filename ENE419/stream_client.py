"""
   ENE 419 - Computer Networks, 2020 Fall, Assignment 3.1

   @filename    stream_client.py
   @author      KWONMINSUK(minshogi@gmail.com) 2014003927
   @date        2020/12/11
"""

import asyncio
import websockets
import json

uri = "wss://api.upbit.com/websocket/v1"
msg = '[{"ticket":"UNIQUE_TICKET"},{"type":"trade","codes":["KRW-BTC"]},{"type":"orderbook","codes":["KRW-BTC"]}]'


async def my_connect():
    async with websockets.connect(uri=uri) as websocket:
        await websocket.send(msg)
        while 1:
            data = await websocket.recv()
            jdata = json.loads(data)
            data_type = jdata["type"]

            if(data_type == "orderbook"):
                unit=jdata["orderbook_units"][0]
                for i in unit:
                    name=str(i)
                    price=str(unit[name])
                    print(name+": "+ price, end='   ')
                print("")


            elif(data_type == "trade"):
                price = str(jdata["trade_price"])
                volume = str(jdata["trade_volume"])
                bid = str(jdata["ask_bid"])
                print("trade_price: "+price+", trade_volume: "+volume+", ask_bid: "+bid)


asyncio.get_event_loop().run_until_complete(my_connect())


