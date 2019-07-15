
import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

class Test {

    static Set<Integer> getMock(int size, int from, int to) {
        Set<Integer> mock = new HashSet<Integer>();
        Random r = new Random();
        while (mock.size() < size)
            mock.add(r.nextInt(to-from) + from);

        return mock;
    }

    static boolean testIfAllValuesArePresent(Tree tree, Set<Integer> dataInserted) {

        for (Integer data : dataInserted) {
            if (!tree.isPresent(data)) return false;
        }

        return true;
    }

    static boolean testBalancement(Tree tree) {
        ArrayList<Node> nodes = new ArrayList();
        nodes.add(tree.getRoot());

        while (!nodes.isEmpty()) {
            Node node = nodes.remove(0);
            int nodeHeight = node.getHeigth();

            Node leftChild = node.getLeftChild();
            Node rightChild = node.getRightChild();

            int leftHeight  = leftChild  != null ? leftChild.getHeigth()  : 0;
            int rightHeight = rightChild != null ? rightChild.getHeigth() : 0;

            if (Math.abs(leftHeight - rightHeight) > 1)
                return false;
        }

        return true;
    }

    public static void main(String args[]) {
        boolean result = true;
        while (result) {
            Set<Integer> mock = getMock(1000, -10000000, 10000000);
            System.out.println("Adding data...");

            Tree tree = new Tree();
            for (Integer data : mock) {
                tree.add(data);
            }
            System.out.println("Data added.");
            System.out.println("Testing data...");

            result = testIfAllValuesArePresent(tree, mock);
            result = result && testBalancement(tree);

            System.out.println("Test passed! Ok");
        }
        System.out.println("Error found!");
    }
}