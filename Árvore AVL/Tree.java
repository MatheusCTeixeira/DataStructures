
import java.util.ArrayList;

public class Tree {
    private Node root = null;

    public void print() {
        ArrayList<Node> nodes = new ArrayList();
        if (this.root != null) nodes.add(this.root);

        while (!nodes.isEmpty()) {
            Node currentNode = nodes.remove(0);

            System.out.print(currentNode + " ");

            if (currentNode.getLeftChild() != null)
                nodes.add(currentNode.getLeftChild());

            if (currentNode.getRightChild() != null)
                nodes.add(currentNode.getRightChild());
        }

        System.out.println();
    }

    public void add(int value) {
        // Adicione o novo nó e rebalance a árvore.
        Node newNode = new Node(value);
        addNode(this.root, newNode);

        // Atualiza a altura dos nós.
        updateHeigth(this.root);

        // Rebalancei o nó inserido, se necessário.
        balance(newNode);
    }

    private Node findNode(int value) {
        // Verifica se um nó está presente na árvore.
        Node temp = this.root;

        // Itera sobre os nós até encontrar o nó procurado ou até chegar
        // além de alguma folha.
        while (temp != null && temp.getValue() != value) {
            if (value < temp.getValue())
                temp = temp.getLeftChild();
            else if (value > temp.getValue())
                temp = temp.getRightChild();
        }

        return temp;
    }

    private Node getRightmostChild(Node node) {
        // Procura pelo nó mais à direita a partir de "node".
        Node temp = node;

        while (temp.getRightChild() != null)
            temp = temp.getRightChild();

        return temp;
    }

    private Node getLeftmostChild(Node node) {
        // Procura pelo nó mais à direita a partir de "node".
        Node temp = node;

        while (temp.getLeftChild() != null)
            temp = temp.getLeftChild();

        return temp;
    }

    public boolean isPresent(int value) {
        // Procura pelo Node.
        Node temp = findNode(value);

        // Se temp referenciar uma instancia de Node e o valor for igual ao
        // procurado, retorne true.
        return (temp != null && temp.getValue() == value);
    }

