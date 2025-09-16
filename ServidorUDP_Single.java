import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorUDP_Single {
    private static final int PORTA = 6000;

    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(PORTA);
        System.out.println("[" + timestamp() + "] Servidor UDP Single-thread iniciado na porta " + PORTA);

        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);

            String msg = new String(packet.getData(), 0, packet.getLength());
            System.out.println("[" + timestamp() + "] Recebido: " + msg + " de " + packet.getAddress());

            Thread.sleep(3000); // Simula processamento

            byte[] resp = ("Echo UDP: " + msg).getBytes();
            DatagramPacket reply = new DatagramPacket(resp, resp.length, packet.getAddress(), packet.getPort());
            server.send(reply);

            System.out.println("[" + timestamp() + "] Cliente UDP atendido: " + packet.getAddress());
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }
}
