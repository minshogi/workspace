"""
class   Deep Learning & applications, Practice #2
file    network.py
author  KWONMINSUK(minshogi@gmail.com)  2014003927
date    2021.04.24(sat)
"""

import numpy as np
import time
import layer


def Cost(yhat, y, size):
    L = Loss(yhat, y)
    J = np.sum(L)/size
    return J


def Loss(a, y):
    aa = np.absolute(a-1e-9)
    return -(y*np.log(aa)+(1-y)*np.log(1-aa))


def LossPrime(a, y):
    aa = np.absolute(a-1e-9)
    return -y/aa + (1-y)/(1-aa)


def Accuracy(yHat, y, size):
    tmp = yHat-y
    cnt = 0
    for i in range(size):
        if tmp[0][i] < 0.1:
            cnt += 1
    return cnt/size*100


class Network:

    def __init__(self, data, networkDesign):
        self.xTrain = np.array(data['xTrain'])
        self.yTrain = np.array(data['yTrain'])
        self.xTest = np.array(data['xTest'])
        self.yTest = np.array(data['yTest'])
        self.M = data['M']
        self.N = data['N']
        self.K = data['K']
        self.myAlpha = networkDesign['alpha']
        self.layerSize = networkDesign['layerSize']
        self.unitSize = networkDesign['unitSize']

        self.Layers = self.layerInitializer()

        self.costWithTrain = float()
        self.costWithTest = float()
        self.accuracyWithTrain = float()
        self.accuracyWithTest = float()
        self.timeForTrain = float()
        self.timeForTest = float()

    def result(self):
        data = {}
        data['accuracyWithTrain'] = self.accuracyWithTrain
        data['accuracyWithTest'] = self.accuracyWithTest
        data['timeForTrain'] = self.timeForTrain
        data['timeForTest'] = self.timeForTest
        data['costWithTrain'] = self.costWithTrain
        data['costWithTest'] = self.costWithTest
        data['alpha'] = self.myAlpha

        return data

    def layerInitializer(self):

        L = []
        inputDim = 2
        for Size in self.unitSize:
            Lt = layer.Layer(units=Size, inputDim=inputDim,
                             alpha=self.myAlpha, M=self.M)
            L.append(Lt)
            inputDim = Size
        return L

    def randomAlpha(self):
        r = -2*np.random.rand()
        a = pow(10, r)
        return a

    def train(self):
        X = self.xTrain
        Y = self.yTrain

        for iter in range(self.K):
            inA = X
            for L in self.Layers:
                inA = L.forwardPropagation(inA)

            dA = LossPrime(inA, Y)
            for L in reversed(self.Layers):
                dA = L.backwardPropagation(dA)

#            if(iter % 10 == 0):
#                cnt = 0
#                for L in self.Layers:
#                    print(f'{cnt}Layer: ')
#                    L.printWB()
#                    cnt += 1

    def test(self, X, Y, testSize):
        A = X
        for L in self.Layers:
            A = L.test(A)

        return A

    def run(self):
        start = time.time()
        self.train()
        end = time.time()
        self.timeForTrain = end-start

        X = self.xTrain
        Y = self.yTrain
        M = self.M

        A = self.test(X, Y, M)
        self.accuracyWithTrain = Accuracy(A, Y, M)
        self.costWithTrain = Cost(A, Y, M)

        X = self.xTest
        Y = self.yTest
        N = self.N

        start = time.time()
        A = self.test(X, Y, N)
        self.accuracyWithTest = Accuracy(A, Y, N)
        self.costWithTest = Cost(A, Y, N)
        end = time.time()

        self.timeForTest = end - start

        return self.result()
