import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ServidorUDP {
    private static final int PORTA = 6000;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(PORTA);
        System.out.println("[" + timestamp() + "] Servidor UDP iniciado na porta " + PORTA);

        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);

            pool.submit(() -> atenderClienteUDP(packet, server));
        }
    }

    private static void atenderClienteUDP(DatagramPacket packet, DatagramSocket server) {
        String threadName = Thread.currentThread().getName();
        try {
            String msg = new String(packet.getData(), 0, packet.getLength());
            System.out.println("[" + timestamp() + "] Thread " + threadName + " recebeu: " + msg + " de " + packet.getAddress());

            Thread.sleep(3000); // Simula processamento

            byte[] resp = ("Echo UDP: " + msg).getBytes();
            DatagramPacket reply = new DatagramPacket(resp, resp.length, packet.getAddress(), packet.getPort());
            server.send(reply);

            System.out.println("[" + timestamp() + "] Thread " + threadName + " finalizou atendimento UDP: " + packet.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }
}
