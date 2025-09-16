import java.net.*;
import java.util.Random;

public class ClienteUDP {
    private static final String SERVER_IP = "192.168.0.1";
    private static final int PORTA = 6000;
    private static final int NUM_THREADS = 5;

    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 1; i <= NUM_THREADS; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    InetAddress endereco = InetAddress.getByName(SERVER_IP);

                    while (true) { // loop infinito
                        try {
                            Thread.sleep(rand.nextInt(5000)); // delay aleatório

                            String msg = "Thread UDP " + threadId + " envia valor aleatório " + rand.nextInt(1000);
                            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), endereco, PORTA);
                            socket.send(packet);

                            byte[] buffer = new byte[1024];
                            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                            socket.receive(reply);

                            System.out.println("Resposta UDP do servidor: " + new String(reply.getData(), 0, reply.getLength()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
