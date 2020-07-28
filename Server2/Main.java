
import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        //Event(String title, String genre, String event_date, int available_seats, int cost_per_seat)
        // creating some events by default 
        Event event1 = new Event("Titlos1", "Horror", "29-04-2020", "15:00", 20, 10);
        Event event2 = new Event("Titlos2", "Horror", "01-05-2020", "10:00", 30, 30);
        Event event3 = new Event("Titlo3", "Horror", "01-05-2020", "10:00", 40, 40);
        Event event4 = new Event("Titlo4", "Horror", "01-05-2020", "10:00", 50, 50);

        Lists.event_list.add(event1);
        Lists.event_list.add(event2);
        Lists.event_list.add(event3);
        Lists.event_list.add(event4);

        try {
            ServerSocket server = new ServerSocket(5555);//Creating a  ServerSocket
            while (true) {
              
                System.out.println("Accepting Connection...");  //Messages to connect to the server
                System.out.println("Local Address :" + server.getInetAddress() + " Port:" + server.getLocalPort());
               Socket sock = server.accept();  //Creating the socket 

                
                ObjectOutputStream objoutstream = new ObjectOutputStream(sock.getOutputStream());// Stream Initialization 
                ObjectInputStream objinstream = new ObjectInputStream(sock.getInputStream());

                String strin = objinstream.readUTF();//Reads a String 
                Object obj = null, obj2 = null;//  Creating an object that will receive or send data to  and from the server
                System.out.println("SERVER1 -> " + strin);// info message 
             // COMMUNICATION PROTOCOL ΜΕ ΤΟ ΥΠΌΛΟΙΠΟ ΣΎΣΤΗΜΑ ( SERVER 1 ΚΑΙ CLIENTS )
                if (strin.equals("START")) {// 
                    objoutstream.writeUTF("WAITING");//Sends  "Waiting" to the Client 
                    objoutstream.flush();
                    
                    do {//Loop that will keep going until the communication is over 
                        strin = objinstream.readUTF();//Reading from the client what the next action is going to be 
                        System.out.println("SERVER1 -> " + strin);//Prints a message  from the Client 
                        String strin2;
                        if (strin.equals("ADMIN")) {//if it receives "Admin" then the next command will have access permissions 
                            do {
                                strin2 = objinstream.readUTF();// reads a command  
                                System.out.println("SERVER1 -> " + strin2);
                                if (strin2.equals("ADD EVENT")) {// add an event command 
                                    objoutstream.writeBoolean(true);
                                    objoutstream.flush();
                                    objoutstream.writeUTF("WAITING FOR EVENT INFO");// sends the client that were waiting for an event 
                                    objoutstream.flush();
                                    do {
                                        obj = objinstream.readObject();//reads an object (event object) 
                                        if (obj != null) {
                                            boolean check = false;
                                            check = Functions.addevent(obj);//  Tries to add it . Checks whether it was added or not and lets the client know 
                                            objoutstream.writeBoolean(check);//Sends the result 
                                            objoutstream.flush();
                                            if (check) {// checks if the result was positive 
                                                objoutstream.writeUTF("ADDED");
                                                objoutstream.flush();
                                            } else { //it case it was not added 
                                                objoutstream.writeUTF("DENIED");
                                                objoutstream.flush();
                                            }
                                        }
                                    } while (obj != null);
                                    objoutstream.writeUTF("WAITING FOR NEXT ACTION");// Waiting
                                    objoutstream.flush();
                                } else if (strin2.equals("DELETE")) {// if it receives  DELETE 
                                    objoutstream.writeBoolean(true);// 
                                    objoutstream.flush();
                                    objoutstream.writeUTF("WAITING FOR INFO OF EVENT TO DELETE");
                                    objoutstream.flush();
                                    do {
                                        obj2 = objinstream.readObject(); // reads the object to be deleted 
                                        if (obj2 != null) {
                                            boolean check = false;
                                            check = Functions.deleteEvent(obj2);// tries to delete it 
                                            objoutstream.writeBoolean(check); // returns if it was deleted or not 
                                            objoutstream.flush();
                                            if (check) {
                                                objoutstream.writeUTF("DELETED");// returns that it was in fact deleted 
                                                objoutstream.flush();
                                            } else {
                                                objoutstream.writeUTF("Not Found");// Returns if there's such an object in the list 
                                                objoutstream.flush();
                                            }
                                        }
                                    } while (obj2 != null);
                                    objoutstream.writeUTF("WAITING FOR NEXT ACTION");//The Server informs the client that he's  Waiting
                                    objoutstream.flush();//Clearing out the memory 
                                }
                            } while (!strin2.equals("EXIT"));// If there's an EXIT message received 
                           // System.out.println("eimai ekso apti while");
                            objoutstream.writeBoolean(true);
                            objoutstream.flush();
                            objoutstream.writeUTF("ADMIN LOGGED OUT");
                            objoutstream.flush();
                        } else if (strin.equals("USER")) {// if its a user then the server will grant the client user rights 
                            
                            objoutstream.writeBoolean(true);
                            objoutstream.flush();
                            do {
                                strin2 = objinstream.readUTF();//reads the next action 
                                System.out.println("SERVER1 -> " + strin2);
                                if (strin2.equals("SEARCH EVENT")) {// search event command 
                                    objoutstream.writeBoolean(true);//sends true back to the user  
                                    objoutstream.flush();
                                    objoutstream.writeUTF("WAITING FOR BOOKING INFO");// στέλνει ότι περιμένει αντικείμενο event
                                    objoutstream.flush();
                                    do {
                                        Lists.searchevent.clear();// clears the list 
                                        obj2 = objinstream.readObject();//reads the filters that the user chose  
                                        if (obj2 != null) {
                                            Functions.searchEvent(obj2); 
                                            if (Lists.searchevent.size() != 0) {//if there's a matching event in the list  
                                                objoutstream.writeUTF("FOUND");// returns that the event was found 
                                                objoutstream.flush();
                                                for (int i = 0; i < Lists.searchevent.size(); i++) {
                                                    System.out.println("Antikeimeno pou stelno : " + ((Event) Lists.searchevent.get(i)).toString());
                                                    objoutstream.writeObject((Event) Lists.searchevent.get(i));// sends the event 
                                                    objoutstream.flush();
                                                }
                                              
                                                objoutstream.writeObject(null);//Sends an empty object just to let the client know that were done and the client wont expect anything else 
                                                objoutstream.flush();
                                                Lists.searchevent.clear();//empties out the list  
                                            } else {
                                                objoutstream.writeUTF("NOT FOUND");//if the object wasnt found then it sends that it wasnt found  
                                                objoutstream.flush();
                                                objoutstream.writeObject(null);
                                                objoutstream.flush();
                                            }
                                        }
                                    } while (obj2 != null);
                                    objoutstream.writeUTF("WAITING FOR NEXT ACTION");//The server sends a "Waiting" message to the client 
                                    objoutstream.flush();
                                } else if (strin2.equals("ORDER")) {// order message 
                                    objoutstream.writeBoolean(true);
                                    objoutstream.flush();
                                    objoutstream.writeUTF("WAITING FOR BOOKING INFO");//waiting for booking object 
                                    objoutstream.flush();

                                    do {
                                        obj2 = objinstream.readObject();//empties the object 
                                        if (obj2 != null) {
                                            float result = Functions.makeABooking(obj2);//tries to make a booking 

                                            objoutstream.writeFloat(result);
                                            objoutstream.flush();
                                        }
                                    } while (obj2 != null);
                                    objoutstream.writeUTF("WAITING FOR NEXT ACTION");// The server sends a "waiting" message to the client 
                                    objoutstream.flush();
                                } else if (strin2.equals("CANCEL ORDER")) {//If it sends a cancellation message  
                                    objoutstream.writeBoolean(true);
                                    objoutstream.flush();
                                    objoutstream.writeUTF("WAITING FOR EVENT INFO");
                                    objoutstream.flush();
                                    do {
                                        obj2 = objinstream.readObject();//Reads the object that the client sends and save it in obj
                                        if (obj2 != null) {
                                            objoutstream.writeInt(Functions.cancelorder(obj2));
                                            objoutstream.flush();
                                        }
                                    } while (obj2 != null);
                                    objoutstream.writeUTF("WAITING FOR NEXT ACTION");//The server sends ' Waiting' to the client 
                                    objoutstream.flush();//clearing the memory 
                                }
                            } while (!strin2.equals("EXIT"));// while no "EXIT" message is received 
                            
                            objoutstream.writeBoolean(true);
                            objoutstream.flush();
                            objoutstream.writeUTF("USER LOGGED OUT");//Sends that the use was logged out 
                            objoutstream.flush();
                        }
                    } while (!strin.equals("END"));//End of Communication 
                    objoutstream.writeUTF("OK");//Message from the server to the client 
                    objoutstream.flush();
                } else { //There was no "Start" Message so the correct protocol wasnt executed 
                    objoutstream.writeUTF("Not welcomed..." + "\n");//The client was not accepted 
                    objoutstream.flush();//Clearing the memory 
                }
                
                objinstream.close();//Closing the streams and the socket 
                objoutstream.close();
                sock.close();
                //End of connection message 
                System.out.println("Connection Closing...");
            }
        } catch (Exception ex) {
            System.out.println("Error during I/O");
            ex.getMessage();
            ex.printStackTrace();
        }
    }
}
