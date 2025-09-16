import java.net.*;
import java.util.Random;

public class ClienteUDP_Single {
    private static final String SERVER_IP = "192.168.0.1";
    private static final int PORTA = 6000;
    private static final int NUM_MSG = 5;

    public static void main(String[] args) {
        Random rand = new Random();

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress endereco = InetAddress.getByName(SERVER_IP);

            for (int i = 1; i <= NUM_MSG; i++) {
                String msg = "Mensagem UDP " + i + " valor aleatório " + rand.nextInt(1000);
                DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), endereco, PORTA);
                socket.send(packet);

                byte[] buffer = new byte[1024];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                socket.receive(reply);

                System.out.println("Resposta UDP: " + new String(reply.getData(), 0, reply.getLength()));

                Thread.sleep(rand.nextInt(3000)); // delay aleatório
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
