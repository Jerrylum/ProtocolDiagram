package com.jerryio.protocol_diagram;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;

public class FileSystem {

    public static String mountedFile = null;

    public static HandleResult save(String path, Diagram d) {
        File file = new File(path);

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(d.toJson());
            writer.close();
        } catch (IOException e) {
            return HandleResult.fail("Failed to write file: " + e.getMessage());
        }

        return HandleResult.success("Saved to " + file.getAbsolutePath());
    }

    public static HandleResult export(String path, Diagram d) {
        File file = new File(path);

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(d.toString());
            writer.close();
        } catch (IOException e) {
            return HandleResult.fail("Failed to write file: " + e.getMessage());
        }

        return HandleResult.success("Saved to " + file.getAbsolutePath());
    }

    public static Diagram load(String path) {
        File file = new File(path);

        try {
            FileReader reader = new FileReader(file, StandardCharsets.UTF_8);
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            reader.close();

            return Diagram.fromJson(new String(buffer));
        } catch (IOException e) {
            return null;
        }
    }
}
