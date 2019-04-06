import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

class HuffmanTree {

    private static final int MAX_CHAR = 256;

    private Node root;

    HuffmanTree(int[] frequencies) {
        Queue<Node> q = new PriorityQueue<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0)
                q.add(new Node(i, frequencies[i]));
        }

        if (q.size() <= 2)
            throw new RuntimeException("Trying to build Huffman tree with only two characters.");

        while (q.size() > 2)
            q.add(new Node(Objects.requireNonNull(q.poll()), Objects.requireNonNull(q.poll())));

        this.root = new Node(Objects.requireNonNull(q.poll()), Objects.requireNonNull(q.poll()));
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
            this.data = MAX_CHAR;
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
