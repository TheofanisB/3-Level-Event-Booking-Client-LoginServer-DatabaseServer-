// ΜΠΑΚΙΤΑΣ ΘΕΟΦΑΝΗΣ 321/2015133 ΟΜΑΔΙΚΟ PROJECT ΚΑΤΑΝΕΜΗΜΕΝΑ 
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            ServerImplementation s= new ServerImplementation();// creates a server 
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            Naming.rebind("localhost", s);//binds the ip and the server 
            System.out.println("RMI Server is running ...");
            Lists.users.add(new User("ADMIN", "ADMIN", "ADMIN", "takis123@takis123.gr", "TAKISSOUGIAS"));//default admin
            Lists.users.add(new User("USER", "USER", "USER", "mpampis123@takis123.gr", "MPAMPISOSOUGIAS"));//default user
            Functions.connectserver();// connection command 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}