
import javax.swing.*;
import java.net.*;
import java.rmi.*;
import java.util.ArrayList;

public interface Operations extends Remote {// Interface to communicate with the server 

    public String connection() throws RemoteException;// connection method  

    public boolean adduser(User obj) throws RemoteException;// new user creation method 

    public boolean deleteuser(User obj) throws RemoteException;//user deletion method  

    public User login(String username, String password) throws RemoteException;// user connection method 

    public void writeobj(Object obj) throws RemoteException;//method that sends an object to the database  

    public boolean readobj() throws RemoteException;// method that reads an object from the database 

    public void readboolean() throws RemoteException;// method that reads a boolean value 

    public ArrayList searchlist() throws RemoteException; // method that searches the list 

    public void clearlist() throws RemoteException;// method that empties the list  

    public boolean user_string(String action) throws RemoteException; // method that sends a string to the server 

    public void readfromserver() throws RemoteException;// method that reads from the server 

    public float resultorder() throws RemoteException;// method that returns the results of the order to the client 

    public boolean addevent(Event obj) throws RemoteException;//adding an event method 

    public boolean deleteevent(Event obj) throws RemoteException;//deleting an event method 

    public void cancel() throws RemoteException;// cancelling an order method 



}
