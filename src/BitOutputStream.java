import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility that allows for the writing of files one bit at a time.
 *
 * @author Ryan Strauss
 */
class BitOutputStream {

    private final int BYTE_SIZE = 8;

    private FileOutputStream outputStream;
    private int buffer;
    private int bufferSize;

    /**
     * Constructs a BitOutputStream that allows the specified file to be written bit by bit.
     *
     * @param file system path to the file to be written
     */
    BitOutputStream(String file) throws FileNotFoundException {
        this.bufferSize = 0;
        this.buffer = 0;
        this.outputStream = new FileOutputStream(file);
    }

    /**
     * Writes the next byte of data in the stream's internal buffer to the file.
     */
    private void flushBuffer() throws IOException {
        this.outputStream.write(this.buffer);
        this.buffer = 0;
        this.bufferSize = 0;
    }

    /**
     * Writes the next bit to the file.
     *
     * @param bit the bit to be written to the file
     */
    public void writeBit(int bit) throws IOException {
        if (bit < 0 || bit > 1)
            throw new IllegalArgumentException("Bit can only be 0 or 1.");

        this.buffer += bit << this.bufferSize++;

        if (this.bufferSize == BYTE_SIZE)
            flushBuffer();
    }

    /**
     * Closes the BitOutputStream.
     */
    public void close() throws IOException {
        if (this.bufferSize > 0)
            flushBuffer();
        this.outputStream.close();
    }

}
