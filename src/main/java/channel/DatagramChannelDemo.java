package channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class DatagramChannelDemo {
        @Test
        public void sendDatagram() throws IOException, InterruptedException {
                DatagramChannel sendChannel = DatagramChannel.open();
                InetSocketAddress sendAddress =
                        new InetSocketAddress("localhost",8888);
                while (true){
                        ByteBuffer buffer =
                                ByteBuffer.wrap("send it李禹麒".getBytes("UTF-8"));
                        sendChannel.send(buffer,sendAddress);
                        System.out.println("send success");
                        Thread.sleep(1000);
                }
        }

        @Test
        public void receiveDatagram() throws IOException {
                //打开DatagramChannel
                DatagramChannel receiveChannel = DatagramChannel.open();
                InetSocketAddress receiveAddress = new InetSocketAddress(8888);
                receiveChannel.bind(receiveAddress);
                ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
                while (true){
                      receiveBuffer.clear();
                        SocketAddress socketAddress
                                = receiveChannel.receive(receiveBuffer);
                        receiveBuffer.flip();
                        System.out.println(socketAddress.toString());
                        System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
                }
        }
        @Test
        public void testConnect() throws IOException {
                DatagramChannel connChannel = DatagramChannel.open();
                connChannel.bind(new InetSocketAddress(8888));
                connChannel.connect(new InetSocketAddress("localhost",8888));
                connChannel.write(
                        ByteBuffer.wrap("send it 李禹麒".getBytes("UTF-8"))
                );
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                while(true){
                       readBuffer.clear();
                       connChannel.read(readBuffer);
                       readBuffer.flip();
                        System.out.println(Charset.forName("UTF-8").decode(readBuffer));
                }
        }

}
