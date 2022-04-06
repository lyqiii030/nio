package charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetDemo {
    public static void main(String[] args) throws CharacterCodingException {
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("qweqwe");
        charBuffer.flip();
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        System.out.println("编码之后");
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer decodeBuffer = charsetDecoder.decode(byteBuffer);
        System.out.println("解码之后：");
        System.out.println(decodeBuffer.toString());
    }
}
