import numpy as np


def sigmoid(z):
    return 1/(1+np.exp(-z))


def Cost(yHat, y, size):
    J = 0
    for i in range(size):
        a = yHat[0][i]
        yi = y[0][i]
        J += Loss(a, yi)
    return J/size


def Loss(a, y):
    aa = np.absolute(a-1e-9)
    return -(y*np.log(aa)+(1-y)*np.log(1-aa))


def Accuracy(yHat, y, size):
    tmp = yHat-y
    cnt = 0
    for i in range(size):
        if(tmp[0][i] < 0.1):
            cnt += 1.0
    return float(cnt/size*100)
