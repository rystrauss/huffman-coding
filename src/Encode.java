import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.System.exit;

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
        int c = inputStream.read();
        while (c != EOF) {
            frequencies[c]++;
            c = inputStream.read();
        }
        inputStream.close();
        return frequencies;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Error: an argument must be provided.\n");
            System.out.println("Usage: java Encode <input>\n");
            System.out.println("    <input>  = system path to the file that will be compressed");
            exit(1);
        }

        setFileNames(args[0]);
        int[] frequencies = countCharacterFrequencies();
        HuffmanTree tree = new HuffmanTree(frequencies);

    }

}
