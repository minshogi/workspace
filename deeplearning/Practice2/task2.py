#class  Deep Learning & applications, Practice #1
#file   name practice1_2014003927.py
#author KWONMINSUK(minshogi@gmail.com)  2014003927
#date   2021/03/25

import numpy as np
import time

rng = np.random.default_rng(12345)
old_setting = np.seterr(all='ignore')

X_train = []
Y_train = []
X_test = []
Y_test = []
W_ = []
B_ = []

def gen_Xs(m):
    ret = np.array(rng.integers(low = -10, high = 10, size = 2*m))
    ret.resize(2, m)
    return ret

def gen_Ys(Xs, m):
    arr = []
    for i in range(m):
        if(Xs[0][i] + Xs[1][i] > 0):
            arr.append(1)
        else:
            arr.append(0)
    return np.array([arr])

def gen_B():
    return rng.random()

def gen_W():
    return np.array([[rng.random()], [rng.random()]])

def sigmoid(z):
    return 1/(1+np.exp(-z))

def Cost(yHat, y, size):
    J = 0
    for i in range(size):
        a = yHat[0][i]
        yi = y[0][i]
        J += Loss(a, yi)
    return J/size

def Cost_Acc_element(x1, x2, y, w1, w2, b, size):
    J = 0
    cnt = 0
    for i in range(size):
        z = w1*x1[i] + w2*x2[i] + b
        a = sigmoid(z)
        J += Loss(a, y[i])
        if(np.absolute(a-y[i]) == 0):
            cnt += 1

    J /= size
    acc = cnt/size*100    

    return J, acc

                                                      
def Loss(a, y):
    aa = np.absolute(a-1e-9)
    return -(y*np.log(aa)+(1-y)*np.log(1-aa))

def Accuracy(yHat, y, size):
    tmp = yHat-y
    cnt = 0
    for i in range(size):
        if(tmp[0][i] == 0):
            cnt += 1
    return cnt/size*100

def init_():
    global X_train, Y_train, X_test, Y_test, W_, B_
    X_train = gen_Xs(1000)
    Y_train = gen_Ys(X_train, 1000)
    X_test = gen_Xs(100)
    Y_test = gen_Ys(X_test, 100)
    W_ = gen_W()
    B_ = gen_B()

def get_from_X_train(m):
    global X_train
    x1 = []
    x2 = []
    for i in range(m):
        x1.append(X_train[0][i])
        x2.append(X_train[1][i])
    return np.array([x1,x2])

def calc_element():
    global X_train, Y_train, X_test, Y_test, W_, B_
    x1_train = X_train[0]
    x2_train = X_train[1]
    y_train = Y_train[0]
    x1_test = X_test[0]
    x2_test = X_test[1]
    y_test = Y_test[0]

    w1 = W_[0][0]
    w2 = W_[1][0]
    b = B_

    alpha = 0.01
    best_alpha = alpha
    best_J = 1
    best_w1 = w1
    best_w2 = w2
    best_b = b

    while alpha < 1200:
        tw1 = w1
        tw2 = w2
        tb = b
        for iter in range (2000):
            dw1 = 0
            dw2 = 0
            db = 0
            for i in range (1000):
                x1 = x1_train[i]
                x2 = x2_train[i]
                yi = y_train[i]

                z = tw1 * x1 + tw2 * x2 + tb
                a = sigmoid(z)
                dz = a - yi
                dw1 += x1*dz
                dw2 += x2*dz
                db += dz
                
            dw1 /= 1000
            dw2 /= 1000
            db /= 1000
            
            tw1 = tw1 - alpha * dw1
            tw2 = tw2 - alpha * dw2
            tb = tb - alpha * db
        
        J, trs = Cost_Acc_element(x1_train, x2_train, y_train, tw1, tw2, tb, 1000)

        if(J < best_J):
            best_J = J
            best_w1 = tw1
            best_w2 = tw2
            best_b = tb
            best_alpha = alpha

        alpha *= 7
    
    w1 = best_w1
    w2 = best_w2
    b = best_b

    j, acc = Cost_Acc_element(x1_train, x2_train, y_train, w1, w2, b, 1000) 

    print("Empirically determined (best) hyper parameter, alpha: ", best_alpha)
    print("function parameter w1: ", w1)
    print("function parameter w2: ", w2)
    print("function parameter B: ", b)
    print(f"The cost on the '1000' train samples: ", j)
    print(f"Accuracy for the '1000' train samples: {acc}%")

    j, acc = Cost_Acc_element(x1_test, x2_test, y_test, w1, w2, b, 100) 
    
    print(f"The cost with the '100' test samples: ", j)
    print(f"Accuracy with the '100' test samples: {acc}%")
    
    
def calc_vect(m, K):
    global X_test, Y_test, W_, B_

    X_train = get_from_X_train(m)
    Y_train = gen_Ys(X_train, m)
    W = W_
    B = B_

    alpha = 0.01
    best_alpha = alpha
    best_J = 1 
    best_W = W
    best_B = B
    while alpha < 1200:
        tW = W
        tB = B
        for iter in range (K):
            Z = np.dot(tW.T, X_train) + tB
            A = sigmoid(Z)
            dZ = A - Y_train
            dW = np.dot(X_train, dZ.T)/m
            dB = np.sum(dZ)/m
            tW = tW - alpha*dW
            tB = tB - alpha*dB

    #       if(iter%10 == 0):
    #           print("Iter: ", iter)
    #           print("W:\n", tW)
    #           print("B:\n", tB)
                
        Z = np.dot(tW.T, X_train) + tB
        A = sigmoid(Z)
        J = Cost(A, Y_train, m)
        if(J<best_J):
            best_J = J
            best_alpha = alpha
            best_W = tW
            best_B = tB
        
        alpha *= 7

    W = best_W
    B = best_B

    Z = np.dot(W.T, X_train) + B
    A = sigmoid(Z)    
    J = Cost(A, Y_train, m)
    
    print("Empirically determined (best) hyper parameter, alpha: ", best_alpha)
    print("function parameter W:\n", W)
    print("function parameter B: ", B)

    print(f"The cost on the '{m}' train samples: ", J)
    print(f"Accuracy for the '{m}' train samples: {Accuracy(A, Y_train, m)}%")

    Z = np.dot(W.T, X_test) + B
    A = sigmoid(Z)
    J = Cost(A, Y_test, 100)
    
    print(f"The cost with the '100' test samples: ", J)
    print(f"Accuracy with the '100' test samples: {Accuracy(A, Y_test, 100)}%")

def time_comparison():
    print("Time calculating now ... ")
    elem_start = time.time()
    print("<Element-wise version>")
    calc_element()
    elem_end = time.time()
    elem_total = elem_end - elem_start
    print(f"Element-wise version, (m, K) = (1000, 2000), total time cost: ", elem_total)

    vect_start = time.time()
    print("\n<Vectorized version>")
    calc_vect(1000, 2000)
    vect_end = time.time()
    vect_total = vect_end - vect_start
    print(f"Vectorized version, (m, K) = (1000, 2000), total time cost: ", vect_total)

    print(f"\nTime comparison: ", elem_total - vect_total)

def main():
    init_()
    time_comparison()
    while(1):
        m, K = map(int, input('m, K: ').split())
        calc_vect(m, K)
        cont = str(input('continue?(_/n)'))
        if cont == 'n':
            break


if __name__ == "__main__":
    main()