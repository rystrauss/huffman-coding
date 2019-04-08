import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Program that decodes a file which was compressed with Encode.
 *
 * @author Ryan Strauss
 */
public class Decode {

    private static final int MAX_BYTE = 256;

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
        outputFile = inputFile.substring(0, extensionIndex) + ".unhuff";
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Error: an argument must be provided.\n");
            System.out.println("Usage: java Decode <input>\n");
            System.out.println("    <input>  = system path to the file that will be decompressed");
            System.exit(1);
        }

        setFileNames(args[0]);
        BitInputStream inputStream = new BitInputStream(inputFile);
        HuffmanTree tree = new HuffmanTree(inputStream);
        PrintStream outputStream = new PrintStream(new File(outputFile));
        tree.decode(inputStream, outputStream, MAX_BYTE);
        inputStream.close();
        outputStream.close();
    }

}
