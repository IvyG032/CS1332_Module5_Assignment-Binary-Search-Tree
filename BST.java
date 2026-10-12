import java.util.NoSuchElementException;

/**
 * Your implementation of a Binary Search Tree.
 *
 * @author Zichao Guan
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not implement a constructor.
     */

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively, using pointer reinforcement.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        // TODO: Implement this method using a private recursive helper.
        if (data == null) {
            throw new IllegalArgumentException ("Data cannot be null.");
        }
        root = addHelper(root, data);
    }

    // implementation of helper method
    private BSTNode<T> addHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size ++;
            return new BSTNode<T>(data);
        }
        else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        }
        else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively, using pointer reinforcement.
     *
     * There are 3 cases to consider:
     * 1: The data is a leaf (0 children). In this case, simply remove it.
     * 2: The data has 1 child. In this case, replace it with its child.
     * 3: The data has 2 children. Use the successor to replace the data,
     *    NOT the predecessor.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        // TODO: Implement this method using private recursive helper(s).
        if (data == null) {
            throw new IllegalArgumentException ("Data cannot be null.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    // implementation of remove 
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            // data not found case
            throw new NoSuchElementException("Data cannot be found.");
        }
        else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        }
        else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        }
        // data found case
        else {
            size --;
            dummy.setData(curr.getData());
            // if 0 child
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;    // setting parent's pointer to null
            }
            // if 1 child, link grandparent and grandchild, skipping target node
            else if (curr.getLeft() == null) {
                return curr.getRight();
            }
            else if (curr.getRight() == null) {
                return curr.getLeft();
            }
            // if 2 child, remove successor
            else {
                // create a second dummy node to catch the returned node
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    // implementation of removing successor 
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            return curr.getRight(); // handles both 0 and 1 child chase by determining there's no left child
        } 
        else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2)); // reinforcing pointer
            return curr;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. DO NOT MODIFY THIS METHOD!
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. DO NOT MODIFY THIS METHOD!
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }
}