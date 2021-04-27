"""
class   Deep Learning & applications, Practice #2
file    task1.py
author  KWONMINSUK(minshogi@gmail.com)  2014003927
date    2021.04.24(sat)
"""

from network import Network


class task1:
    def __init__(self, data):
        self.networkDesign = self.networkDesigner()
        self.myNetwork = Network(data, self.networkDesign)

    def networkDesigner(self):
        ret = {}
        ret['layerSize'] = 1
        ret['unitSize'] = [1]
        ret['alpha'] = 0.7
        return ret

    def run(self):
        return self.myNetwork.run()
