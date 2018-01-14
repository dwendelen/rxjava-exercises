package phoned.notification;

import rx.Observable;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSystemNotificationService implements NotificationService {
    private FileSystem fileSystem;

    private Path directory;

    public FileSystemNotificationService(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void init() throws IOException {
        directory = Files.createTempDirectory("phoned-notifications-");
        directory.toFile().deleteOnExit();
    }

    public void printDirectoryAndContent() throws IOException {
        System.out.println("Notifications in " + directory.toString());

        fileSystem.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                System.out.println("Found file: " + path.toString());

                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public Observable<Notification> getNotifications() {
        //TODO Poll for new files and emit notifications
        return Observable.empty();
    }

    private Notification createNotificationFromFile(Path path) throws IOException {
        Stream<String> lines = fileSystem.lines(path);

        Notification notification = new Notification();
        notification.id = path.getName(-1).toString();
        notification.title = lines.findFirst().orElse("<empty file>");
        notification.body = lines.skip(1).collect(Collectors.joining("\n"));

        return notification;
    }
}
