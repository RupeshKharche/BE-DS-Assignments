import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import CalculatorModule.Calculator;
import CalculatorModule.CalculatorHelper;

public class CalculatorServer {

    public static void main(String[] args) throws Exception {
        // Initialize ORB
        ORB orb = ORB.init(args, null);

        // Activate POA Manager
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();

        // Create instance of servant
        CalculatorImpl calculatorImpl = new CalculatorImpl();

        Object ref = rootPOA.servant_to_reference(calculatorImpl);
        Calculator href = CalculatorHelper.narrow(ref);

        // Bind object in the naming service
        Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        NameComponent path[] = ncRef.to_name("Calculator");
        ncRef.rebind(path, href);

        System.out.println("Calculator Server ready and waiting...");

        // Run orb
        orb.run();
    }
}
