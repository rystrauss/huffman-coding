import java.util.PriorityQueue;
import java.util.Queue;

class HuffmanTree {

    private static final int MAX_BYTE = 256;

    private Node root;

    HuffmanTree(int[] frequencies) {
        Queue<Node> q = new PriorityQueue<>();

        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0)
                q.add(new Node(i, frequencies[i]));
        }
        // Add pseudo-EOF character to queue
        q.add(new Node(MAX_BYTE, 1));

        if (q.size() <= 2)
            throw new RuntimeException("Trying to build Huffman tree with only two characters.");

        while (q.size() > 2)
            q.add(new Node(q.poll(), q.poll()));

        this.root = new Node(q.poll(), q.poll());
    }

    void write(BitOutputStream outputStream) {
        if (this.root == null)
            throw new RuntimeException("Trying to write empty Huffman tree to file.");


    }

    private class Node implements Comparable<Node> {

        Node left, right;
        int data, frequency;

        Node(int data, int frequency) {
            this.data = data;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

        Node(Node left, Node right) {
            this.data = MAX_BYTE;
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }
    }

}
