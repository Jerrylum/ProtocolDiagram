package com.jerryio.protocol_diagram.token;

/**
 * a record that acts as a tuple data structure but with the length of 2
 */
public record Pair<T1, T2> (T1 first, T2 second) {

}
