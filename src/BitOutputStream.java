import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility that allows for the writing of files one bit at a time.
 *
 * @author Ryan Strauss
 */
class BitOutputStream {

    private static final int BYTE_SIZE = 8;

    private FileOutputStream outputStream;
    private int buffer;
    private int bufferSize;

    /**
     * Constructs a BitOutputStream that allows the specified file to be written bit by bit.
     *
     * @param file system path to the file to be written
     * @throws FileNotFoundException if the file cannot be opened
     */
    BitOutputStream(String file) throws FileNotFoundException {
        this.bufferSize = 0;
        this.buffer = 0;
        this.outputStream = new FileOutputStream(file);
    }

    /**
     * Writes the next byte of data in the stream's internal buffer to the file.
     *
     * @throws IOException if the buffer cannot be flushed to the file
     */
    private void flushBuffer() throws IOException {
        this.outputStream.write(this.buffer);
        this.buffer = 0;
        this.bufferSize = 0;
    }

    /**
     * Writes a bit to the file.
     *
     * @param bit the bit to be written to the file
     * @throws IOException if the bit cannot be written to the file
     */
    void writeBit(int bit) throws IOException {
        if (bit < 0 || bit > 1)
            throw new IllegalArgumentException("Bit can only be 0 or 1.");

        this.buffer += bit << this.bufferSize++;

        if (this.bufferSize == BYTE_SIZE)
            flushBuffer();
    }

    /**
     * Writes multiple bits of data to the stream.
     *
     * @param data the data to be written to the file
     * @param bits the number of bits of data to be written
     * @throws IOException if the byte cannot be written to the file
     */
    void writeBits(int data, int bits) throws IOException {
        for (int i = 0; i < bits; i++)
            writeBit((data >> bits - i - 1) % 2);
    }

    /**
     * Closes the BitOutputStream.
     *
     * @throws IOException if the file cannot be closed
     */
    void close() throws IOException {
        if (this.bufferSize > 0)
            flushBuffer();
        this.outputStream.close();
    }

}