    public void delete(int value)  {
        Node target = findNode(value);
        Node toUpdate = null;
        if (target == null) return;

        Node targetParent = target.getParent();
        toUpdate = targetParent;

        // Há uns 10^8 casos possíveis. (Sarcasm detected!)
        // 1   - O nó a ser removido possui um filho à esquerda.
        // 1.1 - Esse filho possui filhos à direita. Neste caso pegue o filho
        //       mais à direita, chamado nó "rightmost". Faça que o pai de
        //       "rightmost" aponte para o filho à esquerda dele. E substitua
        //       "rightmost" no nó a ser removido.
        // 1.2 - Esse filho não possui filhos à direita. Neste caso apenas faça
        //       o pai do nó a ser removido apontar para o filho de nó que será
        //       removido.
        // 2   - O nó a ser removido possui um filho à direita.
        // 2.1 - Esse filho possui filhos à esquerda. Neste caso pegue o filho
        //       mais à esquerda, chamado nó "leftmost". Faça que o pai de
        //       "leftmost" aponte para o filho à direita dele. E substitua
        //       "leftmost"  no nó a ser removido.
        // 2.2 - Esse filho não possui filhos à esquerda. Portanto, apenas faça
        //       que o seu pai aponte para o seu filho à direita.
        // 3   - O nó a ser removido é uma folha e talvez a raíz, ao mesmo
        //       tempo. Neste caso substitua o ponteiro do pai para "null" e se
        //       ele for uma raíz, faça que ele aponte para o nó que o
        //       substitui.

        // Tem filho à esquerda (1.X).
        if (target.getLeftChild() != null) {
            Node targetLeftChild = target.getLeftChild();

            // O filho não tem filhos à direita (1.2).
            if (targetLeftChild.getRightChild() == null) {
                if (targetParent == null) {
                    root = target.getLeftChild();
                } else if (targetParent.getLeftChild() == target) {
                    targetParent.setLeftChild(targetLeftChild);
                } else {
                    targetParent.setRightChild(targetLeftChild);
                }

                targetLeftChild.setRightChild(target.getRightChild());

            } else {
                // O filho tem filhos à direita (1.1).
                Node rightmostChild = getRightmostChild(targetLeftChild);
                Node rightmostParent = rightmostChild.getParent();
                toUpdate = rightmostParent;

                // O filho mais à esqueda tem filhos à direita.
                if (rightmostParent.getLeftChild() == rightmostChild)
                    rightmostParent.setLeftChild(rightmostChild.getLeftChild());
                else
                    rightmostParent.setRightChild(rightmostChild.getLeftChild());

                // Substitui o nó mais à direita no destino.
                if (targetParent == null)
                    root = rightmostChild;
                else if (targetParent.getLeftChild() == target)
                    targetParent.setLeftChild(rightmostChild);
                else
                    targetParent.setRightChild(rightmostChild);

                rightmostChild.setLeftChild(target.getLeftChild());
                rightmostChild.setRightChild(target.getRightChild());
            }
        } else if (target.getRightChild() != null) {
            // O nó não possi filho à esquera, mas possui à direita (2.X).
            Node targetRightChild = target.getRightChild();

            // O filho não possui filho à esquerda (2.2).
            if (targetRightChild.getLeftChild() == null) {
                if (targetParent == null)
                    root = target.getRightChild();
                else if (targetParent.getLeftChild() == target)
                    targetParent.setLeftChild(targetRightChild);
                else
                    targetParent.setRightChild(targetRightChild);

               // targetRightChild.setLeftChild(target.getLeftChild());

            } else {
                // O filho possui filhos à esquerda (2.1).
                Node leftmostChild = getLeftmostChild(targetRightChild);
                Node leftmostParent = leftmostChild.getParent();
                toUpdate = leftmostParent;

                // O filho mais à esquerda do filho à direita possui filhos à
                // direita.
                if (leftmostParent.getLeftChild() == leftmostChild)
                    leftmostParent.setLeftChild(leftmostChild.getRightChild());
                else
                    leftmostParent.setRightChild(leftmostChild.getRightChild());

                // Substitui o nó mais a esquerda no destino.
                if (targetParent == null)
                    root = leftmostChild;
                else if (targetParent.getLeftChild() == target)
                    targetParent.setLeftChild(leftmostChild);
                else
                    targetParent.setRightChild(leftmostChild);

                leftmostChild.setLeftChild(target.getLeftChild());
                leftmostChild.setRightChild(target.getRightChild());
            }
        } else {
            // Caso o nó a ser removido seja uma folha (e, possivelmente, uma
            // raíz) (3).
            if (targetParent == null) {
                root = null;
            } else {
                if (targetParent.isLeftChild(target))
                    targetParent.setLeftChild(null);
                else
                    targetParent.setRightChild(null);
            }
        }

        // Mantém o conceito da àrvore AVL.
        if (root != null) {
            root.setAsRoot();
            updateHeigth(root);
            if (toUpdate == null) toUpdate = root;
            balance(toUpdate);
        }
    }


    /** Adiciona o nó como sendo filho de "parent" e retorna pai onde foi inse-
     *  rido.
     */
    public Node addNode(Node parent, Node newNode) {

        // Adiciona a raíz (o primeiro elemento da árvore).
        if (parent == null) {
            this.root = newNode;
            return this.root;
        }

        // Adiciona o filho à esqueda e atualiza a profundidade
        if (newNode.getValue() < parent.getValue()) {
            // Caso seja uma folha.
            if (parent.getLeftChild() == null) {
                parent.setLeftChild(newNode);
                return parent;
            } else {
                // Caso não seja uma folha, adicione-o e atualize a profundidade.
               return addNode(parent.getLeftChild(), newNode);
            }
        } else {
            // Caso seja uma folha.
            if (parent.getRightChild() == null) {
                parent.setRightChild(newNode);
                return parent;
            } else {
                // Caso não seja uma folha, adecione-o e atualize a profundidade.
                return addNode(parent.getRightChild(), newNode);
            }
        }
    }

    public Node getRoot() {
        // Retorna a raíz. Utilizado para fins de testes.
        return root;
    }

