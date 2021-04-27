"""
class   Deep Learning & applications, Practice #2
file    task3.py
author  KWONMINSUK(minshogi@gmail.com)  2014003927
date    2021.04.24(sat)
"""

from network import Network


class task3:
    def __init__(self, data):
        self.networkDesign = self.networkDesigner()
        self.myNetwork = Network(data, self.networkDesign)

    def networkDesigner(self):
        ret = {}
        ret['layerSize'] = 2
        ret['unitSize'] = [3, 1]
        ret['alpha'] = 0.6
        return ret

    def run(self):
        return self.myNetwork.run()
