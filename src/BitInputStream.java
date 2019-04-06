import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility that allows for the reading of files one bit at a time.
 *
 * @author Ryan Strauss
 */
class BitInputStream {

    private static final int BYTE_SIZE = 8;
    private static final int EOF = -1;

    private FileInputStream inputStream;
    private int buffer;
    private int bufferSize;

    /**
     * Constructs a BitInputStream that allows the specified file to be read bit by bit.
     *
     * @param file system path to the file to be read
     */
    BitInputStream(String file) throws IOException {
        this.bufferSize = 0;
        this.inputStream = new FileInputStream(file);
        fillBuffer();
    }

    /**
     * Refills the stream's internal buffer with the next byte of data.
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
     */
    public int nextBit() throws IOException {
        if (this.buffer == EOF)
            return EOF;

        int bit = this.buffer % 2;
        this.buffer /= 2;
        if (--this.bufferSize == 0)
            fillBuffer();

        return bit;
    }

    /**
     * Closes the BitInputStream.
     */
    public void close() throws IOException {
        this.inputStream.close();
    }

}
