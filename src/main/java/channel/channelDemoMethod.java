package channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class channelDemoMethod {

    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\2.txt","rw");
        FileChannel channel = aFile.getChannel();
        long size = channel.size();
        System.out.println(size);



    }

}
