
import Tree
import random
from queue import Queue

def test_constraint(root):
    nodes = Queue()
    nodes.put(root)

    while (not nodes.empty()):
        currentNode = nodes.get()
        if (currentNode == None):
            continue

        # Verifica se o nó à esqueda é menor que o nó pai.
        if (currentNode.leftBranch != None):
            assert(currentNode.value > currentNode.leftBranch.value)
            assert(currentNode.leftBranch.parent == currentNode)
            nodes.put(currentNode.leftBranch)

        # Verifica se o nó à direita é menor que o nó pai.
        if (currentNode.rightBranch != None):
            assert(currentNode.value <= currentNode.rightBranch.value)
            assert(currentNode.rightBranch.parent == currentNode)
            nodes.put(currentNode.rightBranch)

    return True

def test_tree(tree):
    print("Testing the tree...", end="")
    result = test_constraint(tree.root)
    print("✔ - Ok" if result else "✗ - Fail")

mock = list(set([random.uniform(-10E9, 10E9) for i in range(1000)]))

tree = Tree.Tree()

print("Adding data:".ljust(50, "."), end=" ")
for value in mock:
    tree.add(value)
test_tree(tree)


print("Removing data from 0 to -500:".ljust(50, "."), end=" ")
for value in list(mock)[:-500]:
    tree.delete(value)
test_tree(tree)


print("Adding 100 itens:".ljust(50, ".").ljust(50, "."), end=" ")
for value in mock[: 100]:
    tree.add(value)
test_tree(tree)


print("Removing the 100 itens added:".ljust(50, "."), end=" ")
for value in list(mock)[:100]:
    tree.delete(value)
test_tree(tree)

print("Removing remaining itens:".ljust(50, "."), end=" ")
for value in list(mock)[-500:]:
    tree.delete(value)
test_tree(tree)

assert((tree.root == None))