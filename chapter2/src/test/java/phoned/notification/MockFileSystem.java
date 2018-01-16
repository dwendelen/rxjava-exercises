package phoned.notification;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.util.stream.Stream;

public class MockFileSystem extends FileSystem {
    private int walkInvocations = 0;
    private IOException exceptionToThrow;
    private int[] files = {};

    public void doThrow(IOException exceptionToThrow) {
        this.exceptionToThrow = exceptionToThrow;
    }

    public void mockFiles(int... files) {
        this.files = files;
    }

    @Override
    public Stream<String> lines(Path path) throws IOException {
        if (exceptionToThrow != null) {
            throw exceptionToThrow;
        }

        for (int file : files) {
            if(path.endsWith(filename(file))) {
                return Stream.of(
                       title(file),
                        "Body " + file + " line 1",
                        "Body " + file + " line 2",
                        "Body " + file + " line 3"
                );
            }
        }

        throw new IOException("Unknown file " + path);
    }

    @Override
    public void walkFileTree(Path path, FileVisitor<Path> visitor) throws IOException {
        walkInvocations++;

        if (exceptionToThrow != null) {
            throw exceptionToThrow;
        }

        for (int file : files) {
            visitor.visitFile(path.resolve(filename(file)), null);
        }
    }

    public int getNbOfWalkInvocations() {
        return walkInvocations;
    }

    public static String filename(int file) {
        return "file" + file + ".txt";
    }

    public static String title(int file) {
        return "Title " + file;
    }

    public static String body(int file) {
        return
                "Body " + file + " line 1\n" +
                "Body " + file + " line 2\n" +
                "Body " + file + " line 3";
    }
}
