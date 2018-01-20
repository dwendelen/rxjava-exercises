package phoned.notification;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileSystem {
    public Stream<String> lines(Path path) throws IOException {
        return Files.lines(path);
    }

    public void walkFileTree(Path directory, FileVisitor<Path> fileVisitor) throws IOException {
        Files.walkFileTree(directory, fileVisitor);
    }

    public void deleteFileIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
