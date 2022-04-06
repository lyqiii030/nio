package vchat.client;

import java.io.IOException;

public class ClientB {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("xc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
