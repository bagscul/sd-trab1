import java.io.*;
import java.net.*;
import java.util.Random;

public class ClienteTCP {
    private static final String SERVER_IP = "192.168.0.1";
    private static final int PORTA = 5000;
    private static final int NUM_THREADS = 5; // número de threads simultâneas

    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 1; i <= NUM_THREADS; i++) {
            int threadId = i;
            new Thread(() -> {
                while (true) { // loop infinito
                    try {
                        Thread.sleep(rand.nextInt(5000)); // delay aleatório entre envios

                        Socket socket = new Socket(SERVER_IP, PORTA);
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        String msg = "Thread " + threadId + " envia número aleatório " + rand.nextInt(100);
                        out.write(msg + "\n");
                        out.flush();

                        String resp = in.readLine();
                        System.out.println("Resposta TCP do servidor: " + resp);

                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
