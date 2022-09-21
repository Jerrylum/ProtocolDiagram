package com.jerryio.protocol_diagram.token;

import java.util.Stack;

public class CodePointBuffer {
    private String target;
    private int index;
    private Stack<Integer> history;

    public CodePointBuffer(String target) {
        this.target = target;
        this.index = 0;
        this.history = new Stack<Integer>();
    }

    public int length() {
        return target.length();
    }

    public int getIndex() {
        return index;
    }

    public Character at(int index) {
        return index < length() ? target.charAt(index) : null;
    }

    public void savepoint() {
        history.push(this.index);
    }

    public void rollback() {
        this.index = history.pop();
    }

    public <T> T rollbackAndReturn(T value) {
        rollback();
        return value;
    }

    public void commit() {
        history.pop();
    }

    public <T> T commitAndReturn(T value) {
        commit();
        return value;
    }

    public Character next() {
        return at(index++);
    }

    public Character peek() {
        return at(index);
    }

    public Character peek(int offset) {
        return at(index + offset);
    }

    public boolean hasNext() {
        return index < length();
    }

    public int readDelimiter() {
        int count = 0;
        while (hasNext() && Token.isDelimiter(peek())) {
            count++;
            next();
        }
        return count;
    }

    public String readChunk() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && !Token.isDelimiter(peek())) {
            sb.append(next());
        }
        return sb.toString(); // might be empty
    }

    public String readSafeChunk() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && !Token.isSafeDelimiter(peek())) {
            sb.append(next());
        }
        return sb.toString(); // might be empty
    }
}
