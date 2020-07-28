// ΜΠΑΚΙΤΑΣ ΘΕΟΦΑΝΗΣ 321/2015133 ΟΜΑΔΙΚΟ PROJECT ΚΑΤΑΝΕΜΗΜΕΝΑ 
import java.net.*;
import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//driver
public class Main {
    
    
    private static Operations look_up;//creating the interface to connect with the 1st server 
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException  {
        try {
            String url="localhost";
            Operations look_up =(Operations) Naming.lookup(url);// RMI connection 
            System.out.println(look_up.connection());//connection request 
            PClient2_GUI.firstframe(look_up);//pops up the first graphics window 
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

    }
}
/* 
Admin Account :
Username:ADMIN
Password: ADMIN 



User Account : 
Username:USER
Password: USER 
*/