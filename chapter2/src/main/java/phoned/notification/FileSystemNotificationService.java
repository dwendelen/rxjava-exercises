package phoned.notification;

import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileSystemNotificationService implements NotificationService {
    private FileSystem fileSystem;
    private int pollingInterval;

    private Path directory;
    private Observable<Notification> notifications;

    public FileSystemNotificationService(FileSystem fileSystem, int pollingInterval) {
        this.fileSystem = fileSystem;
        this.pollingInterval = pollingInterval;
    }

    public void init() throws IOException {
        directory = Files.createTempDirectory("phoned-notifications-");
        directory.toFile().deleteOnExit();

        notifications = Observable.<Notification>create(sub -> {
            Runnable r = () -> {
                final Set<Path> filesLastIteration = new HashSet<>();
                try {
                    while(!sub.isUnsubscribed()) {
                        Set<Path> filesThisIteration = walkTheDirectoryAndEmitNewFiles(sub, filesLastIteration);
                        filesLastIteration.clear();
                        filesLastIteration.addAll(filesThisIteration);
                        Thread.sleep(pollingInterval);
                    }
                } catch (Exception e) {
                    sub.onError(e);
                }
            };
            new Thread(r).start();
        }).share();
    }

    private Set<Path> walkTheDirectoryAndEmitNewFiles(Subscriber<? super Notification> sub, Set<Path> filesInPreviousIteration) throws IOException {
        Set<Path> filesThisIteration = new HashSet<>();
        fileSystem.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                filesThisIteration.add(path);
                if(!filesInPreviousIteration.contains(path)) {
                    Notification notification = createNotificationFromFile(path);
                    sub.onNext(notification);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return filesThisIteration;
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
        return notifications;
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

    @Override
    public void removeNotification(String id) {
        Path path = directory.resolve(id);
        fileSystem.deleteFileIfExists(path);
    }
}
