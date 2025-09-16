import java.net.*;

public class ClienteUDP {
    private static final String SERVER_IP = "192.168.0.1";
    private static final int PORTA = 6000;

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    InetAddress endereco = InetAddress.getByName(SERVER_IP);

                    String msg = "Mensagem UDP thread " + threadId;
                    DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), endereco, PORTA);
                    socket.send(packet);

                    byte[] buffer = new byte[1024];
                    DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                    socket.receive(reply);

                    System.out.println("Resposta do servidor UDP: " + new String(reply.getData(), 0, reply.getLength()));
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
