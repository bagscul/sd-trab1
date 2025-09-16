import java.net.*;
import java.util.concurrent.*;

public class ServidorUDP {
    private static final int PORTA = 6000;

    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(PORTA);
        System.out.println("Servidor UDP iniciado na porta " + PORTA);

        ExecutorService pool = Executors.newFixedThreadPool(10);
        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);

            pool.submit(() -> {
                try {
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Recebido: " + msg + " de " + packet.getAddress());

                    Thread.sleep(3000); // Simula processamento

                    byte[] resp = ("Echo UDP: " + msg).getBytes();
                    DatagramPacket reply = new DatagramPacket(resp, resp.length, packet.getAddress(), packet.getPort());
                    server.send(reply);

                    System.out.println("Cliente UDP atendido: " + packet.getAddress());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
