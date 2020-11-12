package code;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    Node root;
    HashMap<Node, Node> childParentMapping;
    private Comparator<Integer> comparator;



    public BinaryTree(int data){
        Node root = new Node(data);
        if(this.root == null && root != null){
            this.root = root;
        }
        childParentMapping = new HashMap<Node, Node>();
        childParentMapping.put(root, null);
    }

    // public void createBinaryTree(){
    //     Node first = new Node(1);
    //     Node second = new Node(2);
    //     Node third = new Node(3);
    //     Node fourth = new Node(4);
    //     Node fifth = new Node(5);

    //     root = first;
    //     first.left = second;
    //     first.right = third;

    //     second.left = fourth;
    //     second.right = fifth;
    // }

    public Node insert(Node root, int data){
        if(root == null){
            root = new Node(data);
        }
        else{
            if(data <= root.value){
                root.left = insert(root.left, data);
                childParentMapping.put(root.left, root);
            }
            else{
                root.right = insert(root.right, data);
                childParentMapping.put(root.right, root);
            }
        }
        return root;
    }

    // public void insertNode(Node root, int data){
    // Queue<Node> q = new LinkedList<Node>();
    // q.add(root);
    //     while(!q.isEmpty()){
    //         Node temp = q.poll();

    //         if(temp.left != null){
    //             temp.left = new Node(data);
    //             break;
    //         }
    //         else{
    //             q.add(temp.left);
    //         }

    //         if(temp.right != null){
    //             temp.right = new Node(data);
    //             break;
    //         }
    //         else{
    //             q.add(temp.right);
    //         }
    //     }
    // }

    public void printLevelOrder(Node root){
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while(!q.isEmpty()){
            Node temp = q.poll();
            System.out.print(temp.value+" ");

            if(temp.left != null){
                q.add(temp.left);
            }
            
            if(temp.right != null){
                q.add(temp.right);
            }
        }
        System.out.println("\n");
    }

    protected int compare(Integer x, Integer y) {
        if (comparator == null) {
            return ((Comparable<Integer>) x).compareTo(y);
        } else {
            return comparator.compare(x, y);
        }
    }

    public Node findNode(int val){
        for (Node n = root; n != null;) {
            int comparisonResult = compare(val, n.value);
            if (comparisonResult == 0) {
                return n;
            } else if (comparisonResult < 0) {
                n = n.left;
            } else {
                n = n.right;
            }
        }
        return null;
    }

    public void leftRotation(Node x){
        if(x.right == null){
            return;
        }
        Node oldRight = x.right;
        x.right = oldRight.left;
        childParentMapping.put(oldRight.left, x);

        if(childParentMapping.get(x) == null){
            root = oldRight;
        }
        else if(childParentMapping.get(x).left == x){
            childParentMapping.get(x).left = oldRight;
            childParentMapping.put(oldRight, childParentMapping.get(x));
        }
        else{
            childParentMapping.get(x).right = oldRight;
            childParentMapping.put(oldRight, childParentMapping.get(x));
        }
        oldRight.left = x;
        childParentMapping.put(x, oldRight);
    }

    public void rightRotation(Node x){
        if(x.left == null){
            return;
        }
        Node oldLeft = x.left;
        x.left = oldLeft.right;
        childParentMapping.put(oldLeft.right, x);

        if(childParentMapping.get(x) == null){
            root = oldLeft;
        }
        else if(childParentMapping.get(x).right == x){
            childParentMapping.get(x).right = oldLeft;
            childParentMapping.put(oldLeft, childParentMapping.get(x));
        }
        else{
            childParentMapping.get(x).left = oldLeft;
            childParentMapping.put(oldLeft, childParentMapping.get(x));
        }
        oldLeft.right = x;
        childParentMapping.put(x, oldLeft);
    }


    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree(2);
        System.out.println("Root is equal to :::::::"+bt.root.value);

        bt.insert(bt.root, 1);
        bt.insert(bt.root, 3);
        bt.insert(bt.root, 5);
        bt.insert(bt.root, 4);
        bt.insert(bt.root, 6);
        
        bt.printLevelOrder(bt.root);

        // print childParentMapping (before rotation)
        System.out.println("**************BEFORE LEFT ROTATION**************");
        for (Node node : bt.childParentMapping.keySet()) {
            if(bt.childParentMapping.get(node) == null){
                System.out.println(node.value + " => " + null);
            }
            else{
            System.out.println(node.value + " => " + bt.childParentMapping.get(node).value);
            }
        }

        bt.printLevelOrder(bt.root);

        Node nodeToRotateAlong = bt.findNode(3);
        System.out.println(nodeToRotateAlong.value);
        if(nodeToRotateAlong!=null){
            bt.leftRotation(nodeToRotateAlong);
        }

        // print childParentMapping (after rotation)
        System.out.println("**************AFTER LEFT ROTATION**************");
        for (Node node : bt.childParentMapping.keySet()) {
            if(bt.childParentMapping.get(node) == null){
                System.out.println(node.value + " => " + null);
            }
            else{
            System.out.println(node.value + " => " + bt.childParentMapping.get(node).value);
            }
        }

        bt.printLevelOrder(bt.root);

        nodeToRotateAlong = bt.findNode(5);
        System.out.println(nodeToRotateAlong.value);
        if(nodeToRotateAlong!=null){
            bt.rightRotation(nodeToRotateAlong);
        }

        // print childParentMapping (after rotation)
        System.out.println("**************AFTER RIGHT ROTATION**************");
        for (Node node : bt.childParentMapping.keySet()) {
            if(bt.childParentMapping.get(node) == null){
                System.out.println(node.value + " => " + null);
            }
            else{
            System.out.println(node.value + " => " + bt.childParentMapping.get(node).value);
            }
        }

        bt.printLevelOrder(bt.root);

    }
}
