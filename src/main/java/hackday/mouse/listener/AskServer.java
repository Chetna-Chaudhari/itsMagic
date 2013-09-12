package hackday.mouse.listener;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created with IntelliJ IDEA.
 * User: aniruddha.gangopadhyay
 * Date: 13/09/13
 * Time: 2:30 AM
 */
public class AskServer {

    public AskServer(){

    }
    public void init(){
        try {
            String name = "Ask";
            Ask engine = new AskImpl();
            Ask stub = (Ask) UnicastRemoteObject.exportObject(engine, 0);
            LocateRegistry LocateRegistry;
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1099);
            registry.rebind(name, stub);
            System.out.println("ask bound");
        } catch (Exception e) {
            System.err.println("ask exception:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AskServer server = new AskServer();
        server.init();

        try {
            String name = "Ask";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Ask comp = (Ask) registry.lookup(name);
            System.out.println(comp.askServer());

        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }

    }
}
