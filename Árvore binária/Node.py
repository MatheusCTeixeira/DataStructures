# ---------------------------------------------------------------------------- #
#                                     Node                                     #
#                                                                              #
#                   Desenvolvido por Matheus Cândido Teixeira                  #
#                                                                              #
# ---------------------------------------------------------------------------- #

class Node:
    def __init__(self, value):
        # Cada branch conecta o nó pai ao nó filho. O leftBrach leva ao filho
        # cujo valor contido nele é menor que o valor contido no nó do pai
        # e o rightBrach para o caso do valor ser maior ou igual ao do nó pai.
        self.parent = None
        self.leftBranch = None
        self.rightBranch = None
        self.value = value

    def setLeftNode(self, node):
        self.leftBranch = node
        if (node): node.parent = self

    def setRightNode(self, node):
        self.rightBranch = node
        if (node): node.parent = self

    def getLeftNode(self):
        return self.leftBranch

    def getRightNode(self):
        return self.rightBranch

    def isLeaf(self):
        # Verifica se este nó é uma "folha", ie, não possui branchs.
        return self.leftBranch == None and self.rightBranch == None

    def getRightmostNode(self):
        # Retorna o nó filho mais à direita em relação a esse nó.
        rightmost = self

        while (rightmost.rightBranch != None):
            rightmost = rightmost.rightBranch

        return rightmost

    def getLeftmostNode(self):
        # Retorna o nó filho mais à esquerda em relação a esse nó.
        leftmost = self

        while (leftmost.leftBranch != None):
            leftmost = leftmost.leftBranch

        return leftmost

    def __str__(self):
        return "Branch to " + str(self.value)






