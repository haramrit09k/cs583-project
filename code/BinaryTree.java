package code;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    Node root;
    HashMap<Node, Node> childParentMapping;
    private Comparator<Integer> comparator;
    int height;


    public BinaryTree(int data){
        Node root = new Node(data);
        if(this.root == null && root != null){
            this.root = root;
        }
        childParentMapping = new HashMap<Node, Node>();
        childParentMapping.put(root, null);

        this.height = height(root);
    }

    public void updateInterval(Node node){
        Node temp = node;
        while(temp != null){
            node.startInterval = temp.value;
            temp = temp.left;
        }

        temp = node;
        while (temp != null) {
            node.endInterval = temp.value;
            temp = temp.right;
        }
    }

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

        updateInterval(root);
        
        return root;
    }

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
        else{
            Node oldRight = x.right;
            x.right = oldRight.left;
            childParentMapping.put(oldRight.left, x);

            if(childParentMapping.get(x) == null){
                root = oldRight;
                if(oldRight!= null){
                    childParentMapping.put(oldRight, null);
                }
            }
            else if(childParentMapping.get(x).left == x){
                childParentMapping.get(x).left = oldRight;
                if(oldRight!= null){
                    childParentMapping.put(oldRight, childParentMapping.get(x));
                }        }
            else{
                childParentMapping.get(x).right = oldRight;
                if(oldRight!= null){
                    childParentMapping.put(oldRight, childParentMapping.get(x));
                }        }
            oldRight.left = x;
            childParentMapping.put(x, oldRight);

            updateInterval(x);
            updateInterval(oldRight);
        }
    }


    public void rightRotation(Node x){
        if(x.left == null){
            return;
        }
        else{
            Node oldLeft = x.left;
            x.left = oldLeft.right;
            childParentMapping.put(oldLeft.right, x);
    
            if(childParentMapping.get(x) == null){
                root = oldLeft;
                if(oldLeft != null){
                    childParentMapping.put(oldLeft, null);
                }
            }
            else if(childParentMapping.get(x).right == x){
                childParentMapping.get(x).right = oldLeft;
                if(oldLeft!= null){
                    childParentMapping.put(oldLeft, childParentMapping.get(x));
                }
            }
            else{
                childParentMapping.get(x).left = oldLeft;
                if(oldLeft!= null){
                    childParentMapping.put(oldLeft, childParentMapping.get(x));
                }        }
            oldLeft.right = x;
            childParentMapping.put(x, oldLeft);

            updateInterval(x);
            updateInterval(oldLeft);
        }
    }

    public void printInterval(Node node){
        System.out.println("Node interval for node "+node.value+" is ("+node.startInterval+", "+node.endInterval+")\n");
    }

    private static int count(Node tree)
    {
      int c =  1;             
      if (tree == null)
          return 0;
      else
      {
          c += count(tree.left);
          c += count(tree.right);
          return c;
      }
    }
  
    private static int height(Node root){
        if(root == null){
            return 0;
        }
        else{
            return  1 + Math.max(height(root.left), height(root.right));
        }
    }

    private static int calculateH(Node root){
        if(root == null){
            return 0;
        }
        else{
            int count = count(root);
            return (int) Math.floor(Math.log(count) / Math.log(2)) + 1;
        }
    }

    public int findRoot(Node root){
        // int h = height(root); // find height
        int h = calculateH(root);
        int n = count(root); // find num of vertices

        System.out.println("height = "+h);
        System.out.println("count = "+n);
        System.out.println();
       
        int twoHMinusOne = (int) Math.pow(2, h-1);
        int twoHMinusTwo = (int) Math.pow(2, h-2);
        int twoH = (int) Math.pow(2, h);
        
        double result = 0;

        System.out.println("twoHMinusOne = "+twoHMinusOne);
        System.out.println("twoHMinusOne = "+twoHMinusTwo);
        System.out.println("twoH = "+twoH);

        if(n >= twoHMinusOne && n <= twoHMinusOne + twoHMinusTwo - 2){
            System.out.println("condition 1 exec");
            result = (n - twoHMinusTwo + 1);
        }
        else if(twoHMinusOne + twoHMinusTwo - 1 <= n && n <= twoH - 1){
            System.out.println("condition 2 exec");
            result = twoHMinusOne;
        }
        System.out.println("*_*_*_*_*_*_*_*_*_rootT = "+result);
        return (int) result;
      }

    public void buildLeftForearm(Node root)
  { 
    Node walkerLeft = root.left;
    while(walkerLeft.left != null){
        rightRotation(walkerLeft);
        walkerLeft = root.left;
    }

    walkerLeft = root.left;
    while(walkerLeft != null){
        if(walkerLeft.left != null){
            rightRotation(walkerLeft);
            walkerLeft = walkerLeft.right;
        }
        else{
            walkerLeft = walkerLeft.right;
        }
    }
  }

    public void buildRightForearm(Node root)
    { 
        Node walkerRight = root.right;
        while(walkerRight.right != null){
            leftRotation(walkerRight);
            walkerRight = root.right;
        }
    walkerRight = root.right;
    while(walkerRight != null){
        if(walkerRight.right != null){
            leftRotation(walkerRight);
            walkerRight = walkerRight.left;
        }
        else{
            walkerRight = walkerRight.left;
        }
    }
    }  

    public void printInOrderTraversal(Node root){
        if(root == null){
            return;
        }
        printInOrderTraversal(root.left);
        System.out.print(root.value +" ");
        printInOrderTraversal(root.right);
    }


    public static void main(String[] args) {
        // BinaryTree bt = new BinaryTree(2);
        // bt.insert(bt.root,4);
        // bt.insert(bt.root,7);
        // bt.insert(bt.root,1);
        // bt.insert(bt.root,3);
        // bt.insert(bt.root,6);
        // // bt.insert(bt.root,9);
        // bt.insert(bt.root,5);
        // // bt.insert(bt.root,8);
        // // bt.insert(bt.root,10);

        BinaryTree bt = new BinaryTree(2);
        bt.insert(bt.root, 1);
        bt.insert(bt.root, 5);
        bt.insert(bt.root, 3);
        bt.insert(bt.root, 6);
        bt.insert(bt.root, 4);

        bt.printInterval(bt.findNode(2));
        bt.printInterval(bt.findNode(1));
        bt.printInterval(bt.findNode(5));
        bt.printInterval(bt.findNode(3));
        bt.printInterval(bt.findNode(6));
        bt.printInterval(bt.findNode(4));
        
        System.out.println("INORDER TRAVERSAL IS:::-;-;-;-;-;-;-;-;");
        bt.printInOrderTraversal(bt.root);
        System.out.println("\n");

        bt.rightRotation(bt.findNode(5));

        System.out.println("AFTER ROTATION::::::::::::::::::::::::::");
        bt.printInterval(bt.findNode(2));
        bt.printInterval(bt.findNode(1));
        bt.printInterval(bt.findNode(5));
        bt.printInterval(bt.findNode(3));
        bt.printInterval(bt.findNode(6));
        bt.printInterval(bt.findNode(4));
        
        System.out.println("INORDER TRAVERSAL IS:::-;-;-;-;-;-;-;-;");
        bt.printInOrderTraversal(bt.root);
        System.out.println("\n");
        
        System.out.println("Root is equal to :::::::"+bt.root.value);
        bt.printLevelOrder(bt.root);

        // find rootT
        int rootTval = bt.findRoot(bt.root);

        Node rootT = bt.findNode(rootTval);
        
        // get rootT to the top
       while(bt.childParentMapping.get(rootT) != null){
           
            // if left child, then right rotation
            if(rootT.value < bt.childParentMapping.get(rootT).value){
                // right rotation
                bt.rightRotation(bt.childParentMapping.get(rootT));
                System.out.println("Performed right rotation");
            }
            // else if right child, then left rotation
            else if(rootT.value > bt.childParentMapping.get(rootT).value){
                // left rotation
                bt.leftRotation(bt.childParentMapping.get(rootT));
                System.out.println("Performed left rotation");
            }
            bt.printLevelOrder(bt.root);
            
        }

        // build left and right forearms
        bt.buildLeftForearm(bt.root);
        bt.buildRightForearm(bt.root);

        System.out.println("Tree after forearm building:\n:::::\n");
        bt.printLevelOrder(bt.root);

        // building left subtree AL
        Node walker = bt.root.left;
        Node walkerRight = walker.right.right;
        Node follower = walker;

        while(walkerRight != null){
            while(walker != null && walker.right != null){
                if(walkerRight != null){
                    walkerRight = walker.right.right;
                }
                bt.leftRotation(follower);
                walker = walkerRight;
                follower = walker;
            }
            walker = bt.root.left;
            walkerRight = walker.right.right;
            follower = walker;
        }

        System.out.println("After left subtree generation:\n");
        bt.printLevelOrder(bt.root);

        // building right subtree AR
        walker = bt.root.right;
        Node walkerLeft = walker.left.left;
        follower = walker;

        while(walkerLeft != null){
            while(walker != null && walker.left != null){
                if(walkerLeft != null){
                    walkerLeft = walker.left.left;
                }
                bt.rightRotation(follower);
                walker = walkerLeft;
                follower = walker;
            }
            walker = bt.root.right;
            walkerLeft = walker.left.left;
            follower = walker;
        }

        System.out.println("After right subtree generation:\n");
        bt.printLevelOrder(bt.root);

    }
}