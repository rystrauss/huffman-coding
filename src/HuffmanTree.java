import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class that represents a Huffman coding tree.
 *
 * @author Ryan Strauss
 */
class HuffmanTree {

    private static final int MAX_BYTE = 256;

    private Node root;
    private String[] codes;

    /**
     * Constructs a Huffman tree from byte frequencies.
     *
     * @param frequencies an array of length MAX_BYTE where index i contains the frequency of byte i
     */
    HuffmanTree(int[] frequencies) {
        Queue<Node> q = new PriorityQueue<>();

        // Add nonzero bytes to the queue for insertion into the tree
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0)
                q.add(new Node(i, frequencies[i]));
        }

        // Add pseudo-EOF character to queue
        q.add(new Node(MAX_BYTE, 1));

        if (q.size() <= 2)
            throw new RuntimeException("Trying to build Huffman tree with only two characters.");

        // Construct the tree
        while (q.size() > 2)
            q.add(new Node(q.poll(), q.poll()));
        this.root = new Node(q.poll(), q.poll());

        this.codes = buildCodes();
    }

    /**
     * Accesses the tree's Huffman codes for each byte.
     *
     * @return a String array where index i contains byte i's Huffman code as a binary string
     */
    String[] getCodes() {
        return this.codes;
    }

    /**
     * Writes the tree to the provided output stream in prefix notation.
     *
     * Each node in the tree is written as 10 bits. The first bit is a flag that indicates whether
     * or not the node is a leaf (1 means it is a leaf). The next 9 bits contain the node's data.
     *
     * @param outputStream the output stream to which the tree will be written
     * @throws IOException if the tree cannot be written to the file
     */
    void write(BitOutputStream outputStream) throws IOException {
        if (this.root == null)
            throw new RuntimeException("Trying to write empty Huffman tree to file.");

        write(outputStream, this.root);
    }

    /**
     * Performs a pre-order traversal of the tree and writes nodes to the output stream.
     *
     * @param outputStream the output stream to which the tree will be written
     * @param cur the current node that will be written
     * @throws IOException if the node cannot be written to the file
     */
    private void write(BitOutputStream outputStream, Node cur) throws IOException {
        if (cur == null)
            return;

        outputStream.writeBit(cur.isLeaf ? 0 : 1);
        outputStream.writeBits(cur.data, 9);
        write(outputStream, cur.left);
        write(outputStream, cur.right);
    }

    /**
     * Determines the Huffman code for each byte in the tree.
     *
     * @return a String array containing the Huffman codes determined by the tree
     */
    private String[] buildCodes() {
        String[] codes = new String[MAX_BYTE + 1];
        buildCodes(codes, this.root, "");
        return codes;
    }

    /**
     * Recursively traverses the tree to determines the Huffman code for each leaf.
     *
     * @param codes a String array where the codes are stored
     * @param cur the current node in the tree
     * @param code the current code determined by the path taken through the tree thus far
     */
    private void buildCodes(String[] codes, Node cur, String code) {
        if (cur.isLeaf) {
            codes[cur.data] = code;
        } else {
            buildCodes(codes, cur.left, code + '0');
            buildCodes(codes, cur.right, code + '1');
        }
    }

    /**
     * Represents a node in the Huffman tree.
     */
    private class Node implements Comparable<Node> {

        Node left, right;
        int data, frequency;
        boolean isLeaf;

        Node(int data, int frequency) {
            this.data = data;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
            this.isLeaf = true;
        }

        Node(Node left, Node right) {
            this.data = 0;
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
            this.isLeaf = false;
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }
    }

}
