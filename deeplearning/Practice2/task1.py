# class  Deep Learning & applications, Practice #1
# file   name practice1_2014003927.py
# author KWONMINSUK(minshogi@gmail.com)  2014003927
# date   2021/03/25

import numpy as np
import time
import json
import calc


class task1:

    def __init__(self, data):
        self.xTrain = np.array(data['xTrain'])
        self.yTrain = np.array(data['yTrain'])
        self.xTest = np.array(data['xTest'])
        self.yTest = np.array(data['yTest'])
        self.M = data['M']
        self.N = data['N']
        self.K = data['K']
        self.genRandomParameter()
        self.costWithTrain = float()
        self.costWithTest = float()
        self.accuracyWithTrain = float()
        self.accuracyWithTest = float()
        self.timeForTrain = float()
        self.timeForTest = float()

    def trainCost(self):
        return self.costWithTrain

    def testCost(self):
        return self.costWithTest

    def trainAccuracy(self):
        return self.accuracyWithTrain

    def testAccuracy(self):
        return self.accuracyWithTest

    def trainTime(self):
        return self.timeForTrain

    def testTime(self):
        return self.timeForTest

    def printWB(self, W, B, iter):
        print(f"iter: {iter}\nW: {W}\nB: {B}\n")

    def result(self):
        data = {}
        data['accuracyWithTrain'] = self.trainAccuracy()
        data['accuracyWithTest'] = self.testAccuracy()
        data['trainTime'] = self.trainTime()
        data['testTime'] = self.testTime()
        data['trainCost'] = self.trainCost()
        data['testCost'] = self.testCost()
        return data

    def genRandomParameter(self):
        self.B = np.random.random()
        self.W = np.array([[np.random.random()], [np.random.random()]])

    def train(self):
        start = time.time()
        W = self.W
        B = self.B
        X = self.xTrain
        Y = self.yTrain
        K = self.K
        M = self.M
        dWBef = W
        dBBef = 0
        alpha = 0.01

        for iter in range(K):
            Z = np.dot(W.T, X) + B
            A = calc.sigmoid(Z)
            dZ = A - Y
            dW = np.dot(X, dZ.T)/M
            dB = np.sum(dZ)/M
            W = W - alpha*dW
            B = B - alpha*dB
            if(iter % 10 == 0):
                self.printWB(W, B, iter)

            if iter % 100 == 0:
                alpha *= 10

            dWBef = dW
            dBBef = dB

        Z = np.dot(W.T, X) + B
        A = calc.sigmoid(Z)
        J = calc.Cost(A, Y, M)

        end = time.time()

        self.W = W
        self.B = B
        self.costWithTrain = J
        self.accuracyWithTrain = calc.Accuracy(A, Y, M)
        self.timeForTrain = end-start

    def test(self):
        start = time.time()
        W = self.W
        B = self.B
        X = self.xTest
        Y = self.yTest
        N = self.N

        Z = np.dot(W.T, X) + B
        A = calc.sigmoid(Z)
        J = calc.Cost(A, Y, N)

        end = time.time()

        self.costWithTest = J
        self.accuracyWithTest = calc.Accuracy(A, Y, N)
        self.timeForTest = end - start

    def run(self):
        self.train()
        self.test()
        return self.result()
