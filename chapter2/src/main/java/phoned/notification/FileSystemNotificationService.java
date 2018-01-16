package phoned.notification;

import rx.Observable;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Collectors;

public class FileSystemNotificationService implements NotificationService {
    private FileSystem fileSystem;
    private int pollingInterval;

    private Path directory;

    public FileSystemNotificationService(FileSystem fileSystem, int pollingInterval) {
        this.fileSystem = fileSystem;
        this.pollingInterval = pollingInterval;
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
                System.out.println("Found file: " + path.getName(path.getNameCount() - 1).toString());

                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public Observable<Notification> getNotifications() {
        //TODO Poll for new files and emit notifications
        //TODO Remove dummy
        Notification dummy = new Notification();
        dummy.id = "dummy";
        dummy.title = "dummy";
        dummy.body = "This is a dummy notification.\nIt should be deleted.";

        return Observable.just(dummy);
    }

    private Notification createNotificationFromFile(Path path) throws IOException {
        Notification notification = new Notification();
        notification.id = path.getName(path.getNameCount() - 1).toString();
        notification.title = fileSystem.lines(path)
                .findFirst()
                .orElse("<empty file>");
        notification.body = fileSystem.lines(path)
                .skip(1)
                .collect(Collectors.joining("\n"));

        return notification;
    }
}
