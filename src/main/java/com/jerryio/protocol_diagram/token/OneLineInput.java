package com.jerryio.protocol_diagram.token;

import java.util.ArrayList;
import java.util.List;

public record OneLineInput(List<Pair<String, Integer>> params) implements Token {
    public static OneLineInput parse(CodePointBuffer buffer) {
        buffer.readDelimiter();

        List<Pair<String, Integer>> params = new ArrayList<>();
        while (true) {
            SafeString name = SafeString.parse(buffer);
            if (name == null)
                break;

            buffer.readDelimiter();

            if (buffer.peek() != (Character)':')
                return null;
            buffer.next();

            buffer.readDelimiter();

            PositiveInt p = PositiveInt.parse(buffer);
            if (p == null)
                return null;
            params.add(new Pair<>(name.content(), Integer.parseInt(p.value())));

            if (buffer.peek() != (Character)',')
                break;
            buffer.next();

            buffer.readDelimiter();
        }        

        if (buffer.hasNext())
            return null;
        else
            return new OneLineInput(params);
    }
}
