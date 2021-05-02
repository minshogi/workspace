from socket import *

serverPort = 12000
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('127.0.0.1', serverPort))
serverSocket.listen(1)
print ('The server is ready to receive')

while 1:
    connectionSocket, addr = serverSocket.accept()
    sentence = connectionSocket.recv(1024)
    capitalizedSentence = sentence.upper()
    print('GET Message received:\n',sentence)
    connectionSocket.send(capitalizedSentence)
    connectionSocket.close()

