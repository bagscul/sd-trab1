import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServidorTCP {
    private static final int PORTA = 5000;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORTA);
        System.out.println("Servidor TCP iniciado na porta " + PORTA);

        ExecutorService pool = Executors.newFixedThreadPool(10); // Threads paralelas

        while (true) {
            Socket cliente = server.accept();
            pool.submit(() -> {
                try {
                    System.out.println("Atendendo cliente: " + cliente.getInetAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

                    String msg = in.readLine();
                    System.out.println("Recebido: " + msg);

                    Thread.sleep(5000); // Simula processamento demorado
                    out.write("Echo: " + msg + "\n");
                    out.flush();

                    System.out.println("Cliente atendido: " + cliente.getInetAddress());
                    cliente.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
