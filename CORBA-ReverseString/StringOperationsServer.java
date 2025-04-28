import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ReverseModule.StringOperations;
import ReverseModule.StringOperationsHelper;

public class StringOperationsServer {

    public static void main(String[] args) throws Exception {
        // Initialize ORB
        ORB orb = ORB.init(args, null);

        // Activate POA manager
        POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();

        StringOperationsImpl stringOperationsImpl = new StringOperationsImpl();

        org.omg.CORBA.Object ref = rootPOA.servant_to_reference(stringOperationsImpl);
        StringOperations href = StringOperationsHelper.narrow(ref);

        NamingContextExt ncRef = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));

        NameComponent path[] = ncRef.to_name("StringOperations");
        ncRef.rebind(path, href);

        System.out.println("Server ready and waiting...");

        orb.run();
    }
}
