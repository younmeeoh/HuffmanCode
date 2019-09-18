import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Date;


public class huffman {

    //comparator for priority queue class in java to make priority queue
    static class MyComparator implements Comparator<Node>{
        public int compare(Node x, Node y){
            //when two values share different priority.
            if(x.data != y.data)return x.data - y.data;
            //when we add new tree that has same priority, we need newest one to be popped off (FIFO)
            else {
                long s = (x.timestamp -y.timestamp);
                int i = (int) s;
                return i;
            }
        }
    }

    //this function will set Huffman code to each leaf node.
    public static void setCode(Node root, String s){
        //base case, if it is the leaf node where left and right are empty
        //Then get the huffman encoding by traversing through tree.
        if(root.left == null
            && root.right == null
        && root.c != '0') {
            //do not add the first string of null into Huffman.
            if(root.Huffman == null) {
                root.Huffman = s;
                return;
            }
            //add the binary numbers to Huffman property .
            else{
                root.Huffman = root.Huffman + s;
                return;
            }

        }
        //inductive case
        // if we go to left then add "0" to the code.
        // if we go to the right add"1" to the code.

        // recursive calls for left and
        // right sub-tree of the generated tree.
        setCode(root.left, s + "0");
        setCode(root.right, s + "1");
    }

    //this function will traverse through the tree to find matching character and print.
    public static void traverse(char c, Node root){
        //if there is left node, check the left node
        if (root.left != null){

            traverse(c,root.left); //keep moving left if not matched
        }
        //if the node's character matches the character from the string, print!
        if (root.c == c)
        {
            System.out.print(root.Huffman + " ");
            //print the huffman code.
        }
        //if there is right node to the root, move right
        if (root.right != null){
            traverse(c,root.right); //keep moving right till leaf node.
        }

    }

    //function to printout the codes
    public static void encode(String s,Node root) {

        //converting the string to array
        char[] strArr = s.toCharArray();

        //loop until the string array is finished.
        for(int i = 0; i < strArr.length; i++){

            //find the char within tree and print its huffman code using In-Order traverse function to search.
            traverse(strArr[i],root);
        }
        //print its huffmancode

    }

    public static void main(String[] args){


        Date date = new Date();//for FIFO implementation in Priority Queue

        int qSize = 9; //elements in priority queue

        //array for character
        char[] charArr = {'.','U','T','Y','E','A','I',' ','S'};
        int[] charFreq = {1,1,1,2,2,2,3,4,6};

        PriorityQueue<Node> q = new PriorityQueue<>(qSize, new MyComparator());
        //create chr into node tree.
        for(int i=0;i<9;i++) {
            Node tmp = new Node();
            tmp.data = charFreq[i];
            tmp.c = charArr[i];
            tmp.root = tmp;//set the root to itself.
            tmp.right = null;
            tmp.left = null;
            q.add(tmp); // add it to priority queue
        }
        Node root = new Node();
        while(qSize != 1){
            //remove two nodes using peek and poll for priority queue structure!
            Node x = q.peek(); //retrieve the highest queue into x
            q.poll();//pop off the stack
            //second node pop
            Node y = q.peek();//retrieve second into y
            q.poll();
            //make them child of next
            Node tmp = new Node();
            tmp.c = '0';
            tmp.left = x; //first node from priority queue
            tmp.right = y; //second
            tmp.data = x.data+y.data;
            tmp.timestamp = date.getTime();
            root = tmp; //empty root
            //insert new node into priority queue
            q.add(tmp);
            qSize--; //decrement the queue size for while loop
        }

        //we now need to encode them into binary numbers.
        setCode(root, "");

        //input string to be encoded at the end.
        String input = "SUSIE SAYS IT IS EASY.";

        //this is going to display the input message in encoded huffman binary numbers
        encode(input, root);


    }
}
