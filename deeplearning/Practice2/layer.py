import numpy as np


def sigmoid(z):
    return 1/(1+np.exp(-z))


def sigmoidPrime(z):
    return sigmoid(z)*(1-sigmoid(z))


class Layer:
    def __init__(self, units, inputDim, alpha, M):
        self.myUnits = units
        self.myDimension = inputDim
        self.myAlpha = alpha
        self.W = self.wInitializer(units, inputDim)
        self.b = self.bInitializer(units)
        self.aCache = np.array(0)
        self.zCache = np.array(0)
        self.M = M

    def alphaUpdate(alpha):
        self.myAlpha = alpha

    def wInitializer(self, units, inputDim):
        return np.random.rand(units, inputDim)

    def bInitializer(self, units):
        return np.random.rand(units, 1)

    def forwardPropagation(self, data):
        Z = np.dot(self.W, data) + self.b
        A = sigmoid(Z)
        self.aCache = data
        self.zCache = Z
        return A

    def backwardPropagation(self, dAIn):
        dZ = dAIn * sigmoidPrime(self.zCache)
        dW = np.dot(dZ, self.aCache.T)/self.M
        db = np.sum(dZ, axis=1, keepdims=True)/self.M
        dAOut = np.dot(self.W.T, dZ)

        self.W = self.W - dW * self.myAlpha
        self.b = self.b - db * self.myAlpha

        return dAOut

    def printWB(self):
        print(f"W: {self.W}\nB: {self.b}")

    def test(self, data):
        Z = np.dot(self.W, data) + self.b
        A = sigmoid(Z)
        return A
