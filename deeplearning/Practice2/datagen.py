"""
class   Deep Learning & applications, Practice #2
file    datagen.py
author  KWONMINSUK(minshogi@gmail.com)  2014003927
date    2021.04.24(sat)
"""


import numpy as np


def genXs(m):
    ret = np.random.uniform(low=-10.0, high=10.0, size=2*m)
    ret.resize(2, m)
    return ret


def genYs(Xs, m):
    arr = Xs.sum(axis=0)
    arr[arr > 0] = 1
    arr[arr <= 0] = 0
    return arr


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
