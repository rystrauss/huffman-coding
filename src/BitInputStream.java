import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility that allows for the reading of files one bit at a time.
 *
 * @author Ryan Strauss
 */
class BitInputStream {

    private static final int INT_SIZE = 16;
    private static final int BYTE_SIZE = 8;
    private static final int EOF = -1;

    private FileInputStream inputStream;
    private int buffer;
    private int bufferSize;

    /**
     * Constructs a BitInputStream that allows the specified file to be read bit by bit.
     *
     * @param file system path to the file to be read
     * @throws IOException if the file cannot be opened
     */
    BitInputStream(String file) throws IOException {
        this.bufferSize = 0;
        this.inputStream = new FileInputStream(file);
        fillBuffer();
    }

    /**
     * Refills the stream's internal buffer with the next byte of data.
     *
     * @throws IOException if the next byte cannot be read from the file
     */
    private void fillBuffer() throws IOException {
        if (this.bufferSize != 0)
            throw new RuntimeException("Trying to fill non-empty buffer.");

        this.buffer = this.inputStream.read();
        this.bufferSize = BYTE_SIZE;
    }

    /**
     * Gets the next bit from the file.
     *
     * @return the next bit that is read from the file
     * @throws IOException if the next bit cannot be read from the file
     */
    int nextBit() throws IOException {
        if (this.buffer == EOF)
            return EOF;

        int bit = this.buffer % 2;
        this.buffer /= 2;
        if (--this.bufferSize == 0)
            fillBuffer();

        return bit;
    }

    /**
     * Gets the specified number of bits worth of data from the stream.
     *
     * @param bits the number of bits to read in
     * @return the data read from the stream
     * @throws IOException if the data cannot be read from the stream
     */
    int nextBits(int bits) throws IOException {
        if (bits > INT_SIZE)
            throw new IllegalArgumentException("Cannot read more than 4 bytes (one int) at a time.");

        int data = 0;
        for (int i = 0; i < bits; i++)
            data += nextBit() << (bits - i - 1);
        return data;
    }

    /**
     * Closes the BitInputStream.
     *
     * @throws IOException if the input stream cannot be closed
     */
    void close() throws IOException {
        this.inputStream.close();
    }

}
