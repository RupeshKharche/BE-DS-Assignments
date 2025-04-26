import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * This class provides actual implementation for ServerInterface.
 */
public class CalculatorServerImpl extends UnicastRemoteObject implements CalculatorServerInterface {

    protected CalculatorServerImpl() throws RemoteException {
        super();
    }

    @Override
    public float add(float num1, float num2) throws RemoteException {
        System.out.println("Received request: add(" + num1 + ", " + num2 + ")");
        return num1 + num2;
    }

    @Override
    public float subtract(float num1, float num2) throws RemoteException {
        System.out.println("Received request: subtract(" + num1 + ", " + num2 + ")");
        return num1 - num2;
    }

    @Override
    public float multiply(float num1, float num2) throws RemoteException {
        System.out.println("Received request: multiply(" + num1 + ", " + num2 + ")");
        return num1 * num2;
    }

    @Override
    public float divide(float num1, float num2) throws RemoteException {
        if(num2 == 0) {
            throw new ArithmeticException("Division by zero");
        }
        System.out.println("Received request: divide(" + num1 + ", " + num2 + ")");
        return num1 / num2;
    }
}
