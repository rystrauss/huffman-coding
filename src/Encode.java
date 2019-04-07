import java.io.FileInputStream;
import java.io.IOException;

/**
 * Program that compresses a given file using Huffman Coding.
 *
 * @author Ryan Strauss
 */
public class Encode {

    private static final int MAX_BYTE = 256;
    private static final int EOF = -1;

    private static String inputFile;
    private static String outputFile;

    /**
     * Sets input and output file names.
     *
     * @param file system path to the input file
     */
    private static void setFileNames(String file) {
        inputFile = file;
        int extensionIndex = inputFile.lastIndexOf('.');
        outputFile = inputFile.substring(0, extensionIndex) + ".huff";
    }

    /**
     * Counts the frequency of each byte in the input file.
     *
     * @return an int array containing the frequencies of the bytes
     * @throws IOException if the input file cannot be opened
     */
    private static int[] countCharacterFrequencies() throws IOException {
        int[] frequencies = new int[MAX_BYTE];
        FileInputStream inputStream = new FileInputStream(inputFile);
        int b = inputStream.read();
        while (b != EOF) {
            frequencies[b]++;
            b = inputStream.read();
        }
        inputStream.close();
        return frequencies;
    }

    /**
     * Writes the given code to the output stream.
     *
     * @param outputStream the output stream to write to
     * @param s a String containing a Huffman code (assumed to only contain 1's and 0's)
     * @throws IOException if the stream cannot be written to
     */
    private static void writeString(BitOutputStream outputStream, String s) throws IOException {
        for (int i = 0; i < s.length(); i++)
            outputStream.writeBit(s.charAt(i) - '0');
    }

    /**
     * Encodes the input file and writes the compressed data to the output file.
     *
     * @param outputStream the bit stream to write the compressed data to
     * @param codes        the Huffman codes to use
     */
    private static void encode(BitOutputStream outputStream, String[] codes) throws IOException {
        FileInputStream inputStream = new FileInputStream(inputFile);

        int b = inputStream.read();
        while (b != EOF) {
            if (codes[b] == null) {
                inputStream.close();
                outputStream.close();
                System.out.println("Error: tried to compress a character for which there is no code.");
                System.exit(1);
            }
            writeString(outputStream, codes[b]);
            b = inputStream.read();
        }

        // Write pseudo-EOF
        writeString(outputStream, codes[MAX_BYTE]);

        inputStream.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Error: an argument must be provided.\n");
            System.out.println("Usage: java Encode <input>\n");
            System.out.println("    <input>  = system path to the file that will be compressed");
            System.exit(1);
        }

        setFileNames(args[0]);
        int[] frequencies = countCharacterFrequencies();
        HuffmanTree tree = new HuffmanTree(frequencies);
        BitOutputStream outputStream = new BitOutputStream(outputFile);
        tree.write(outputStream);
        encode(outputStream, tree.getCodes());
        outputStream.close();
    }

}
