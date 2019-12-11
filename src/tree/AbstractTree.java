package tree;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public abstract class AbstractTree<T extends Number> {
    protected Node<T> root;
    protected BinaryOperator<T> adder;
    protected Comparator<T> comparator;
    protected T zero;

    protected AbstractTree(BinaryOperator<T> adder, Comparator<T> comparator, T zero) {
        this.adder = adder;
        this.comparator = comparator;
        this.zero = zero;
    }

    public Node<T> getRoot() {
        return root;
    }

    /**
     *
     * @return number of nodes in tree
     */
    public int getSize() {
        return computeSizeOfSubtree(root);
    }

    public T getSum() {
        return computeSumOfSubtree(root);
    }

    abstract AbstractTree<T> removeSubtree(Node<T> rootSubTree);

    abstract AbstractTree<T> maximize(int k);

    abstract AbstractTree<T> maximize();

    private int computeSizeOfSubtree(Node<T> node) {
        int cnt = node.getChildren().size();
        for (var child : node.getChildren()) {
            cnt += computeSizeOfSubtree(child);
        }
        return cnt;
    }

    private T computeSumOfSubtree(Node<T> node) {
        T sum = root.getValue();
        for (var child : node.getChildren()) {
            sum = adder.apply(sum, computeSumOfSubtree(child));
        }
        return sum;
    }

    //логичнее было бы поставить тут статик?
    private boolean isNodeFoundInSubtree(Node<T> currentNode, Node<T> desired) {
        if (currentNode == desired) {
            return true;
        }
        for (var child : currentNode.getChildren()) {
            return isNodeFoundInSubtree(child, desired);
        }
        return false;
    }

    protected boolean isNodeFound(Node<T> desired) {
        return isNodeFoundInSubtree(root, desired);
    }


}
