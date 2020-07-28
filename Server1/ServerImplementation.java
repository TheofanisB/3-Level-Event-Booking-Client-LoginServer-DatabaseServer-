
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerImplementation extends UnicastRemoteObject implements Operations {

    public ServerImplementation() throws Exception {//creating a server object 
        super();
    }

    public String connection() { // returns a  String saying  "Connected"
        return "Connected";
    }

    public boolean adduser(User obj) { // adds a new user and returns 0 or 1 depending if the user was actually added or not 
        return Functions.adduser(obj);
    }

    public boolean deleteuser(User obj) { // deletes a user and eturns 0 or 1 depending if the user was actually deleted or not 
        return Functions.deleteuser(obj);
    }

    public User login(String username, String password) { // checks if the user credentials are correct 
        User check = null;
        try {
            check = Functions.checkUsers(username, password);// login check 
            return check;
        } catch (IOException ex) {
            Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public void readfromserver() {//method that reads from the server 
        Functions.readfromserver();
    }
    
    public void readboolean() {////method that reads a boolean value from the user 
        Functions.readBool();
    }

    public boolean addevent(Event obj) {// Method that adds an event to the server 
        synchronized(this){//by using this , were making sure that one event gets added at a time 
        return Functions.addevent(obj);
        }
    }

    public boolean deleteevent(Event obj) {// Event Deletion method 
         synchronized(this){
        return Functions.deleteevent(obj);
         }
    }

    public void writeobj(Object obj) {// sends an object to the 2nd server 
         synchronized(this){
        Functions.writeobj(obj);
         }
    }

    public boolean readobj() { //reads an object from the 2nd server  
         synchronized(this){
                return Functions.readObject();
         }
     
    }

    public ArrayList searchlist() { //  returns the matching events according to the criteria 
        System.out.println("searchlist s1: "+Lists.searchevent.size());// printing the size of a list 
        //System.out.println("searchlist2 s1: "+((Event)Lists.searchevent.get(0)).getTitle());
        return Lists.searchevent;
    }
    
    public void clearlist(){// empties out the list 
        Lists.searchevent.clear();
    }
    
    public float resultorder(){//reads the cost of the order 
        return Functions.readfloatfromserver();
    }

    public boolean user_string(String action) { //sends a string to the user 
        boolean check = false;
        try {
            check = Functions.userstring(action);
            return check;
        } catch (IOException ex) {
            Logger.getLogger(ServerImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public void cancel() {// empties out the list and terminates the server 
        Lists.your_bookings.clear();
        Functions.cancelserver();
    }


    
}
