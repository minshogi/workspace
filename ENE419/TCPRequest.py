import requests as req
import os

resp = req.get('http://127.0.0.1:12000')
print("receive:\n", resp.text)
