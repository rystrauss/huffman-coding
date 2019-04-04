import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Utility that allows for the reading of files one bit at a time.
 *
 * @author Ryan Strauss
 */
public class BitInputStream {

    private final int BYTE_SIZE = 8;
    private final int EOF = -1;

    private FileInputStream inputStream;
    private int buffer;
    private int bufferSize;

    /**
     * Constructs a BitInputStream that allows the specified file to be read bit by bit.
     *
     * @param file system path to the file to be read
     */
    BitInputStream(String file) {
        this.bufferSize = 0;
        try {
            this.inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.toString());
        }
        fillBuffer();
    }

    /**
     * Refills the stream's internal buffer with the next byte of data.
     */
    private void fillBuffer() {
        if (this.bufferSize != 0)
            throw new RuntimeException("Trying to fill non-empty buffer.");

        try {
            this.buffer = this.inputStream.read();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }

        this.bufferSize = BYTE_SIZE;
    }

    /**
     * Gets the next bit from the file.
     *
     * @return the next bit that is read from the file
     */
    public int nextBit() {
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
    public void close() {
        try {
            this.inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

}
