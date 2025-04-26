import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 * This class configures the name of the server for the rmi registry.
 */
public class CalculatorServer {
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try {
            String registryHost = args[0];
            Integer registryPort = Integer.parseInt(args[1]);
            assert registryHost != null;
            assert registryPort != null;

            System.setProperty("java.rmi.server.hostname", registryHost);

            // Create instance of the calculator implementation
            CalculatorServerImpl calculatorServerImpl = new CalculatorServerImpl();

            String rmiURL = "rmi://" + registryHost + ":" + registryPort + "/CalculatorServer";
            // Set name of the calculator impl for the RMI registry
            Naming.rebind(rmiURL, calculatorServerImpl);

            System.out.println("Server started successfully");
        } catch(Exception exception) {
            System.out.println("Failed to start server with message:\n " + exception.getMessage());
        }
    }
}
