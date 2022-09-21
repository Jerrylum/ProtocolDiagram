package com.jerryio.protocol_diagram.token;

import java.util.ArrayList;
import java.util.List;

public record OneLineInput(List<Pair<String, Integer>> params) implements Token {
    public static OneLineInput parse(CodePointBuffer buffer) {
        buffer.readDelimiter();

        List<Pair<String, Integer>> params = new ArrayList<>();
        while (true) {
            String name = buffer.readSafeChunk();
            if (name.isEmpty())
                break;

            buffer.readDelimiter();

            if (buffer.next() != (Character)':')
                return null;

            buffer.readDelimiter();

            PositiveInt p = PositiveInt.parse(buffer);
            if (p == null)
                return null;
            params.add(new Pair<>(name, Integer.parseInt(p.value())));

            if (buffer.next() != (Character)',')
                break;

            buffer.readDelimiter();
        }        

        if (buffer.hasNext())
            return null;
        else
            return new OneLineInput(params);
    }
}
