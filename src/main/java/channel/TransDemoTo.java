package channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransDemoTo {
    public static void main(String[] args) throws  Exception{
        RandomAccessFile aFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\3.txt","rw");
        RandomAccessFile bFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\4.txt", "rw");
        FileChannel channelFrom = aFile.getChannel();
        FileChannel channelTo = bFile.getChannel();
        long position = 0;
        long count = channelFrom.size();
        channelFrom.transferTo(position,count,channelTo);
        channelFrom.close();
        channelTo.close();
        System.out.println("over");


    }
}
