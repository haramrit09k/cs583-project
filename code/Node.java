package code;

/**
 * Node
 */
public class Node {
    
    int value;
    Node left;
    Node right;
    int startInterval;
    int endInterval;
 
    Node(int value) {
        this.value = value;
        right = null;
        left = null;
    }
}
