package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BinaryTree {
    Node root;
    HashMap<Node, Node> childParentMapping;
    private Comparator<Integer> comparator;
    int height;

    public BinaryTree(int data) {
        Node root = new Node(data);
        if (this.root == null && root != null) {
            this.root = root;
        }
        childParentMapping = new HashMap<Node, Node>();
        childParentMapping.put(root, null);

        this.height = height(root);
    }

    public void updateInterval(Node node) {
        Node temp = node;
        while (temp != null) {
            node.startInterval = temp.value;
            temp = temp.left;
        }

        temp = node;
        while (temp != null) {
            node.endInterval = temp.value;
            temp = temp.right;
        }
    }

    public Node insert(Node root, int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            if (data <= root.value) {
                root.left = insert(root.left, data);
                childParentMapping.put(root.left, root);
            } else {
                root.right = insert(root.right, data);
                childParentMapping.put(root.right, root);
            }
        }

        updateInterval(root);

        return root;
    }

    public void printLevelOrder(Node root) {
        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        while (!q.isEmpty()) {
            Node temp = q.poll();
            System.out.print(temp.value + ", ");

            if (temp.left != null) {
                q.add(temp.left);
            }

            if (temp.right != null) {
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

    public Node findNode(int val) {
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

    public void leftRotation(Node x) {
        if (x.right == null) {
            return;
        } else {
            Node oldRight = x.right;
            x.right = oldRight.left;
            childParentMapping.put(oldRight.left, x);

            if (childParentMapping.get(x) == null) {
                root = oldRight;
                if (oldRight != null) {
                    childParentMapping.put(oldRight, null);
                }
            } else if (childParentMapping.get(x).left == x) {
                childParentMapping.get(x).left = oldRight;
                if (oldRight != null) {
                    childParentMapping.put(oldRight, childParentMapping.get(x));
                }
            } else {
                childParentMapping.get(x).right = oldRight;
                if (oldRight != null) {
                    childParentMapping.put(oldRight, childParentMapping.get(x));
                }
            }
            oldRight.left = x;
            childParentMapping.put(x, oldRight);

            updateInterval(x);
            updateInterval(oldRight);
        }
    }

    public void rightRotation(Node x) {
        if (x.left == null) {
            return;
        } else {
            Node oldLeft = x.left;
            x.left = oldLeft.right;
            childParentMapping.put(oldLeft.right, x);

            if (childParentMapping.get(x) == null) {
                root = oldLeft;
                if (oldLeft != null) {
                    childParentMapping.put(oldLeft, null);
                }
            } else if (childParentMapping.get(x).right == x) {
                childParentMapping.get(x).right = oldLeft;
                if (oldLeft != null) {
                    childParentMapping.put(oldLeft, childParentMapping.get(x));
                }
            } else {
                childParentMapping.get(x).left = oldLeft;
                if (oldLeft != null) {
                    childParentMapping.put(oldLeft, childParentMapping.get(x));
                }
            }
            oldLeft.right = x;
            childParentMapping.put(x, oldLeft);

            updateInterval(x);
            updateInterval(oldLeft);
        }
    }

    public void printInterval(Node node) {
        System.out.println("Node interval for node " + node.value + " is (" + node.startInterval + ", "
                + node.endInterval + ")\n");
    }

    private static int count(Node tree) {
        int c = 1;
        if (tree == null)
            return 0;
        else {
            c += count(tree.left);
            c += count(tree.right);
            return c;
        }
    }

    private static int height(Node root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(height(root.left), height(root.right));
        }
    }

    private static int calculateH(Node root) {
        if (root == null) {
            return 0;
        } else {
            int count = count(root);
            return (int) Math.floor(Math.log(count) / Math.log(2)) + 1;
        }
    }

    public int findRoot(Node root) {
        // int h = height(root); // find height
        int h = calculateH(root);
        int n = count(root); // find num of vertices

        System.out.println("height = " + h);
        System.out.println("count = " + n);
        System.out.println();

        int twoHMinusOne = (int) Math.pow(2, h - 1);
        int twoHMinusTwo = (int) Math.pow(2, h - 2);
        int twoH = (int) Math.pow(2, h);

        double result = 0;

        System.out.println("twoHMinusOne = " + twoHMinusOne);
        System.out.println("twoHMinusOne = " + twoHMinusTwo);
        System.out.println("twoH = " + twoH);

        if (n >= twoHMinusOne && n <= twoHMinusOne + twoHMinusTwo - 2) {
            System.out.println("condition 1 exec");
            result = (n - twoHMinusTwo + 1);
        } else if (twoHMinusOne + twoHMinusTwo - 1 <= n && n <= twoH - 1) {
            System.out.println("condition 2 exec");
            result = twoHMinusOne;
        }
        System.out.println("*_*_*_*_*_*_*_*_*_rootT = " + result);
        return (int) result;
    }

    public void buildLeftForearm(Node root) {
        Node walkerLeft = root.left;
        while (walkerLeft.left != null) {
            rightRotation(walkerLeft);
            walkerLeft = root.left;
            printLevelOrder(root);
        }

        Node followerLeft = root.left;
        walkerLeft = followerLeft.right;
        while (walkerLeft != null) {
            if (walkerLeft.left != null) {
                rightRotation(walkerLeft);
                walkerLeft = followerLeft.right;
            } else {
                walkerLeft = walkerLeft.right;
                followerLeft = followerLeft.right;
            }

            printLevelOrder(root);
        }
    }

    public void buildRightForearm(Node root) {
        Node walkerRight = root.right;
        while (walkerRight.right != null) {
            leftRotation(walkerRight);
            walkerRight = root.right;
        }
        Node followerRight = root.right;
        walkerRight = followerRight.left;
        while (walkerRight != null) {
            if (walkerRight.right != null) {
                leftRotation(walkerRight);
                walkerRight = followerRight.left;
            } else {
                walkerRight = walkerRight.left;
                followerRight = followerRight.left;
            }
        }
    }

    public void printInOrderTraversal(Node root) {
        if (root == null) {
            return;
        }
        printInOrderTraversal(root.left);
        System.out.print(root.value + " ");
        printInOrderTraversal(root.right);
    }

    public Set<Integer> ListifyTree(Node root) {
        if (root == null) {
            return null;
        }

        Set<Integer> nodeList = new HashSet<Integer>();
        // Set<Integer> leftList = new TreeSet<Integer>();
        // Set<Integer> rightList = new TreeSet<Integer>();
        nodeList.add(root.value);
        Set<Integer> leftList = ListifyTree(root.left);
        Set<Integer> rightList = ListifyTree(root.right);
        if (leftList != null) {
            nodeList.addAll(leftList);
        }

        if (rightList != null) {
            nodeList.addAll(rightList);
        }
        System.out.println(nodeList.toString());
        return nodeList;
    }

    public static boolean isIntervalSame(Node node1, Node node2) {
        if (node1.startInterval == node2.startInterval) {
            if (node1.endInterval == node2.endInterval) {
                System.out.println("YES, The intervals are the same :)");
                return true;
            }
        }
        System.out.println("BAZINGA!!!");
        return false;
    }

    public static boolean identicalTrees(Node a, Node b) {
        /* 1. both empty */
        if (a == null && b == null)
            return true;

        /* 2. both non-empty -> compare them */
        if (a != null && b != null)
            return (a.value == b.value && identicalTrees(a.left, b.left) && identicalTrees(a.right, b.right));

        /* 3. one empty, one not -> false */
        return false;
    }

    // TODO
    public void findNodesWithSameIntervals(Node root1, Node root2) {
        while (root1 != null) {
            if (isIntervalSame(root1, root2)) {
                System.out.println("Found an equivalent " + root1.value + " - - - " + root2.value);
            }
        }
    }

    public static void main(String[] args) {

        BinaryTree t = new BinaryTree(7);
        t.insert(t.root, 4);
        t.insert(t.root, 9);
        t.insert(t.root, 2);
        t.insert(t.root, 6);
        t.insert(t.root, 8);
        t.insert(t.root, 10);
        t.insert(t.root, 1);
        t.insert(t.root, 3);
        t.insert(t.root, 5);

        // BinaryTree s = new BinaryTree(41);
        // s.insert(s.root, 20);
        // s.insert(s.root, 65);
        // s.insert(s.root, 11);
        // s.insert(s.root, 29);
        // s.insert(s.root, 50);
        // s.insert(s.root, 91);
        // s.insert(s.root, 1);
        // s.insert(s.root, 18);
        // s.insert(s.root, 32);
        // s.insert(s.root, 72);
        // s.insert(s.root, 99);
        // s.insert(s.root, 16);
        // s.insert(s.root, 77);

        BinaryTree s = new BinaryTree(4);
        s.insert(s.root, 3);
        s.insert(s.root, 7);
        s.insert(s.root, 2);
        s.insert(s.root, 6);
        s.insert(s.root, 9);
        s.insert(s.root, 1);
        s.insert(s.root, 5);
        s.insert(s.root, 8);
        s.insert(s.root, 10);

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

        isIntervalSame(s.findNode(4), t.findNode(7));

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

        BinaryTree bt = new BinaryTree(7);
        bt.insert(bt.root, 4);
        bt.insert(bt.root, 8);
        bt.insert(bt.root, 2);
        bt.insert(bt.root, 5);
        bt.insert(bt.root, 12);
        bt.insert(bt.root, 1);
        bt.insert(bt.root, 3);
        bt.insert(bt.root, 6);
        bt.insert(bt.root, 11);
        bt.insert(bt.root, 13);
        bt.insert(bt.root, 9);
        bt.insert(bt.root, 10);

        System.out.println("Root is equal to :::::::" + bt.root.value);
        bt.printLevelOrder(bt.root);

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

        // building left subtree AL
        Node walker = bt.root.left;
        // Node walkerRight = walker.right.right;
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
            // alternate rotation policy

            // only consider count - height nodes from top for rotation

            // if value exceeds count - height, reinitialize walker and follower
            int countDummy = i;
            while (walker.right != null && countDummy < countLIteration) {
                System.out.println("walker = " + walker.value);
                System.out.println("follower = " + follower.value);

                countDummy += 2;
                if (countDummy >= countLIteration) {
                    break;
                }
                walker = walker.right.right;
                bt.leftRotation(follower);
                bt.printLevelOrder(bt.root);
                follower = walker;
            }
            walker = bt.root.left;
            follower = walker;
            countWalker = bt.root.left;
        }

        // while(walker.right != null || walker.right.right != null){
        // System.out.println("walker = "+walker.value);
        // System.out.println("follower = "+follower.value);

        // walker = walker.right.right;
        // bt.leftRotation(follower);
        // bt.printLevelOrder(bt.root);
        // follower = walker;
        // }
        // walker = bt.root.left;
        // follower = walker;

        // while (walkerRight != null) {
        // while (walker != null && walker.right != null) {
        // if (walkerRight != null) {
        // walkerRight = walker.right.right;
        // }
        // if(walkerRight != null){
        // bt.leftRotation(follower);
        // }
        // bt.printLevelOrder(bt.root);
        // walker = walkerRight;
        // follower = walker;
        // }
        // walker = bt.root.left;
        // walkerRight = walker.right.right;
        // follower = walker;
        // }

        System.out.println("After left subtree generation:\n");
        bt.printLevelOrder(bt.root);

        // building right subtree AL
        walker = bt.root.right;
        // Node walkerRight = walker.right.right;
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
            // alternate rotation policy

            // only consider count - height nodes from top for rotation

            // if value exceeds count - height, reinitialize walker and follower
            int countDummy = i;
            while (walker.left != null && countDummy < countRIteration) {
                System.out.println("walker = " + walker.value);
                System.out.println("follower = " + follower.value);

                countDummy += 2;
                if (countDummy >= countRIteration) {
                    break;
                }
                walker = walker.left.left;
                bt.rightRotation(follower);
                bt.printLevelOrder(bt.root);
                follower = walker;
            }
            walker = bt.root.right;
            follower = walker;
            countWalker = bt.root.right;
        }

        // // building right subtree AR
        // walker = bt.root.right;
        // Node walkerLeft = walker.left.left;
        // follower = walker;

        // while (walkerLeft != null) {
        //     while (walker != null && walker.left != null) {
        //         if (walkerLeft != null) {
        //             walkerLeft = walker.left.left;
        //         }
        //         bt.rightRotation(follower);
        //         walker = walkerLeft;
        //         follower = walker;
        //     }
        //     walker = bt.root.right;
        //     walkerLeft = walker.left.left;
        //     follower = walker;
        // }

        System.out.println("After right subtree generation:\n");
        bt.printLevelOrder(bt.root);

        // check if bt and t are identical (after A1)

        if (identicalTrees(bt.root, t.root)) {
            System.out.println("They are identical trees! YOOOOHOOOOOOO");
        } else {
            System.out.println("AIIIIINNNNNN!!!!");
        }
    }
}