import java.io.*;
import java.net.*;
import java.util.Random;

public class ClienteTCP_Single {
    private static final String SERVER_IP = "192.168.0.1";
    private static final int PORTA = 5000;
    private static final int NUM_MSG = 5; // número de mensagens enviadas

    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 1; i <= NUM_MSG; i++) {
            try {
                Socket socket = new Socket(SERVER_IP, PORTA);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String msg = "Mensagem " + i + " número aleatório " + rand.nextInt(100);
                out.write(msg + "\n");
                out.flush();

                String resp = in.readLine();
                System.out.println("Resposta TCP: " + resp);

                socket.close();

                Thread.sleep(rand.nextInt(3000)); // delay aleatório entre mensagens
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
