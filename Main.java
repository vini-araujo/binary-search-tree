//Vinicius Araujo, Assignment 5.
//This file run the 4 traversal types 10 times each with 500 random numbers generated, and prints the ranking of fastest and slowest and writes the traversals to file.
import java.util.LinkedList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        //LinkedList randomNmbers = new LinkedList();
        BinarySearchTree tree = new BinarySearchTree();

        System.out.println("Rank of runtime from fastest to slowest: ");



        try {
            FileWriter writer = new FileWriter("bst_elements.txt");     //file writer to write on a file called bst_elements.txt
            StringBuilder output = new StringBuilder();

            //BinarySearchTree tree = null;
            for (int j = 0; j < 10; j++) {      //for loop to repeat the process 10 times with different random numbers each time
                LinkedList randomNumbers = generateRandomNumbers(rand);

                tree = new BinarySearchTree();
                for (int i = 0; i < randomNumbers.size(); i++) {
                    tree.insert((Integer) randomNumbers.get(i));        //inserts the unique random numbers in the BST.
                }

                tree.inorder();     //calls the traversal methods on the BST
                tree.preorder();
                tree.postorder();
                tree.levelOrder();

            }
            tree.avegareRunTimes();      //calls method to write the average run times on file

            writer.write(output.toString());        //writes the result to file

            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        tree.ranking();     //prints the traversal methods in order from fastest so slowest in the console.

    }



    private static LinkedList<Integer> generateRandomNumbers(Random rand) {     //method that generates a list with 500 random numbers that are unique
        LinkedList<Integer> randomNumbers = new LinkedList<>();      //I am using a LInked list to add the random numbers to make sure they are unique first before adding to the BST

        while (randomNumbers.size() < 500) {        //while there isnt 500 numbers in the arraylist, keeps adding random numbers as long as that number is not present in the arraylist yet (is unique)
            int number = rand.nextInt(9998) + 1;
            if (!randomNumbers.contains(number)) {
                randomNumbers.add(number);
            }
        }

        return randomNumbers;
    }
}
