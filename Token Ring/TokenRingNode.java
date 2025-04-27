import java.io.*;
import java.net.*;

public class TokenRingNode {
    private static String nodeHost, nextHost, startHost;
    private static int nodePort, nextPort, startPort;
    private static boolean hasToken = false;
    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        if (args.length != 6) {
            System.out.println("Usage: java TokenRingNode <host> <port> <next-host> <next-port> <start-host> <start-port>");
            return;
        }

        nodeHost = args[0];
        nodePort = Integer.parseInt(args[1]);
        nextHost = args[2];
        nextPort = Integer.parseInt(args[3]);
        startHost = args[4];
        startPort = Integer.parseInt(args[5]);

        ServerSocket serverSocket = new ServerSocket(nodePort);
        System.out.println("[" + nodeHost + ":" + nodePort + "] Listening...");

        // Handle incoming token
        Thread listener = new Thread(() -> {
            while (running) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String msg = in.readLine();
                    if ("TOKEN".equals(msg)) {
                        hasToken = true;
                        criticalSection();
                        passToken();
                    }
                } catch (IOException ignored) {}
            }
        });
        listener.start();

        // If this is the start node, send token first
        if (nodeHost.equals(startHost) && nodePort == startPort) {
            Thread.sleep(3000);  // Wait for all nodes to start
            hasToken = true;
            criticalSection();
            passToken();
        }

        // Graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            running = false;
            try {
                serverSocket.close();
            } catch (IOException ignored) {}
        }));

        listener.join();
    }

    private static void criticalSection() {
        if (!hasToken) return;
        System.out.println("[" + nodeHost + ":" + nodePort + "] Entering critical section...");
        try {
            Thread.sleep(2000);  // Simulate work
        } catch (InterruptedException ignored) {}
        System.out.println("[" + nodeHost + ":" + nodePort + "] Exiting critical section...");
    }

    private static void passToken() {
        if (!hasToken) return;

        for (int attempts = 0; attempts < 5; attempts++) {
            try (Socket socket = new Socket(nextHost, nextPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println("TOKEN");
                System.out.println("[" + nodeHost + ":" + nodePort + "] Passed token to " + nextHost + ":" + nextPort);
                hasToken = false;
                return;
            } catch (IOException e) {
                System.err.println("Retrying to send token... (" + (attempts + 1) + ")");
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
        }

        System.err.println("Failed to send token to " + nextHost + ":" + nextPort + " after retries.");
    }
}
