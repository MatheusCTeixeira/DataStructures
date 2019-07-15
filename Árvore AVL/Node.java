

public class Node {
    private Node leftBranch = null;
    private Node rightBranch = null;
    private Node parent = null;

    private int heigth;

    private int value;

    public Node(int value) {
        this.value = value;

        leftBranch = null;
        rightBranch = null;
        parent = null;
        heigth = 0;

        if (parent == null) parent = null;
    }

    @Override
    public String toString() {
        int left  = (leftBranch != null) ? leftBranch.getValue() : -1;
        int right = (rightBranch != null) ? rightBranch.getValue() : -1;
        return "<"+left+">("+this.value+")<"+right+">";
    }

    public void setLeftChild(Node leftChild) {
        if (leftChild != null) leftChild.parent = this;

        leftBranch = leftChild;
    }

    public void setAsRoot() {
        parent = null;
    }

    public void setRightChild(Node rightChild) {
        if (rightChild != null) rightChild.parent = this;

        rightBranch = rightChild;
    }

    public Node getLeftChild() {
        return leftBranch;
    }

    public Node getParent() {
        return parent;
    }

    public Node getRightChild() {
        return rightBranch;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getHeigth() {
        return heigth;
    }

    public boolean isLeaf() {
        return (leftBranch == null && rightBranch == null);
    }
}