    private int updateHeigth(Node node) {
        // Caso seja raíz, a sua profundidade é 0, e, portanto, suba a àrvore
        // atualizando as alturas des seus parents. Caso contrário, desça a
        // àrvore até encontrar uma raíz e atualize a partir daí.
        if (node == null) return 0;

        if (node.getLeftChild() == null && node.getRightChild() == null) {
            // Neste caso é uma folha e a altura é 1.
            node.setHeigth(1);
            return 1;
        } else {
            // Calcula e atualuiza a altura dos nós descendentes recursivamente.
            int leftChildHeigth = updateHeigth(node.getLeftChild());
            int rightChildHeigth = updateHeigth(node.getRightChild());

            // A altura é igual a maior altura do seus filhos.
            int maxHeigth = Math.max(leftChildHeigth, rightChildHeigth);

            node.setHeigth(maxHeigth + 1);

            return maxHeigth + 1;
        }
    }

    private void balance(Node node) {
        // Balanceia um determinado nó, caso necessário.
        // Existe 4 formas de balanceamento:
        // -- Direita Direita  (DD): Difere do DE pelo fato do ramo à esquerda
        //      ser maior do que o ramo à direita do primeiro filho à esquerda.
        // -- Direita Esquerda (DE): Realiza um "ajuste" e chama o DD.
        // -- Esquerda Direita (ED): Realiza um "ajuste" e chama o EE.
        // -- Esquerda Esquerda(EE): Difere do ED pelo fato do ramo à direita
        //      ser maior do que o ramo à esquerda do primeiro filho à direita.
        if (node == null) return;

        if (node.isLeaf()) {

        } else if (isLeftRotationCondition(node)){
            leftRotation(node);
        } else if (isRightRotationCondition(node)) {
            rightRotation(node);
        } else if (isLeftRightRotationCondition(node)) {
            leftRightRotation(node);
        } else if (isRightLeftRotationCondition(node)) {
            rightLeftRotation(node);
        }

        balance(node.getParent());
    }

    private boolean isLeftRotationCondition(Node node) {
        // Obtém as referências.
        Node rightChild = node.getRightChild();
        Node leftChild  = node.getLeftChild();
        if (rightChild == null) return false;

        // Obtém os filhos do filho à direita.
        Node rigthRightChild = rightChild.getRightChild();
        Node rightLeftChild  = rightChild.getLeftChild();

        // Obtém a altura de cada um dos filhos do node.
        int     leftHeigth   = leftChild != null ? leftChild.getHeigth() : 0;
        int     rightHeight  = rightChild.getHeigth();

        // Detecta se a subárvore com raíz em "node" está desbalanceada.
        boolean isUnbalanced = (rightHeight - leftHeigth) > 1;

        // Obtém a altura de cada um dos filhos dos filho à direita de "node".
        int rightRightHeigth = rigthRightChild != null ?
                           rigthRightChild.getHeigth(): 0;

        int rightLeftHeigth = rightLeftChild != null ?
                           rightLeftChild.getHeigth(): 0;

        // Distingue entre uma rotação LL ou LR.
        boolean isLeftRotation = (rightRightHeigth - rightLeftHeigth) >= 0;

        return (isUnbalanced && isLeftRotation & rigthRightChild != null);
    }

    private boolean isRightRotationCondition(Node node) {
        // Obtém as referências.
        Node leftChild  = node.getLeftChild() ;
        Node rightChild = node.getRightChild();
        if (leftChild   == null) return false ;

        // Obtém os filhos do filho à esquerda.
        Node leftLeftChild = leftChild.getLeftChild();
        Node leftRightChild = leftChild.getRightChild();

        // Obtém a altura de cada um dos filhos do node.
        int     rightHeight  = rightChild != null ? rightChild.getHeigth() : 0;
        int     leftHeigth   = leftChild.getHeigth();

        // Detecta se a subárvore com raíz em "node" está desbalanceada.
        boolean isUnbalanced = (leftHeigth - rightHeight) > 1;

        // Obtém a altura de cada um dos filhos dos filho à esquerda de "node".
        int leftLeftHeight = leftLeftChild != null ?
                         leftLeftChild.getHeigth() : 0;

        int leftRightHeight = leftRightChild != null ?
                        leftRightChild.getHeigth() : 0;

        // Distingue entre uma rotação LL ou LR.
        boolean isLeftRotation = (leftLeftHeight - leftRightHeight) >= 0;

        return (isUnbalanced && isLeftRotation && leftLeftChild != null);
    }

