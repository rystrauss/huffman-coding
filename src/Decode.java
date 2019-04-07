import java.io.IOException;

public class Decode {

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
            System.out.println("Usage: java Encode <input>\n");
            System.out.println("    <input>  = system path to the file that will be decompressed");
            System.exit(1);
        }

        setFileNames(args[0]);
        BitInputStream inputStream = new BitInputStream(inputFile);
        HuffmanTree tree = new HuffmanTree(inputStream);
        inputStream.close();
    }

}
