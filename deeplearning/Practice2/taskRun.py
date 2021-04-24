"""
class   Deep Learning & applications, Practice #2
file    taskRun.py
author  KWONMINSUK(minshogi@gmail.com)  2014003927
date    2021.04.24(sat)
"""

from datagen import datagen
from task1 import task1
from task2 import task2
from task3 import task3


def main():
    m, n, k = map(int, input('m, n, k: ').split())
    data = datagen(m, n, k)
    t1 = task1(data)
    t1data = t1.run()
    print('task1: \n', t1data)

    t2 = task2(data)
    t2data = t2.run()
    print('task2: \n', t2data)

    t3 = task3(data)
    t3data = t3.run()
    print('task3: \n', t3data)


if __name__ == "__main__":
    main()
