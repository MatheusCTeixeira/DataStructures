
# ---------------------------------------------------------------------------- #
#                                     Tree                                     #
#                                                                              #
#                   Desenvolvido por Matheus Cândido Teixeira                  #
#                                                                              #
# ---------------------------------------------------------------------------- #

from Node import Node
import queue

class Tree:
    def __init__(self):
        self.root = None
        # self._tall = 0

    def _findNode(self, value):
        # Procura na árvore a partir da raiz pelo nó que contém o valor passado
        # por parâmetro. Se não houver esse valor, retorne None.
        currentNode = self.root

        while (currentNode != None):
            if (currentNode.value == value):
                return currentNode

            if (value < currentNode.value):
                currentNode = currentNode.leftBranch
            else:
                currentNode = currentNode.rightBranch

        return None

    def add(self, value):
        # Adicione valores na árvore.

        # Mova na árvore até encontrar uma folha aonde se possa adicionar o
        # novo dado.
        oldNode, currentNode = None, self.root

        # Ao final da iteração a variável "oldNode" deve conter a referência de
        # uma folha. Se a árvore estiver vazia, "oldNode" deve ser igual à None.
        while (currentNode != None):
            if (currentNode != None):
                oldNode = currentNode

            if (value < currentNode.value):
                currentNode = currentNode.leftBranch
            else:
                currentNode = currentNode.rightBranch


        if (oldNode == None):                   # Root
            self.root = Node(value)
        elif (value < oldNode.value):           # Left node
            oldNode.setLeftNode(Node(value))
        else:                                   # Right node
            oldNode.setRightNode(Node(value))

    def delete(self, value):
        # Procura o nó a se deletar.
        currentNode = self._findNode(value)

        # Se nó for None (não foi encontrado), lance uma exceção.
        if (currentNode == None):
            raise "Value not found"

        # * Caso o nó a ser removido seja uma folha há duas possibilidades:
        #       1 - É uma folha ordinária.
        #       2 - Além de ser uma folha, é, também, a raiz.
        if (currentNode.isLeaf()):
            # Trata o primeiro caso (folha ordinária).
            if (currentNode.parent):
                parent = currentNode.parent
                if (parent.getLeftNode() == currentNode):
                    parent.setLeftNode(None)
                else:
                    parent.setRightNode(None)
            else:
            # Trata o segundo caso (folha e raíz).
                self.root = None
        # * Caso o nó seja um nó interno, há duas possibilidades:
        #       1 - Substituir pelo nó mais à direita, do filho à esquerda.
        #       2 - Substituir pelo nó mais à esquerda, do filho à direita.
        elif (currentNode.getLeftNode() != None): # Implementa a 1ª opção.
            rightmostNode = currentNode.getLeftNode().getRightmostNode()

            if (rightmostNode.parent != None):
                rightmostParent = rightmostNode.parent

                # Se o nó mais a direita do filho à esqueda for o próprio filho,
                # substitua esse nó pelo nó à esqueda do filho à esqueda. Caso
                # contrário, se o nó mais a direita não for filho do nó atual,
                # substitua filho à esqueda do seu pai (que antes apontava para
                # ele) para o seu filho à esqueda.
                if (rightmostNode != currentNode.getLeftNode()):
                    rightmostParent.setRightNode(rightmostNode.getLeftNode())
                else:
                    rightmostParent.setLeftNode(rightmostNode.getLeftNode())

            currentNode.value = rightmostNode.value

        elif (currentNode.getRightNode() != None): # Implementa a 2ª opção.
            leftmostNode = currentNode.getRightNode().getLeftmostNode()

            if (leftmostNode.parent != None):
                leftmostParent = leftmostNode.parent

                # Mesma razão que apresentado acima.
                if (leftmostNode != currentNode.getRightNode()):
                    leftmostParent.setLeftNode(leftmostNode.getRightNode())
                else:
                    leftmostParent.setRightNode(leftmostNode.getRightNode())

            currentNode.value = leftmostNode.value


    def print(self):
        # Exibe os nós presentes na árvore a partir da raíz.
        nodes = queue.Queue()
        nodes.put(self.root)

        while (not nodes.empty()):
            currentNode = nodes.get()
            if (currentNode == None):
                continue

            print(currentNode.value)

            if (currentNode.leftBranch != None):
                nodes.put(currentNode.leftBranch)

            if (currentNode.rightBranch != None):
                nodes.put(currentNode.rightBranch)
