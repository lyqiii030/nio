package files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesDemo1 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\李禹麒Java项目\\FileCreateTest");
        Path directory = Files.createDirectory(path);

        Files.copy(Paths.get("D:\\李禹麒Java项目\\FileCreateTest"),
                Paths.get("D:\\李禹麒Java项目\\FileTest2"),
                StandardCopyOption.REPLACE_EXISTING); //覆盖已存在的

//        移动以及重命名
        Path sourcePath = Paths.get("D:\\李禹麒Java项目\\FileTest2");
        Path destinationPath = Paths.get("D:\\李禹麒Java项目\\FileTest233");
        Files.move(sourcePath,destinationPath,StandardCopyOption.REPLACE_EXISTING);

//        删除 通过files类操作
        Path  path1 = Paths.get("D:\\李禹麒Java项目\\FileTest233");
        Files.delete(path1);

        //通过文件目录 以及文件名查找文件
        Path rootPath = Paths.get("D:\\李禹麒Java项目\\");
        String fileToFind = File.separator+"123.txt";
        Files.walkFileTree(rootPath,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileString = file.toAbsolutePath().toString();
                if (fileString.endsWith(fileToFind)){
                    System.out.println("file found at path:"+file.toAbsolutePath());
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
