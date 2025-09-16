import java.io.*;
import java.net.*;

public class ClienteTCP {
    private static final String SERVER_IP = "192.168.0.1";
    private static final int PORTA = 5000;

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) { // 3 threads de exemplo
            int threadId = i;
            new Thread(() -> {
                try {
                    Socket socket = new Socket(SERVER_IP, PORTA);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String msg = "Mensagem da thread " + threadId;
                    out.write(msg + "\n");
                    out.flush();

                    String resp = in.readLine();
                    System.out.println("Resposta do servidor: " + resp);

                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
