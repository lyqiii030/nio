package vchat.client;

import java.io.IOException;

public class ClientA {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("lyq");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
