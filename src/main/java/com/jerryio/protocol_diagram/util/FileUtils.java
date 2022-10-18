package com.jerryio.protocol_diagram.util;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;

public class FileUtils {

    public static File resolvePath(String path, String suggestedExt) {
        if (path.isEmpty())
            return null;
        if (path.isBlank())
            return null;

        try {
            Path p = Paths.get(path).toAbsolutePath().normalize();
            File f = p.toFile();

            if (f.exists()) {
                if (f.isDirectory())
                    return null;
            } else {
                String fileName = p.getFileName().toString();

                // add extension and check again.
                // it is because it is possible that a directory called "name.json" exists.
                if (suggestedExt != null && fileName.indexOf('.') == -1)
                    return resolvePath(p.getParent().resolve(fileName + "." + suggestedExt).toString());
            }
            return f;
        } catch (Exception e) { // SecurityException, InvalidPathException, and more...
            return null;
        }
    }

    public static File resolvePath(String path) {
        return resolvePath(path, null);
    }

    public static HandleResult save(String path, Diagram d) {
        File file = resolvePath(path, "json");
        if (file == null)
            return fail("Invalid file path.");

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(d.toJson());
            writer.close();
        } catch (IOException e) {
            return fail("Failed to write file: " + e.getMessage());
        }

        return success("Saved to " + file.getAbsolutePath());
    }

    public static HandleResult export(String path, Diagram d) {
        File file = resolvePath(path);
        if (file == null)
            return fail("Invalid file path.");

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(d.toString());
            writer.close();
        } catch (IOException e) {
            return fail("Failed to write file: " + e.getMessage());
        }

        return success("Saved to " + file.getAbsolutePath());
    }

    public static Diagram load(String path) {
        File file = resolvePath(path, "json");
        if (file == null)
            return null;

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