    private boolean isLeftRightRotationCondition(Node node) {
        // Obtém as referências.
        Node leftChild  = node.getLeftChild() ;
        Node rightChild = node.getRightChild();
        if (leftChild   == null) return false ;

        // Obtém o filho à direita do filho à esquerda.
        Node leftRightChild = leftChild.getRightChild();

        // Calcula a altura dos filhos de "node".
        int     rightHeight  = rightChild != null ? rightChild.getHeigth() : 0;
        int     leftHeigth   = leftChild.getHeigth();

        // Verifica se a àrvore está desbalanceada.
        boolean isUnbalanced = (leftHeigth - rightHeight) > 1;

        return (isUnbalanced && leftRightChild != null);
    }

    private boolean isRightLeftRotationCondition(Node node) {
        // Obtém as referências.
        Node rightChild = node.getRightChild();
        Node leftChild = node.getLeftChild();
        if (rightChild == null) return false;

        // Obtém o filho à esquerda do filho à direita.
        Node rigthLeftChild = rightChild.getLeftChild();

        // Calcula a altura dos filhos de "node".
        int     leftHeigth   = leftChild != null ? leftChild.getHeigth() : 0;
        int     rightHeight  = rightChild.getHeigth();
        boolean isUnbalanced = (rightHeight - leftHeigth) > 1;

        return (isUnbalanced && rigthLeftChild != null);
    }

    private void leftRotation(Node node) {
        // Simplica as referências.
        Node parent          = node.getParent();
        Node rightChild      = node.getRightChild();

        // Mantém o status quo do restante da árvore, ie, mantem o nó que aponta
        // para "node" apontando para o nó que substitui "node" após o
        // balanceamento.
        if (parent == null) {                       // Quando for a raíz.
            root = rightChild;
            rightChild.setAsRoot();
        } else if (parent.getLeftChild() == node) { // Quando for filho a esque.
            parent.setLeftChild(rightChild);
        } else {
            parent.setRightChild(rightChild);       // Quando for filha à direi.
        }

        // Ajustas as referências.
        node.setRightChild(rightChild.getLeftChild());
        rightChild.setLeftChild(node);

        // O ajuste provavelmene vai alterar as alturas dos subnós de "node" e
        // dos seus antepassados.
        updateHeigth(this.root);
    }

    private void leftRightRotation(Node node) {
        // Simplica as referências.
        Node leftChild      = node.getLeftChild();
        Node leftRightChild = leftChild.getRightChild();

        // Lêia: DOMINIO/data_structures_algorithms/avl_tree_algorithm.htm
        // onde DOMINIO = https://www.tutorialspoint.com
        node.setLeftChild(leftRightChild);
        leftChild.setRightChild(leftRightChild.getLeftChild());
        leftRightChild.setLeftChild(leftChild);

        rightRotation(node);
    }

    private void rightRotation(Node node) {
        // Simplica as referências.
        Node parent    = node.getParent();
        Node leftChild = node.getLeftChild();

        // Mantém o status quo do restante da árvore, ie, mantem o nó que aponta
        // para "node" apontando para o nó que substitui "node" após o
        // balanceamento.
        if (parent == null) {                       // Quando for a raíz.
            root = leftChild;
            leftChild.setAsRoot();
        } else if (parent.getLeftChild() == node) { // Quando for filho à esque.
            parent.setLeftChild(leftChild);
        } else {
            parent.setRightChild(leftChild);        // Quando for filha à direi.
        }

        // Ajustas as referências.
        node.setLeftChild(leftChild.getRightChild());
        leftChild.setRightChild(node);

        // O ajuste provavelmene vai alterar as alturas dos subnós de "node" e
        // dos seus antepassados.
        updateHeigth(this.root);
    }

    private void rightLeftRotation(Node node) {
        // Simplica as referências.
        Node rightChild     = node.getRightChild();
        Node rightLeftChild = rightChild.getLeftChild();

        // Lêia: DOMINIO/data_structures_algorithms/avl_tree_algorithm.htm
        // onde DOMINIO = https://www.tutorialspoint.com
        node.setRightChild(rightLeftChild);
        rightChild.setLeftChild(rightLeftChild.getRightChild());
        rightLeftChild.setRightChild(rightChild);

        leftRotation(node);
    }


}