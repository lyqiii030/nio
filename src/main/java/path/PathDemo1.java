package path;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo1 {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\李禹麒Java项目\\nio\\1.txt");
        System.out.println(path.normalize());
    }
}
