import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import ReverseModule.StringOperations;
import ReverseModule.StringOperationsHelper;

public class StringOperationsClient {
    
    public static void main(String[] args) throws Exception {
        ORB orb = ORB.init(args, null);

        // Get instance of name service
        NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

        // Get instance of servant
        StringOperations stringOperations = StringOperationsHelper.narrow(ncRef.resolve_str("StringOperations"));

        String result = stringOperations.reverse("Hello");
        System.out.println("Reverse of Hello is " + result);
    }
}
