import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ServidorTCP {
    private static final int PORTA = 5000;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORTA);
        System.out.println("[" + timestamp() + "] Servidor TCP iniciado na porta " + PORTA);

        while (true) {
            Socket cliente = server.accept();
            pool.submit(() -> atenderCliente(cliente));
        }
    }

    private static void atenderCliente(Socket cliente) {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println("[" + timestamp() + "] Thread " + threadName + " atendendo cliente: " + cliente.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

            String msg = in.readLine();
            System.out.println("[" + timestamp() + "] Thread " + threadName + " recebeu: " + msg);

            Thread.sleep(5000); // Simula processamento demorado

            out.write("Echo: " + msg + "\n");
            out.flush();

            System.out.println("[" + timestamp() + "] Thread " + threadName + " finalizou atendimento do cliente: " + cliente.getInetAddress());
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }
}
