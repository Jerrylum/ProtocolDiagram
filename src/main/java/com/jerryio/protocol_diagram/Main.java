package com.jerryio.protocol_diagram;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");

        // CodePointBuffer buffer = new CodePointBuffer("-");

        // Minus d = Minus.parse(buffer);
        // if (d != null) {
        //     System.out.println(d.value());
        // } else {
        //     System.out.println("null");
        // }

        // Int p = Int.parse(new CodePointBuffer("0"));
        // NumberT p = NumberT.parse(new CodePointBuffer("3.14"));
        // BooleanT p = BooleanT.parse(new CodePointBuffer("t "));
        // SingleQuoteString p = SingleQuoteString.parse(new CodePointBuffer("'hello world'"));
        // DoubleQuoteString p = DoubleQuoteString.parse(new CodePointBuffer("\"hello world\""));
        // StringT p = StringT.parse(new CodePointBuffer("hello world"));
        // Parameter p = Parameter.parse(new CodePointBuffer("8"));
        // if (p != null) {
        //     System.out.println(p.getDouble());
        // } else {
        //     System.out.println("null");
        // }

        // SafeString p = SafeString.parse(new CodePointBuffer("hello world:"));
        // if (p != null) {
        //     System.out.println(p.content());
        // } else {
        //     System.out.println("null");
        // }

        // CommandLine p = CommandLine.parse(new CodePointBuffer("add 8 'hello world'"));
        // if (p != null) {
        //     System.out.println(">>" + p.name() + "<<");
        //     System.out.println(">>" + p.params().get(0).isNumber() + "<<");

        // } else {
        //     System.out.println("null");
        // }

        OneLineInput p = OneLineInput.parse(new CodePointBuffer("a: 8, b: 9"));
        if (p != null) {
            System.out.println(">>" + p.params().get(0).first() + "<<");
            System.out.println(">>" + p.params().get(0).second() + "<<");

        } else {
            System.out.println("null");
        }
    }
}
