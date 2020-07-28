
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Functions {

    
    protected static ObjectOutputStream objectOut;//Streams clarification
    protected static ObjectInputStream objinstream;

    protected static void createstream(Socket sock) {// Method that creates the streams 
        try {
            //Initialization
            objectOut = new ObjectOutputStream(sock.getOutputStream());
            objinstream = new ObjectInputStream(sock.getInputStream());

            objectOut.writeUTF("START");//Sending a message from the Client to the Server
            objectOut.flush();//Flushing the memory 
            System.out.println("SERVER2 -> " + objinstream.readUTF());//Printing the message that server2 sent 
        } catch (IOException e) {
            System.out.println("Write error");
        }
    }

    protected static User checkUsers(String username, String password) throws IOException {// Method that check the users during login 
        for (int i = 0; i < Lists.users.size(); i++) {//checks for matches on every element in the list 
            if (Lists.users.get(i).getUsername().equals(username) && Lists.users.get(i).getPassword().equals(password)) {
                System.out.println("TYPE " + Lists.users.get(i).getType());
                if ((Lists.users.get(i).getType()).equals("ADMIN")) {// if its an admin 
                    objectOut.writeUTF("ADMIN");
                    objectOut.flush();
                    return Lists.users.get(i);
                } else {//if its a user user
                    objectOut.writeUTF("USER");
                    objectOut.flush();
                    return Lists.users.get(i);
                }
            }
        }
        return null;
    }

    protected static void writeobj(Object obj) {//sends an object to the 2nd server 
        try {
            objectOut.writeObject(obj);
            objectOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected static boolean userstring(String strin) throws IOException {//sending a string to the 2nd server 
        objectOut.writeUTF(strin);
        objectOut.flush();
        return objinstream.readBoolean();//reads a boolean type stream  
    }

    protected static void readfromserver() {//reads from the 2nd server 
        try {
            System.out.println("SERVER2 -> " + objinstream.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void readBool() {//reads a boolean value from the 2nd server 
        try {
            objinstream.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static float readfloatfromserver() {//reads a float number from the 2nd server ( Used for the price) 
        float result = -1;
        try {
            result = objinstream.readFloat();
            System.out.println("SERVER2 -> " + result);
            return result;
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    protected static boolean readObject() {//reads an object from the 2nd server 
        Object obj = null;
        boolean check = false;
        try {
            do {
                obj = objinstream.readObject();
                if (obj != null) {
                    //System.out.println("96 " + ((Event) obj).toString());
                    Lists.searchevent.add(obj);
                    check = true;
                }else{
                  //  System.out.println("prepei na bgο apo tin while");
                }
            } while (obj != null);
          
            if(!check){
                Lists.searchevent.clear();
            }
            return check;
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("eimai ekso apo to try catch");
        return check;

    }

    protected static boolean addevent(Event obj) {// Adding an object to the 2nd server 
        try {
            objectOut.writeObject(obj);
            objectOut.flush();
            return objinstream.readBoolean();//reads if the object was added or not 
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    protected static boolean adduser(User obj) {//adds a new user to the list 

        for (int i = 0; i < Lists.users.size(); i++) {
            if (obj.getUsername().equals(Lists.users.get(i).getUsername())) {
                return false;
            }

        }
        Lists.users.add(obj);
        return true;
    }

    protected static boolean deleteuser(User obj) {//reads a user object and then deletes it if its found 
        for (int i = 0; i < Lists.users.size(); i++) {
            if (obj.getUsername().equals(Lists.users.get(i).getUsername())) {
                Lists.users.remove(Lists.users.get(i));
                return true;
            }
        }
        return false;
    }

    protected static boolean deleteevent(Event obj) {//Function that sends the event to the 2nd server and reads if it was deleted or not 
        try {
            objectOut.writeObject(obj);
            objectOut.flush();
            return objinstream.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    protected static void username(String username) {//returns the type of the username 
        try {
            if (username.equals("ADMIN")) {//if its an  admin
                objectOut.writeUTF("ADMIN");
                objectOut.flush();

            } else { //if its a user  
                objectOut.writeObject("USER");
                objectOut.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void connectserver() {// connection function 
        try {
            
            Socket sock = new Socket("localhost", 5555);//Creating a  Socket

           //Connection Messages  
            System.out.println("Sending Messages to the Server...");
            System.out.println("Connecting to " + sock.getInetAddress() + " and port " + sock.getPort());
            System.out.println("Local Address :" + sock.getLocalAddress() + " Port:" + sock.getLocalPort());

            createstream(sock);
        } catch (IOException ex) {
            System.out.println("Connection Refused!!!");

        }
    }

    protected static void cancelserver() {// Sends "Cancel" to stop the connection 
        try {
            objectOut.writeUTF("CANCEL");
            objectOut.flush();
            System.out.println("SERVER2 -> " + objinstream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
