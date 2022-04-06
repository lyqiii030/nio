package channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class channelDemoWrite {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("D:\\李禹麒Java项目\\nio\\2.txt","rw");
        FileChannel fileChannel = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String data = "asdfhhjk 123123";
        buffer.clear();
        buffer.put(data.getBytes());
        buffer.flip();
        while(buffer.hasRemaining()){
            fileChannel.write(buffer);
        }
        fileChannel.close();

    }


}
