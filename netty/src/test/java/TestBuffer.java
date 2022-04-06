import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("1.txt").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
     while (true){
         int read = channel.read(byteBuffer);
         log.debug("读取到的字节数：{}",read);
         if (read==-1){
         break;
         }
         byteBuffer.flip();
         while (byteBuffer.hasRemaining()){
             byte b = byteBuffer.get();
             log.debug("实际字节{}",(char)b);
         }
         byteBuffer.clear();
     }
        } catch (IOException e) {
        }

    }
    @Test
    public void test(){

    }
}
