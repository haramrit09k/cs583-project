package code;

import java.util.*;

public class A1 extends BinaryTree{
   
    public A1(int data) {
        super(data);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
                
        // BinaryTree bt = new BinaryTree(2);
        // bt.insert(bt.root, 1);
        // bt.insert(bt.root, 5);
        // bt.insert(bt.root, 3);
        // bt.insert(bt.root, 6);
        // bt.insert(bt.root, 4);

        // BinaryTree bt = new BinaryTree(4);
        // bt.insert(bt.root, 2);
        // bt.insert(bt.root, 7);
        // bt.insert(bt.root, 1);
        // bt.insert(bt.root, 3);
        // bt.insert(bt.root, 6);
        // bt.insert(bt.root, 9);
        // bt.insert(bt.root, 5);
        // bt.insert(bt.root, 8);
        // bt.insert(bt.root, 10);

        List<Integer> BinaryTreeNodes = new ArrayList<Integer>(Arrays.asList(4,2,7,1,3,6,9,5,8,10));
        BinaryTree bt = new BinaryTree(BinaryTreeNodes.get(0));
        for (int i = 1; i< BinaryTreeNodes.size(); i++) {
            bt.insert(bt.root, BinaryTreeNodes.get(i));
        }

        // BinaryTree bt = new BinaryTree(7);
        // bt.insert(bt.root, 4);
        // bt.insert(bt.root, 8);
        // bt.insert(bt.root, 2);
        // bt.insert(bt.root, 5);
        // bt.insert(bt.root, 12);
        // bt.insert(bt.root, 1);
        // bt.insert(bt.root, 3);
        // bt.insert(bt.root, 6);
        // bt.insert(bt.root, 11);
        // bt.insert(bt.root, 13);
        // bt.insert(bt.root, 9);
        // bt.insert(bt.root, 10);

        System.out.println("Root is equal to :::::::" + bt.root.value);
        bt.printLevelOrder(bt.root);


        // **************************************************************
        // *********************** A1 starts here ***********************
        // **************************************************************
        
                // find rootT
                int rootTval = bt.findRoot(bt.root);

                Node rootT = bt.findNode(rootTval);
        
                // get rootT to the top
                while (bt.childParentMapping.get(rootT) != null) {
        
                    // if left child, then right rotation
                    if (rootT.value < bt.childParentMapping.get(rootT).value) {
                        // right rotation
                        bt.rightRotation(bt.childParentMapping.get(rootT));
                        System.out.println("Performed right rotation");
                    }
                    // else if right child, then left rotation
                    else if (rootT.value > bt.childParentMapping.get(rootT).value) {
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
                System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        
                // ************** Building left subtree AL ************** 
                Node walker = bt.root.left;
                Node follower = walker;
                Node countWalker = bt.root.left;
        
                int countLeftForearm = 0;
                // find count of nodes in left forearm
                while (countWalker != null) {
                    countLeftForearm += 1;
                    countWalker = countWalker.right;
                }
                // then find height of left forearm using formula calculateH
                int heightLeftForearm = (int) Math.floor(Math.log(countLeftForearm) / Math.log(2));
                countWalker = bt.root.left;
        
                // for i from 0 to height - 1
                for (int i = 0; i <= heightLeftForearm; i++) {
                    int countLIteration = 0;
                    // find count for that iteration
                    while (countWalker != null) {
                        countLIteration += 1;
                        countWalker = countWalker.right;
                    }
        
                    // Alternate Rotation Policy
        
                    // only consider count - height nodes from top for rotation
                    int countDummy = i;
                    while (walker.right != null && countDummy < countLIteration) {
                        System.out.println("walker = " + walker.value);
                        System.out.println("follower = " + follower.value);
        
                        countDummy += 2;
                        if (countDummy > countLIteration) {
                            break;
                        }
                        if(walker.right.right == null){
                            walker = walker.right;
                        }
                        else{
                            walker = walker.right.right;
                        }
                        bt.leftRotation(follower);
                        bt.printLevelOrder(bt.root);
                        follower = walker;
                    }
        
                    // if value exceeds count - height, reinitialize walker and follower
                    walker = bt.root.left;
                    follower = walker;
                    countWalker = bt.root.left;
                }
        
                System.out.println("After left subtree generation:\n");
                bt.printLevelOrder(bt.root);
        
                // ************** Building right subtree AR ******************
                walker = bt.root.right;
                follower = walker;
                countWalker = bt.root.right;
        
                int countRightForearm = 0;
                // find count of nodes in left forearm
                while (countWalker != null) {
                    countRightForearm += 1;
                    countWalker = countWalker.left;
                }
                // then find height of left forearm using formula calculateH
                int heightRightForearm = (int) Math.floor(Math.log(countRightForearm) / Math.log(2));
                countWalker = bt.root.right;
        
                // for i from 0 to height - 1
                for (int i = 0; i <= heightRightForearm; i++) {
                    int countRIteration = 0;
                    // find count for that iteration
                    while (countWalker != null) {
                        countRIteration += 1;
                        countWalker = countWalker.left;
                    }
        
                    // Alternate Rotation Policy
                    // only consider count - height nodes from top for rotation
                    int countDummy = i;
                    while (walker.left != null && countDummy < countRIteration) {
                        System.out.println("walker = " + walker.value);
                        System.out.println("follower = " + follower.value);
        
                        countDummy += 2;
                        if (countDummy >= countRIteration) {
                            break;
                        }
                        if(walker.left.left == null){
                            walker = walker.left;
                        }
                        else{
                            walker = walker.left.left;
                        }
                        bt.rightRotation(follower);
                        bt.printLevelOrder(bt.root);
                        follower = walker;
                    }
        
                    // if value exceeds count - height, reinitialize walker and follower
                    walker = bt.root.right;
                    follower = walker;
                    countWalker = bt.root.right;
                }
        
        
                System.out.println("After right subtree generation:\n");
                bt.printLevelOrder(bt.root);
        
        
    }
}
