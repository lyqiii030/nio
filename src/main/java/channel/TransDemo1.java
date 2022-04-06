package channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransDemo1 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\2.txt","rw");
        RandomAccessFile bFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\3.txt","rw");
        FileChannel channelAFrom = aFile.getChannel();
        FileChannel channelBTo = bFile.getChannel();
        long position = 0;
        long count = channelAFrom.size();
        channelBTo.transferFrom(channelAFrom,position,count);
        channelAFrom.close();
        channelBTo.close();
        System.out.println("ok");


    }

}
