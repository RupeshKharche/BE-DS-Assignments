import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import CalculatorModule.Calculator;
import CalculatorModule.CalculatorHelper;

public class CalculatorClient {

    public static void main(String[] args) throws Exception {
        // Intialize ORB
        ORB orb = ORB.init(args, null);

        // Get instance of name service
        Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // Get instance of servant through name service
        Calculator calc = CalculatorHelper.narrow(ncRef.resolve_str("Calculator"));

        // Call remote methods
        float addition = calc.add(10, 20);
        System.out.println("10 + 20 = " + addition);
    }
}
