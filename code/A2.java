package code;
import java.util.*;

public class A2 extends BinaryTree{
    public A2(int data) {
        super(data);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        BinaryTree s = new BinaryTree(12);
        s.insert(s.root, 8);
        s.insert(s.root, 13);
        s.insert(s.root, 4);
        s.insert(s.root, 10);
        s.insert(s.root, 2);
        s.insert(s.root, 7);
        s.insert(s.root, 9);
        s.insert(s.root, 11);
        s.insert(s.root, 1);
        s.insert(s.root, 3);
        s.insert(s.root, 6);
        s.insert(s.root, 5);

        int n = 31;

        // List<Integer> BinaryTreeNodes = new ArrayList<Integer>(Arrays.asList(12, 1, 4, 10, 17, 8, 3, 13, 20, 7, 14, 2, 11, 9, 16, 6, 18, 15, 19, 5));
        
        List<Integer> BinaryTreeNodes = new ArrayList<Integer>();

        for(int i = 1; i<=n; i++){
            BinaryTreeNodes.add(i);
        }
        System.out.println(BinaryTreeNodes.toString());
        
        Collections.shuffle(BinaryTreeNodes);
        System.out.println(BinaryTreeNodes.toString());
        
        BinaryTree a = new BinaryTree(BinaryTreeNodes.get(0));
        for (int i = 1; i< BinaryTreeNodes.size(); i++) {
            a.insert(a.root, BinaryTreeNodes.get(i));
        }

        BinaryTree t = new BinaryTree(8);
        t.insert(t.root, 4);
        t.insert(t.root, 12);
        t.insert(t.root, 2);
        t.insert(t.root, 6);
        t.insert(t.root, 10);
        t.insert(t.root, 13);
        t.insert(t.root, 1);
        t.insert(t.root, 3);
        t.insert(t.root, 5);
        t.insert(t.root, 7);
        t.insert(t.root, 9);
        t.insert(t.root, 11);

        // BinaryTree s = new BinaryTree(4);
        // s.insert(s.root, 3);
        // s.insert(s.root, 7);
        // s.insert(s.root, 2);
        // s.insert(s.root, 6);
        // s.insert(s.root, 9);
        // s.insert(s.root, 1);
        // s.insert(s.root, 5);
        // s.insert(s.root, 8);
        // s.insert(s.root, 10);

        System.out.println("The tree contains the following nodes: " + s.ListifyTree(s.root).toString());

        Set<Integer> nodeSet = s.ListifyTree(s.root);

        List<Integer> notRotateList = new ArrayList<Integer>();
        for (Integer integer : nodeSet) {
            if (isIntervalSame(s.findNode(integer), t.findNode(integer))) {
                // add to notRotate list
                notRotateList.add(integer);
            }
        }
        System.out.println("THE NOT ROTATE LIST IS AS FOLLOWS");
        System.out.println(notRotateList.toString());
        System.out.println();
        System.out.println();
        System.out.println();

        List<Integer> maxEqSubtreeList = new ArrayList<Integer>();

        for (Integer integer : notRotateList) {
            // if parent of element in notrotatelist found in notrotatelist, remove the
            // element
            // since it won't be contributing to the max eq subtree
            if (notRotateList.contains(s.childParentMapping.get(s.findNode(integer)).value)) {
                continue;
            } else {
                maxEqSubtreeList.add(integer);
            }
        }

        System.out.println("THE NOT ROTATE LIST FOR MAX EQ SUBTREES IS AS FOLLOWS");
        System.out.println(maxEqSubtreeList.toString());
        System.out.println();
        System.out.println();
        System.out.println();

        List<Integer> maxIdSubtreeList = new ArrayList<Integer>();

        for(Integer integer : maxEqSubtreeList){
            if(identicalTrees(s.findNode(integer), t.findNode(integer))){
                maxIdSubtreeList.add(integer);
            }
        }

        System.out.println("THE NOT ROTATE LIST FOR MAX ID SUBTREES IS AS FOLLOWS");
        System.out.println(maxIdSubtreeList.toString());
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("CHILD PARENT MAPPING FOR S:**********");
        for (Node node : s.childParentMapping.keySet()) {
            if(s.childParentMapping.get(node) == null){
                System.out.println(node.value + " => " + null);
            }
            else{
            System.out.println(node.value + " => " + s.childParentMapping.get(node).value);
            }
        }

        // For A2:
        // when building forearms, ignore nodes in maxIdSubtreeList

        // find rootT
        int rootTval = s.findRoot(s.root);

        Node rootT = s.findNode(rootTval);

        // get rootT to the top
        while (s.childParentMapping.get(rootT) != null) {

            // if left child, then right rotation
            if (rootT.value < s.childParentMapping.get(rootT).value) {
                // right rotation
                s.rightRotation(s.childParentMapping.get(rootT));
                System.out.println("Performed right rotation");
            }
            // else if right child, then left rotation
            else if (rootT.value > s.childParentMapping.get(rootT).value) {
                // left rotation
                s.leftRotation(s.childParentMapping.get(rootT));
                System.out.println("Performed left rotation");
            }
            s.printLevelOrder(s.root);

        }

        // build left and right forearms
        s.buildLeftForearmIdenticalSubtrees(s.root, maxIdSubtreeList);
        s.buildRightForearmIdenticalSubtrees(s.root, maxIdSubtreeList);

        System.out.println("Tree after forearm building:\n:::::\n");
        s.printLevelOrder(s.root);
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");

        // ************** Building left subtree AL ************** 
        Node walker = s.root.left;
        Node follower = walker;
        Node countWalker = s.root.left;

        int countLeftForearm = 0;
        // find count of nodes in left forearm
        while (countWalker != null) {
            countLeftForearm += 1;
            countWalker = countWalker.right;
        }
        // then find height of left forearm using formula calculateH
        int heightLeftForearm = (int) Math.floor(Math.log(countLeftForearm) / Math.log(2));
        countWalker = s.root.left;

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

                if(follower.left != null){
                    if(!maxIdSubtreeList.contains(follower.left.value) && !maxIdSubtreeList.contains(follower.right.value)){
                        s.leftRotation(follower);
                    }
                }
                else {
                    if(!maxIdSubtreeList.contains(follower.right.value)){
                        s.leftRotation(follower);
                    }
                } 
                
                s.printLevelOrder(s.root);
                follower = walker;
            }

