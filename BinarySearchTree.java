//Vinicius Araujo
//Got the BST code from Canvas example
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
    class BinarySearchTree {

        private double inOrderTotal = 0;        //fields to store the value of the runtime of each traversal method.
        private double preOrderTotal = 0;
        private double postOrderTotal = 0;      //each time one of the traversal method runs, I add the value to the variable and then I divide it by then at the end to get average
        private double levelOrderTotal = 0;

        /* Class containing left
        and right child of current node and key value*/
        class Node {
            int key;
            Node left;
            Node right;

            public Node(int item) {
                key = item;
                left = null;
                right = null;
            }
        }

        // Root of BST
        Node root;

        // Constructor
        BinarySearchTree() {
            root = null;
        }

        // This method mainly calls deleteRec()
        void deleteKey(int key) {
            root = deleteRec(root, key);
        }

        /* A recursive function to
        delete an existing key in BST
        */
        Node deleteRec(Node root, int key) {
            /* Base Case: If the tree is empty */
            if (root == null)
                return root;

            /* Otherwise, recur down the tree */
            if (key < root.key)
                root.left = deleteRec(root.left, key);
            else if (key > root.key)
                root.right = deleteRec(root.right, key);

                // if key is same as root's
                // key, then this is the
                // node to be deleted
            else {
                // node with only one child or no child
                if (root.left == null)
                    return root.right;
                else if (root.right == null)
                    return root.left;

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                root.key = minValue(root.right);

                // Delete the inorder successor
                root.right = deleteRec(root.right, root.key);
            }

            return root;
        }

        int minValue(Node root) {
            int minv = root.key;
            while (root.left != null) {
                minv = root.left.key;
                root = root.left;
            }
            return minv;
        }

        // This method mainly calls insertRec()
        void insert(int key) {
            root = insertRec(root, key);
        }

        /* A recursive function to
        insert a new key in BST */
        Node insertRec(Node root, int key) {

		/* If the tree is empty,
		return a new node */
            if (root == null) {
                root = new Node(key);
                return root;
            }

            /* Otherwise, recur down the tree */
            if (key < root.key)
                root.left = insertRec(root.left, key);
            else if (key > root.key)
                root.right = insertRec(root.right, key);

            /* return the (unchanged) node pointer */
            return root;
        }

        // This method mainly calls InorderRec()
        void inorder() {        //this method writes the inorder traversal to the file
            long startTime = System.nanoTime(); //gets the start time of the method

            try {
                FileWriter fileWriter = new FileWriter("bst_elements.txt", true);  //writes to this file
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                fileWriter.write("\n");
                fileWriter.write("In-order traversal: ");
                inorderRec(root, bufferedWriter);


                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();   //gets the end time of the method
            inOrderTotal += (endTime - startTime);  //adds the time to the inOrderTotal to count the total time for the 10 runs together
            //writeTimeToFile("\n" + "In-order runtime: " + (endTime-startTime) + " nano-seconds");
        }


        void inorderRec(Node root, BufferedWriter bufferedWriter) throws IOException {      //method to do inorder traversal of BST and write to file
            if (root != null) {
                inorderRec(root.left, bufferedWriter);      //goes to left roots first
                bufferedWriter.write(root.key + " ");
                inorderRec(root.right, bufferedWriter);     //goes to right roots second
            }

        }

        void preorderRec(Node root, BufferedWriter bufferedWriter) throws IOException {     //method to do preorder traversal of BST
            if (root != null) {
                bufferedWriter.write(root.key + " ");   //Goes to root, then left subtree, then right at last
                preorderRec(root.left, bufferedWriter);
                preorderRec(root.right, bufferedWriter);
            }
        }

        void preorder() {       //this method writes the preorder traversal to the file
            long startTime = System.nanoTime();
            try {
                FileWriter fileWriter = new FileWriter("bst_elements.txt", true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                fileWriter.write("\n");
                fileWriter.write("Pre-order traversal: ");
                preorderRec(root, writer);

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            preOrderTotal += (endTime - startTime);
            //writeTimeToFile("\n" + "Pre-order runtime: " + (endTime-startTime) + " nano-seconds");
        }

        void clear() {      //this method clears the BST
            root = null;
        }


        void postorderRec(Node root, BufferedWriter bufferedWriter) throws IOException {
            if (root != null) {
                postorderRec(root.left, bufferedWriter);      //starts at the left subtrees, goes to the right subtrees, and then the root at last
                postorderRec(root.right, bufferedWriter);
                bufferedWriter.write(root.key + " ");
            }
        }

        void postorder() {      //this method writes the postorder traversal to the file
            long startTime = System.nanoTime();
            try {
                FileWriter FileWriter = new FileWriter("bst_elements.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(FileWriter);
                FileWriter.write("\n");
                bufferedWriter.write("Post-order traversal: ");
                postorderRec(root, bufferedWriter);

                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            postOrderTotal += (endTime - startTime);
            //writeTimeToFile("\n" + "Post-order runtime: " + (endTime-startTime) + " nano-seconds");
        }

        void levelOrder() {
            long startTime = System.nanoTime();
            try {
                FileWriter fileWriter = new FileWriter("bst_elements.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                if (root == null) {
                    bufferedWriter.close();
                    return;
                }

                Queue<Node> queue = new LinkedList<>(); //using queue to store nodes
                queue.add(root);
                bufferedWriter.write("\n");
                bufferedWriter.write("Level-order traversal: ");

                    while (!queue.isEmpty()) {
                        Node current = queue.poll();
                        bufferedWriter.write(current.key + " ");  // writes it to file

                        if (current.left != null) {
                            queue.add(current.left);
                        }

                        if (current.right != null) {
                            queue.add(current.right);
                        }
                    }
                    bufferedWriter.newLine();   //after it prints level-order, it skips a line and the ouptut will run again with the 4 traversal methods 9 more times

                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            levelOrderTotal += (endTime - startTime);
            //writeTimeToFile("\n" + "Level-order runtime: " + (endTime-startTime) + " nano-seconds");
        }

        void avegareRunTimes() {        //method to calculate and write the average runtime of each traversal method
            try {
                FileWriter fileWriter = new FileWriter("bst_elements.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write("\n");
                bufferedWriter.write("Average In-order runtime: " + (inOrderTotal/10) + " nano-seconds");
                bufferedWriter.newLine();
                bufferedWriter.write("Average Pre-order runtime: " + (preOrderTotal/10) + " nano-seconds");
                bufferedWriter.newLine();
                bufferedWriter.write("Average Post-order runtime: " + (postOrderTotal/10) + " nano-seconds");
                bufferedWriter.newLine();
                bufferedWriter.write("Average Level-order runtime: " + (levelOrderTotal/10) + " nano-seconds");
                bufferedWriter.newLine();

                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void ranking() {    //I am creating this array to sort it from smallest to biggest so that I can rank the average runtimes
            String[] averages = {(inOrderTotal/10) + " nanoseconds in average (In-order traversal)", (preOrderTotal/10) + " nanoseconds in average (Pre-order traversal)", (postOrderTotal/10) + " nanoseconds in average (Post-order traversal)", (levelOrderTotal/10) + " nanoseconds in average (Level-order traversal)"};
            Arrays.sort(averages);  //sorts array in order from smallest number to biggest
            for (int i = 0; i < averages.length; i++) {
                System.out.println(i+1 + ": " + averages[i]);   //prints each element of the sorted array in order.
            }
            }
        }



