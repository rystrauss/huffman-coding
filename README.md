# Huffman Coding

This is an implementation of Huffman coding for digital compression. It is named after David Huffman,
who invented the technique as a graduate student at MIT in 1950. Both JPEG and MP3 encoders use Huffman coding as
part of their algorithms.

## Usage

**Encoding:** `Encode.java` contains a program for compressing a file. It accepts a single command line argument
which is the file to be compressed. The compressed file will be saved as a `.huff` file.

**Decoding:** `Decode.java` contains a program for uncompressing a `.huff` file. It accepts a single command line
argument which is the file to be uncompressed. The decoded file will be saved as a `.unhuff` file.