            // if value exceeds count - height, reinitialize walker and follower
            walker = s.root.left;
            follower = walker;
            countWalker = s.root.left;
        }

        System.out.println("After left subtree generation:\n");
        s.printLevelOrder(s.root);

        // ************** Building right subtree AR ******************
        walker = s.root.right;
        follower = walker;
        countWalker = s.root.right;

        int countRightForearm = 0;
        // find count of nodes in left forearm
        while (countWalker != null) {
            countRightForearm += 1;
            countWalker = countWalker.left;
        }
        // then find height of left forearm using formula calculateH
        int heightRightForearm = (int) Math.floor(Math.log(countRightForearm) / Math.log(2));
        countWalker = s.root.right;

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
                if (countDummy > countRIteration) {
                    break;
                }
                if(walker.left.left == null){
                    walker = walker.left;
                }
                else{
                    walker = walker.left.left;
                }
                if(follower.right != null){
                    if(!maxIdSubtreeList.contains(follower.left.value) && !maxIdSubtreeList.contains(follower.right.value)){
                        s.rightRotation(follower);
                    }
                }
                else{
                    if(!maxIdSubtreeList.contains(follower.left.value)){
                        s.rightRotation(follower);
                    }
                } 
                s.printLevelOrder(s.root);
                follower = walker;
            }

            // if value exceeds count - height, reinitialize walker and follower
            walker = s.root.right;
            follower = walker;
            countWalker = s.root.right;
        }


        System.out.println("After right subtree generation:\n");
        s.printLevelOrder(s.root);


        System.out.println("The number of rotations are: "+ s.countRotations);
        // check if s and t are identical (after A1)


    }
}
