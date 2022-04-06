package pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
    public static void main(String[] args)throws Exception {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("liyuqi".getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);
        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();
        int length = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(),0,length));
        sinkChannel.close();
        sourceChannel.close();

    }
}
