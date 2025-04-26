import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface defines the methods to be implemented by the remote server.
 */
public interface CalculatorServerInterface extends Remote {
    
    float add(float num1, float num2) throws RemoteException;
    float subtract(float num1, float num2) throws RemoteException;
    float multiply(float num1, float num2) throws RemoteException;
    float divide(float num1, float num2) throws RemoteException;
}