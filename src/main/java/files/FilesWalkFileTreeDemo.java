package files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class FilesWalkFileTreeDemo {
// alt shift m 快速提取方法

    public static void main(String[] args) {
}

    private static void m1() throws IOException {
        AtomicInteger countJava = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\JAVA工作"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")){
                    countJava.incrementAndGet();
                    System.out.println(file);
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("总数为"+countJava);
    }


    private static void m2() throws IOException {
        AtomicInteger dircount = new AtomicInteger();
        AtomicInteger filecount = new AtomicInteger();

        Files.walkFileTree(Paths.get("D:\\JAVA工作"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("=====>"+dir);
                dircount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                filecount.incrementAndGet();
                System.out.println(file);
                return super.visitFile(file, attrs);
            }
        });

        System.out.println("dir count:"+dircount);
        System.out.println("file count:"+filecount);
    }
}
