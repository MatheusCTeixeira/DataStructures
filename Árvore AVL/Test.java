
import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

class Test {

    static List<Integer> getMock(int size, int from, int to) {
        Set<Integer> mock = new HashSet<Integer>();
        Random r = new Random();
        while (mock.size() < size)
            mock.add(r.nextInt(to-from) + from);

        ArrayList<Integer> mockAsList = new ArrayList<>();
        mockAsList.addAll(mock);

        return mockAsList;
    }

    static boolean testIfAllValuesArePresent(Tree tree, List<Integer> dataInserted) {

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
        int times = 0;
        final int MAX_TIMES = 2000;
        final int MAX_RANDOM_DATA = 2000;

        System.out.print("Testing datastruct... ");
        while (result && times++ < MAX_TIMES) {
            // Gera valores aleatórios.
            List<Integer> mock =
                getMock(MAX_RANDOM_DATA, 0, Integer.MAX_VALUE);

            Tree tree = new Tree();

            // Insere os valores na árvore mantendo-a balanceada.
            for (Integer data : mock)
                tree.add(data);

            // Remove alguns valores da árvore.
            for (Integer data: mock.subList(0, 500)) {
                if (!tree.isPresent(data)) result = false;
                tree.delete(data);
            }

            // Testa se os valores que não foram removidos ainda estão na
            // árvore.
            result = result &&
                testIfAllValuesArePresent(tree, mock.subList(500, mock.size()));

            // Testa o balanceamento da árvore após a remoção de alguns valores.
            result = result &&
                testBalancement(tree);
        }

        if (result)
            System.out.println("All testes passed!");
        else
            System.out.println("My implementation has errors D:");
    }
}