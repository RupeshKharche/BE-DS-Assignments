import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculatorClient {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        
        // Connect to the RMI registry
        String registryHost = args[0];
        Integer registryPort = Integer.parseInt(args[1]);
        assert registryHost != null;
        assert registryPort != null;

        Registry registry = LocateRegistry.getRegistry(registryHost, registryPort);

        CalculatorServerInterface calculator = (CalculatorServerInterface) registry.lookup("CalculatorServer");

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number 1: ");
        float num1 = sc.nextFloat();

        System.out.println("Enter number 2: ");
        float num2 = sc.nextFloat();

        System.out.println("Enter the operation to be performed (add / subtract / mulitply / divide): ");
        String operation = sc.next();

        float result = 0;
        switch (operation.toLowerCase()) {
            case "add":
                result = calculator.add(num1, num2);
                break;

            case "subtract":
                result = calculator.subtract(num1, num2);
                break;

            case "multiply":
                result = calculator.multiply(num1, num2);
                break;

            case "divide":
                result = calculator.divide(num1, num2);
                break;

            default:
                System.out.println("Unsupported operation: " + operation.toLowerCase());
                sc.close();
                break;
        }

        System.out.println("Result: " + result);
        sc.close();
    }
}
