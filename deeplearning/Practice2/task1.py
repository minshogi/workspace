# class  Deep Learning & applications, Practice #1
# file   name practice1_2014003927.py
# author KWONMINSUK(minshogi@gmail.com)  2014003927
# date   2021/03/25

import numpy as np
import time
import json
import layer


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
        if tmp[0][i] < 0.3:
            cnt += 1
    return cnt/size*100


class task1:

    def __init__(self, data, alpha):
        self.xTrain = np.array(data['xTrain'])
        self.yTrain = np.array(data['yTrain'])
        self.xTest = np.array(data['xTest'])
        self.yTest = np.array(data['yTest'])
        self.M = data['M']
        self.N = data['N']
        self.K = data['K']
        self.myAlpha = alpha
        self.L1 = layer.Layer(units=1, inputDim=2, alpha=alpha, M=data['M'])

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

    def train(self):
        start = time.time()

        X = self.xTrain
        Y = self.yTrain

        for iter in range(self.K):
            A = self.L1.forwardPropagation(X)
            self.L1.backwardPropagation(A)

            if(iter % 10 == 0):
                # self.L1.printWB()
                print(Cost(A, self.yTrain, self.M))

        end = time.time()

        self.timeForTrain = end-start

    def test(self):
        start = time.time()

        A = self.L1.test(self.xTrain)
        J = Cost(A, self.yTrain, self.M)
        self.accuracyWithTrain = Accuracy(A, self.yTrain, self.M)
        self.costWithTrain = Cost(A, self.yTrain, self.M)

        A = self.L1.test(self.xTest)
        J = Cost(A, self.yTest, self.N)
        self.accuracyWithTest = Accuracy(A, self.yTest, self.N)
        self.costWithTest = Cost(A, self.yTest, self.N)

        end = time.time()

        self.timeForTest = end - start

    def run(self):
        self.train()
        self.test()
        return self.result()
