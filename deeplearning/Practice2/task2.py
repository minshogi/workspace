# class  Deep Learning & applications, Practice #1
# file   name practice1_2014003927.py
# author KWONMINSUK(minshogi@gmail.com)  2014003927
# date   2021/03/25

import network


class task2:
    def __init__(self, data):
        self.networkDesign = self.networkDesigner()
        self.network = network.Network(data, self.networkDesign)

    def networkDesigner(self):
        ret = {}
        ret['layerSize'] = 2
        ret['unitSize'] = [1, 1]
        ret['alpha'] = 0.88
        return ret

    def run(self):
        return self.network.run()
