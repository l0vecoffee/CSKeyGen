package com.cobaltstrick;

import java.util.*;
import java.io.*;
import java.nio.*;

public class DataParser {
    protected DataInputStream content;
    protected byte[] bdata;
    protected ByteBuffer buffer;
    protected byte[] original;
    protected Stack frames;

    public DataParser(final InputStream inputStream) {
        this(CommonUtils.readAll(inputStream));
    }

    public void jump(final long n) throws IOException {
        this.frames.push(this.content);
        this.content = new DataInputStream(new ByteArrayInputStream(this.original));
        if (n > 0L) {
            this.consume((int)n);
        }
    }

    public DataParser(final byte[] original) {
        this.content = null;
        this.bdata = new byte[8];
        this.buffer = null;
        this.frames = new Stack();
        this.original = original;
        (this.buffer = ByteBuffer.wrap(this.bdata)).order(ByteOrder.LITTLE_ENDIAN);
        this.content = new DataInputStream(new ByteArrayInputStream(original));
    }

    public void consume(final int n) throws IOException {
        this.content.skipBytes(n);
    }

    public int readInt() throws IOException {
        this.buffer.clear();
        this.content.read(this.bdata, 0, 4);
        return this.buffer.getInt(0);
    }

    public long readQWord() throws IOException {
        this.buffer.clear();
        this.content.read(this.bdata, 0, 8);
        return this.buffer.getLong(0);
    }

    public byte readByte() throws IOException {
        return this.content.readByte();
    }

    public char readChar() throws IOException {
        return (char)this.content.readByte();
    }

    public char readChar(final DataInputStream dataInputStream) throws IOException {
        return (char)dataInputStream.readByte();
    }

    public byte[] readBytes(final int n) throws IOException {
        final byte[] array = new byte[n];
        this.content.read(array);
        return array;
    }

    public int readShort() throws IOException {
        this.content.read(this.bdata, 0, 2);
        return this.buffer.getShort(0) & 0xFFFF;
    }

    public boolean more() throws IOException {
        return this.content.available() > 0;
    }

    public String readCountedString() throws IOException {
        final int int1 = this.readInt();
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < int1; ++i) {
            sb.append(this.readChar());
        }
        return sb.toString();
    }

    public String readString() throws IOException {
        final StringBuffer sb = new StringBuffer();
        while (true) {
            final char char1 = this.readChar();
            if (char1 <= '\0') {
                break;
            }
            sb.append(char1);
        }
        return sb.toString();
    }

    public String readString(final int n) throws IOException {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            final char char1 = this.readChar();
            if (char1 > '\0') {
                sb.append(char1);
            }
        }
        return sb.toString();
    }

    public DataInputStream getData() {
        return this.content;
    }

    public void little() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public void big() {
        this.buffer.order(ByteOrder.BIG_ENDIAN);
    }
}
