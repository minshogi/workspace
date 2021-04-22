# class  Deep Learning & applications, Practice #2
# file   datagen.py
# author KWONMINSUK(minshogi@gmail.com)  2014003927
# date

import numpy as np
import time
import json


def genXs(m):
    ret = np.array(np.random.uniform(low=-10.0, high=10.0, size=2*m))
    ret.resize(2, m)
    return ret


def genYs(Xs, m):
    arr = []
    for i in range(m):
        if(Xs[0][i] + Xs[1][i] > 0):
            arr.append(1)
        else:
            arr.append(0)
    return np.array([arr])


def datagen(m, n, k):
    xTrain = genXs(m)
    yTrain = genYs(xTrain, m)
    xTest = genXs(n)
    yTest = genYs(xTest, n)

    data = {}
    data['xTrain'] = xTrain.tolist()
    data['yTrain'] = yTrain.tolist()
    data['xTest'] = xTest.tolist()
    data['yTest'] = yTest.tolist()
    data['M'] = m
    data['N'] = n
    data['K'] = k

    return data
