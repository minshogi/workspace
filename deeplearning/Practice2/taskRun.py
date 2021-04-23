from datagen import datagen
from task1 import task1
# from task2 import task2
# from task3 import task3
import json


def main():
    m, n, k = map(int, input('m, n, k: ').split())
    alpha = float(input('alpha: '))
    data = datagen(m, n, k)
    t1 = task1(data, alpha)
    t1test = t1.test()
    t1data = t1.run()
    print(t1data)


if __name__ == "__main__":
    main()
