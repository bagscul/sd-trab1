import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServidorTCP_Single {
    private static final int PORTA = 5000;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORTA);
        System.out.println("[" + timestamp() + "] Servidor TCP Single-thread iniciado na porta " + PORTA);

        while (true) {
            Socket cliente = server.accept();
            System.out.println("[" + timestamp() + "] Atendendo cliente: " + cliente.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

            try {
                String msg = in.readLine();
                System.out.println("[" + timestamp() + "] Recebido: " + msg);

                Thread.sleep(5000); // Simula processamento
                out.write("Echo: " + msg + "\n");
                out.flush();

                System.out.println("[" + timestamp() + "] Cliente atendido: " + cliente.getInetAddress());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cliente.close();
            }
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }
}
