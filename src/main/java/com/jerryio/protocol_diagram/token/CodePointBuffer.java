package com.jerryio.protocol_diagram.token;

import java.util.Stack;

import com.jerryio.protocol_diagram.util.TokenUtils;

public class CodePointBuffer {
    private String target;
    private int index;
    private Stack<Integer> history;

    /**
     * a constructor that takes a string as the input
     * 
     * @param target
     */
    public CodePointBuffer(String target) {
        this.target = target;
        this.index = 0;
        this.history = new Stack<Integer>();
    }

    /**
     * a method to retrieve the length of the internally stored string
     * 
     * @return
     */
    public int length() {
        return target.length();
    }

    /**
     * a method to get the current cursor index
     * 
     * @return int
     */
    public int getIndex() {
        return index;
    }

    /**
     * a method to lookup the character by specified index
     * 
     * @param index
     * @return Character
     */
    public Character at(int index) {
        return index < length() ? target.charAt(index) : null;
    }

    /**
     * a method to save the current cursor into the history
     */
    public void savepoint() {
        history.push(this.index);
    }

    /**
     * a method to retrieve the last saved cursor position, and restore the cursor
     * from that position
     */
    public void rollback() {
        this.index = history.pop();
    }

    /**
     * a wrapper function that receives and returns the value without performing any
     * operation on top of that value
     * and rollback behind the scene
     * 
     * @param <T>
     * @param value
     * @return T
     */
    public <T> T rollbackAndReturn(T value) {
        rollback();
        return value;
    }

    /**
     * a method that remove the last saved cursor
     */
    public void commit() {
        history.pop();
    }

    /**
     * a wrapper function that receives and returns the value without performing any
     * operation on top of that value
     * and commit behind the scene
     * 
     * @param <T>
     * @param value
     * @return T
     */
    public <T> T commitAndReturn(T value) {
        commit();
        return value;
    }

    /**
     * a method to lookup the cursor pointing character and move the cursor to the
     * right
     * 
     * @return Character
     */
    public Character next() {
        return at(index++);
    }

    /**
     * a method to lookup the cursor pointing character
     * 
     * @param offset
     * @return Character
     */
    public Character peek() {
        return at(index);
    }

    /**
     * a method to look a head character by given offset
     * 
     * @param offset
     * @return Character
     */
    public Character peek(int offset) {
        return at(index + offset);
    }

    /**
     * a method to determine whether the string could be further consumed
     * 
     * @return boolean
     */
    public boolean hasNext() {
        return index < length();
    }

    /**
     * a method that reads every delimiter and stops once it bumps into a
     * non-delimiter character and return
     * @return int a integer denoting the number of delimiter has read
     */
    public int readDelimiter() {
        int count = 0;
        while (hasNext() && TokenUtils.isDelimiter(peek())) {
            count++;
            next();
        }
        return count;
    }

    /**
     * a method that reads every character and stops once it bumps into a delimiter
     * 
     * @return String
     */
    public String readChunk() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && !TokenUtils.isDelimiter(peek())) {
            sb.append(next());
        }
        return sb.toString(); // might be empty
    }

    /**
     * a method that reads every character and stops once it bumps into a safe
     * delimiter, which, is a superset of delimiter, it contains
     * (null | ' ' | ':' | ',')
     * 
     * @return String
     */
    public String readSafeChunk() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && !TokenUtils.isSafeDelimiter(peek())) {
            sb.append(next());
        }
        return sb.toString(); // might be empty
    }
}
