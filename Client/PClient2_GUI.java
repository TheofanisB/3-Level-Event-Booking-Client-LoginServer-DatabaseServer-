
import javax.swing.*;
import java.awt.*;
import static java.awt.SystemColor.info;
import java.awt.event.*;
import java.io.*;
import static java.lang.String.valueOf;
import java.net.Socket;
import java.rmi.RemoteException;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.TitledBorder;

public class PClient2_GUI extends JFrame {

    protected static final String date_pattern = "(01|02|03|04|05|06|07|08|09|([12][0-9]|3[01]))-(01|02|03|04|05|06|07|08|09|10|11|12)-202[0-9]";// pattern to check proper date format

    protected static JMenuBar menuBars(JFrame frame, Operations look_up) {// UI bar that includes options , exit , about and details buttons
        JMenuBar mainBar;
        JMenu menu1, menu2;
        JMenuItem booking, exit;
        JMenuItem about;

        mainBar = new JMenuBar();

        menu1 = new JMenu("Options");
        booking = new JMenuItem("Book Tickets for an Event");
        exit = new JMenuItem("Exit");

        menu2 = new JMenu("About");
        about = new JMenuItem("Details");

        mainBar.add(menu1);
        mainBar.add(menu2);

        menu1.add(booking);
        menu1.add(exit);

        menu2.add(about);

        booking.addActionListener(new ActionListener() {// Clicking on the booking button
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showOptionDialog(null, "Would you like to book tickets for an Event ?", "Booking", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, Lists.choices, Lists.choices[1]);
                if (n == 0) {
                    frame.dispose();
                    firstframe(look_up);
                }
            }
        });

        exit.addActionListener(new ActionListener() { // Clicking on the exit button
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "EXIT", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, Lists.choices, Lists.choices[1]);
                if (n == 0) {
                    System.exit(0);
                }
            }
        });

        about.addActionListener(new ActionListener() {// app dev info
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "CINEMA BOOKING SERVICES \n Coded by:\n Theo B   \n", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return mainBar;
    }

    protected static void firstframe(Operations look_up) { //Πρώτο Μενού με Log in , Register ή Exit/ First Graphical Interface that lets the user Log in , Register or Exit the app
        JFrame firstframe = new JFrame();
        JPanel row1, row2;
        JButton login, register, exit;
        JLabel info;
        firstframe.setJMenuBar(menuBars(firstframe, look_up));
        firstframe.setLayout(new FlowLayout());
        firstframe.setTitle("Karlovasi Cinema Booking");

        row1 = new JPanel();
        info = new JLabel("Welcome!", JLabel.CENTER);
        info.setFont(new Font("Arial", Font.BOLD, 20));
        row1.add(info);

        FlowLayout flowlayout = new FlowLayout();
        row1.setLayout(flowlayout);

        row2 = new JPanel();
        login = new JButton("Log in");
        register = new JButton("Register");
        exit = new JButton("Exit");

        row2.add(login);
        row2.add(register);
        row2.add(exit);
        row2.setLayout(flowlayout);

        Container panel = firstframe.getContentPane();
        GridLayout layout = new GridLayout(2, 1);
        panel.setLayout(layout);

        firstframe.add(row1);
        firstframe.add(row2);

        firstframe.setContentPane(panel);
        firstframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstframe.pack();
        firstframe.setLocationRelativeTo(null);
        firstframe.setVisible(true);

        login.addActionListener(new ActionListener() { // login button
            public void actionPerformed(ActionEvent e) {
                firstframe.dispose();
                login(look_up);
            }
        });

        register.addActionListener(new ActionListener() { // register button
            public void actionPerformed(ActionEvent e) {
                firstframe.dispose();
                register_menu(look_up);
            }
        });

        exit.addActionListener(new ActionListener() { // exit button that asks confirmation
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showOptionDialog(null, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, Lists.choices, Lists.choices[1]);
                if (n == 0) {
                    System.exit(0);
                }
            }
        });

    }

    protected static void login(Operations look_up) {// Log in Window 
        JFrame login = new JFrame();
        JPanel row1, row2, row3, row4, row5;
        JLabel username, password;
        JTextField username1;
        JPasswordField password1;
        JButton connect, cancel;

        login.setDefaultCloseOperation(EXIT_ON_CLOSE);

        login.setTitle("Program");

        login.setLayout(new FlowLayout());

        login.setJMenuBar(menuBars(login, look_up));

        row1 = new JPanel();
        username = new JLabel("Username: ");

        row2 = new JPanel();
        username1 = new JTextField(20);

        row3 = new JPanel();
        password = new JLabel("Password: ");

        row4 = new JPanel();
        password1 = new JPasswordField(20);

        row5 = new JPanel();
        connect = new JButton("Login");
        cancel = new JButton("Cancel");

        Container panel = login.getContentPane();
        GridLayout layout = new GridLayout(5, 1);
        panel.setLayout(layout);
        FlowLayout flowlayout = new FlowLayout();

        row1.setLayout(flowlayout);
        row1.add(username);

        row2.setLayout(flowlayout);
        row2.add(username1);

        row3.setLayout(flowlayout);
        row3.add(password);

        row4.setLayout(flowlayout);
        row4.add(password1);

        row5.setLayout(flowlayout);
        row5.add(connect);
        row5.add(cancel);
        login.add(row1);
        login.add(row2);
        login.add(row3);
        login.add(row4);
        login.add(row5);

        connect.addActionListener(new ActionListener() {// Connect button that lets the user have access to the menus if his credentials are matching a user in the datebase
            public void actionPerformed(ActionEvent e) {
                if (username1.getText().equals("")) {// blank username
                    JOptionPane.showMessageDialog(login, "Please, Fill the gaps!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else if (username1.getText() != null) {
                    if (new String(password1.getPassword()).equals("")) { // blank password
                        JOptionPane.showMessageDialog(login, "Please, Fill the gaps!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    } else {
                        User user = null;
                        try {
                            user = look_up.login(username1.getText(), new String(password1.getPassword()));//Looks up the user in the User Database of the 1st Server
                            if (user != null) {//If the user exists in the database then user will have either an "ADMIN" or a "USER" value on his getType() function
                                if (user.getType().equals("ADMIN")) {// if the user account is an Admin
                                    login.dispose();
                                    admin_menu(look_up);// prompts to the admin menu
                                } else if (user.getType().equals("USER")) { // If the user is a normal user
                                    login.dispose();
                                    look_up.readboolean();
                                    user_menu(look_up, user);//prompts to the user menu
                                }
                            } else {///if the user didnt exist in the database
                                JOptionPane.showMessageDialog(login, "Check your credentials!!!", "Error Message", JOptionPane.ERROR_MESSAGE);

                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        });

        cancel.addActionListener(new ActionListener() {//exiting button
            public void actionPerformed(ActionEvent e) {
                login.dispose();
                firstframe(look_up);
            }
        });

        login.setContentPane(panel);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(400, 250);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }

    protected static void admin_menu(Operations look_up) {// Admin's menu

        JFrame admin = new JFrame();
        JLabel tab1Message;
        JLabel tab2Message;
        JTabbedPane choices;
        JPanel p1;
        JPanel p2;
        JButton create_event;
        JButton cancel_event;
        JButton logout;

        admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin.setVisible(true);
        admin.getContentPane().setLayout(new FlowLayout());

        choices = new JTabbedPane();
        admin.getContentPane().add(choices);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2));
        p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 2));

        tab1Message = new JLabel("Tickets", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 20));
        p1.add(tab1Message);

        create_event = new JButton("Make a new Event");
        p1.add(create_event);
        cancel_event = new JButton("Cancel An Event");
        p1.add(cancel_event);

        tab2Message = new JLabel("Account Settings:", JLabel.CENTER);
        tab2Message.setFont(new Font("Arial", Font.BOLD, 20));
        p2.add(tab2Message);

        logout = new JButton("Log out!");
        p2.add(logout);

        create_event.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("ADD EVENT")) {// Sends a "ADD EVENT" string to the 2nd server  and if the 2nd server received the message then 
                        admin.dispose();//closes the admin window
                        look_up.readfromserver();//reads message from the 2nd server
                        add_event(look_up);// prompts to the "Add an Event" Window
                    } else {// in case we get a message we didnt expect
                        JOptionPane.showMessageDialog(admin, "What would you like to do?", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cancel_event.addActionListener(new ActionListener() {// cancel/delete an event button
            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("DELETE")) {// sends DELETE to the 2nd server
                        admin.dispose();
                        look_up.readfromserver();//expects confirmation
                        delete_event(look_up);// opens up the delete event menu
                    } else {
                        JOptionPane.showMessageDialog(admin, "What would you like to do?", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        logout.addActionListener(new ActionListener() {// log out button
            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("EXIT")) {//sends exiting message to the 2nd server
                        look_up.readfromserver();//Περιμένει επιβεβαίωση από το server// expects verification reply from server
                        admin.dispose();//closes down the admin menu
                        login(look_up);// goes back to the login screen
                    } else {
                        JOptionPane.showMessageDialog(admin, "What would you like to do?", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        choices.addTab("Ticket Menu", null, p1, "Ticket Related Options");
        choices.addTab("Admin Menu", null, p2, "Account Related Options");
        admin.setSize(400, 200);
        admin.setLocationRelativeTo(null);

    }

    protected static void add_event(Operations look_up) {// Graphical Menu that allows the Admin to add an event
        JFrame frame = new JFrame();
        JLabel tab1Message;

        JPanel p1;
        JLabel title_of_event_l;
        JLabel genre_label;
        JLabel date_l;
        JLabel av_seats_label;
        JLabel time_label;
        JLabel cost_label;
        JTextField title1;
        JTextField date;
        JTextField av_seats;
        JTextField cost;
        JButton add_button;
        JButton cancel_button;
        ButtonGroup sgroup;////Button Group responsible for the type of the event (ex. Horror , Comedy,etc.)

        JRadioButton horror, action, comedy, other;//Event type buttons 
        String[] hours = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));

        tab1Message = new JLabel("Add an Event", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 20));
        p1.add(tab1Message);
        title1 = new JTextField();
        date = new JTextField();
        av_seats = new JTextField();
        cost = new JTextField();

        horror = new JRadioButton("Horror");
        horror.setMnemonic(KeyEvent.VK_B);
        horror.setActionCommand("Horror");
        horror.setSelected(true);

        action = new JRadioButton("Action");
        action.setMnemonic(KeyEvent.VK_C);
        action.setActionCommand("Action");

        comedy = new JRadioButton("Comedy");
        comedy.setMnemonic(KeyEvent.VK_D);
        comedy.setActionCommand("Comedy");

        other = new JRadioButton("Other");
        other.setMnemonic(KeyEvent.VK_E);
        other.setActionCommand("Other");

        sgroup = new ButtonGroup();
        sgroup.add(horror);
        sgroup.add(action);
        sgroup.add(comedy);
        sgroup.add(other);

        JComboBox time = new JComboBox(hours);// Combo box that makes a scroll down selection menu
        time.setEditable(false);

        title_of_event_l = new JLabel("Title:");
        date_l = new JLabel("Date (dd-mm-yyyy):");
        time_label = new JLabel("Time:");
        genre_label = new JLabel("Genre:");
        av_seats_label = new JLabel("Available Seats:");
        cost_label = new JLabel("Cost per seat:");

        p1.add(title_of_event_l);
        p1.add(title1);
        p1.add(genre_label);
        p1.add(horror);
        p1.add(action);
        p1.add(comedy);
        p1.add(other);
        p1.add(date_l);
        p1.add(date);
        p1.add(time_label);
        p1.add(time);
        p1.add(av_seats_label);
        p1.add(av_seats);
        p1.add(cost_label);
        p1.add(cost);

        add_button = new JButton("Add the Event");
        cancel_button = new JButton("Cancel");
        p1.add(add_button);
        p1.add(cancel_button);

        frame.add(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        add_button.addActionListener(new ActionListener() {// Button that adds a new event
            public void actionPerformed(ActionEvent e) {

                if (date.getText().matches(date_pattern))// if the date is in the correct format 
                {
                    String clock = hours[time.getSelectedIndex()];//sets clock to what the user picked on the hour scrolldown menu
                    String genre = "";
                    if (horror.isSelected()) {
                        genre = "Horror";
                    } else if (action.isSelected()) {
                        genre = "Action";
                    } else if (comedy.isSelected()) {
                        genre = "Comedy";
                    } else if (other.isSelected()) {
                        genre = "Other";
                    }
                    //Αν υπάρχει κενό
                    if (title1.getText().equals("") || date.getText().equals("") || genre.equals("") || av_seats.getText().equals("") || cost.getText().equals("")) {// if there's a blank
                        JOptionPane.showMessageDialog(frame, "Please, Fill the blanks!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            //Event(String title, String genre, String event_date, String time, int available_seats, int cost_per_seat)
                            Event event1 = new Event(title1.getText(), genre, date.getText(), clock, Integer.parseInt(av_seats.getText()), Integer.parseInt(cost.getText()));
                            
                            if (look_up.addevent(event1)) {// Στέλνει την εκδήλωση sends the event to the 2nd server and if it was succesfully added then it 
                                look_up.readfromserver();//reads answer
                                JOptionPane.showMessageDialog(null, "The event has been successfully added!!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                title1.setText("");
                                date.setText("");
                                av_seats.setText("");
                                cost.setText("");
                            } else {// if it the request was accepted then it sends a positive reply
                                look_up.readfromserver();
                                JOptionPane.showMessageDialog(frame, "The event was NOT added!", "Error Message", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {// if the date is wrong 

                    JOptionPane.showMessageDialog(frame, "Wrong Date Format!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancel_button.addActionListener(new ActionListener() {// closes the window and goes back to the admin menu 
            public void actionPerformed(ActionEvent e) {
                try {
                    look_up.writeobj(null);
                    frame.dispose();
                    look_up.readfromserver();
                    admin_menu(look_up);
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        //popup window me apantisi
        // this.dispose();
        //Below we add Actionlisteners to our buttons
    }

    protected static void delete_event(Operations look_up) {// window that lets the admin delete an event  
        JFrame frame = new JFrame();
        JLabel tab1Message;
        JTabbedPane choices;
        JPanel p1;
        JLabel title_of_event_l;
        JLabel date_l, time_l;
        JTextField title1;
        JTextField date1;
        JButton delete_button, cancel_button;
        String[] hours = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        JComboBox time = new JComboBox(hours);//dropdown combobox for time 
        time.setEditable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(new FlowLayout());

        choices = new JTabbedPane();
        frame.getContentPane().add(choices);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(12, 1));
        tab1Message = new JLabel("Deleting an Event", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 20));
        p1.add(tab1Message);
        title1 = new JTextField();
        date1 = new JTextField();
        time_l = new JLabel("Time:");
        title_of_event_l = new JLabel("Title:");
        date_l = new JLabel("Date (dd-mm-yyyy):");

        p1.add(title_of_event_l);
        p1.add(title1);
        p1.add(date_l);
        p1.add(date1);
        p1.add(time_l);
        p1.add(time);

        delete_button = new JButton("Delete the Event");
        p1.add(delete_button);
        cancel_button = new JButton("Cancel");
        p1.add(cancel_button);
        choices.addTab("Delete An Event", null, p1, "");
        frame.add(choices);

        delete_button.addActionListener(new ActionListener() {// event deletion button 
            public void actionPerformed(ActionEvent e) {
                if (title1.getText().equals("") || date1.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "Please, Fill the blanks!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (date1.getText().matches(date_pattern)) {
                        String clock = hours[time.getSelectedIndex()];
                        //Event(String title, String genre, String event_date, String time, int available_seats, int cost_per_seat)
                        Event event1 = new Event(title1.getText(), "", date1.getText(), clock, 0, 0);
                        try {
                            if (look_up.deleteevent(event1)) {//Sends an object  to be deleted if it is found 
                                look_up.readfromserver();
                                JOptionPane.showMessageDialog(null, "The event has been successfully deleted!!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                title1.setText("");
                                date1.setText("");
                            } else { //else it shows a rejection message 
                                look_up.readfromserver();
                                JOptionPane.showMessageDialog(null, "The event was not found", "Success", JOptionPane.INFORMATION_MESSAGE);
                                title1.setText("");
                                date1.setText("");
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {

                        JOptionPane.showMessageDialog(frame, "Wrong Date Format!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        cancel_button.addActionListener(new ActionListener() {// / back 
            public void actionPerformed(ActionEvent e) {
                try {
                    look_up.writeobj(null);
                    frame.dispose();
                    look_up.readfromserver();
                    admin_menu(look_up);
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        frame.setSize(280, 400);
        frame.setLocationRelativeTo(null);

    }

    protected static void user_menu(Operations look_up, User current_user) {//user menu 
        JFrame frame = new JFrame();
        JLabel tab1Message;
        JLabel tab2Message;
        JTabbedPane choices;
        JPanel p1;
        JPanel p2;
        JButton search_event;
        JButton order_tickets;
        JButton delete_account;
        JButton cancel_order;
        JButton logout;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(new FlowLayout());

        choices = new JTabbedPane();
        frame.getContentPane().add(choices);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(4, 1));
        p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 2));
        tab1Message = new JLabel("Tickets", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 20));
        p1.add(tab1Message);

        search_event = new JButton("Search Event");
        p1.add(search_event);
        order_tickets = new JButton("Order Tickets");
        p1.add(order_tickets);
        cancel_order = new JButton("Cancel Your Bookings");
        p1.add(cancel_order);

        tab2Message = new JLabel("Account Settings:", JLabel.CENTER);
        tab2Message.setFont(new Font("Arial", Font.BOLD, 20));
        p2.add(tab2Message);

        delete_account = new JButton("Delete Your Account");
        p2.add(delete_account);
        logout = new JButton("Log out!");
        p2.add(logout);

        choices.addTab("Ticket Menu", null, p1, "Ticket Related Options");
        choices.addTab("User Menu", null, p2, "Account Related Options");

        logout.addActionListener(new ActionListener() {//log out button that returns the user to the log in menu 
            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("EXIT")) {
                        look_up.readfromserver();
                        frame.dispose();
                        login(look_up);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Exiting Error", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        search_event.addActionListener(new ActionListener() {// search button 

            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("SEARCH EVENT")) {// sends "SEARCH EVENT"  if the request was accepted  and then it proceeds to the search menu   
                        look_up.readfromserver();
                        frame.dispose();
                        booking_menu(look_up, current_user);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        order_tickets.addActionListener(new ActionListener() {//buttons that opens the order menu   

            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("ORDER")) {
                        look_up.readfromserver();
                        frame.dispose();
                        order_tickets(look_up, current_user);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        cancel_order.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (look_up.user_string("CANCEL ORDER")) {
                        look_up.readfromserver();
                        frame.dispose();
                        cancel_order(look_up, current_user);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        delete_account.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showOptionDialog(frame, "Are you sure you want to delete your account?", "DELETE YOUR ACCOUNT", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, Lists.choices, Lists.choices[1]);
                try {
                    if (n == 0) {
                        if (look_up.deleteuser(current_user)) {
                            if (look_up.user_string("EXIT")) {
                                look_up.readfromserver();
                                frame.dispose();
                                login(look_up);
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        frame.setSize(
                400, 400);
        frame.setLocationRelativeTo(
                null);
    }

    protected static void order_tickets(Operations look_up, User current_user) {
        JFrame frame = new JFrame();
        JLabel tab1Message;
        JTabbedPane choices;
        JPanel p1;
        JLabel title_of_event_l;
        JLabel date_l;
        JLabel amount_l;
        JLabel time_label;

        JTextField title1;
        JTextField date1;
        JTextField amount1;
        String[] hours = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        JComboBox patternList = new JComboBox(hours);
        patternList.setEditable(false);

        JButton order_button, cancel;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(new FlowLayout());

        choices = new JTabbedPane();
        frame.getContentPane().add(choices);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(12, 1));

        tab1Message = new JLabel("Order Your Tickets", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 20));
        p1.add(tab1Message);
        title1 = new JTextField();
        date1 = new JTextField();
        amount1 = new JTextField();

        title_of_event_l = new JLabel("Title:");
        date_l = new JLabel("Date (dd-mm-yyyy):");
        amount_l = new JLabel("Amount:");
        time_label = new JLabel("Time :");

        p1.add(title_of_event_l);
        p1.add(title1);
        p1.add(date_l);
        p1.add(date1);
        p1.add(time_label);
        p1.add(patternList);
        p1.add(amount_l);
        p1.add(amount1);

        order_button = new JButton("Order");
        p1.add(order_button);
        cancel = new JButton("Cancel");
        p1.add(cancel);

        choices.addTab("Order Tickets", null, p1, "Order Your Tickets");

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        //order_button
        order_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (title1.getText().equals("") || date1.getText().equals("") || amount1.getText().equals("")) {// if there's blanks 

                    JOptionPane.showMessageDialog(frame, "Please, Fill the blanks!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {

                    if (date1.getText().matches(date_pattern)) { //  if the date pattern is correct 
                        String clock = hours[patternList.getSelectedIndex()];
                        Booking booking = new Booking(title1.getText(), date1.getText(), clock, Integer.parseInt(amount1.getText()), 0, current_user.getUsername());
                        try {
                            look_up.writeobj(booking);//sends the booking desired 
                            float result = look_up.resultorder(); // sends the result of the request 
                            if (result == -1) { //if there's not enough seats 
                                JOptionPane.showMessageDialog(frame, "Insufficient Amount of seats!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                            } else if (result == -2) {// if the event wasnt found 
                                JOptionPane.showMessageDialog(frame, "No event with such info was found!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                            } else {
                                booking.setCost(result);
                                JOptionPane.showMessageDialog(null, "Result" + "\n Username: " + booking.getUsername() + "\nTitle: " + booking.getTitle() + "\nDate: " + booking.getEvent_date() + "\nTime: " + booking.getTime() + "\nCost: " + booking.getCost(),
                                        "Result", JOptionPane.INFORMATION_MESSAGE);
                            }//proceeds to close the server connection and returns to the user menu  
                            look_up.writeobj(null);
                            look_up.readfromserver();
                            frame.dispose();
                            user_menu(look_up, current_user);
                        } catch (RemoteException ex) {
                            Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {

                        JOptionPane.showMessageDialog(frame, "Wrong Date Format!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //Κουμπι επιστροφής στο μενού επιλογών του χρήστη Returning back to the user options menu 
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.dispose();
                    look_up.writeobj(null);
                    look_up.readfromserver();
                    user_menu(look_up, current_user);
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //cancel
    }

    protected static void cancel_order(Operations look_up, User current_user) { //  graphs for canceling an event
        JFrame frame = new JFrame();
        JLabel tab1Message;

        JTabbedPane choices;
        JPanel p1;
        JLabel title_of_event_l;
        JLabel date_l;
        JLabel time_label;
        JTextField title1;
        JTextField date1;
        JButton cancel_order, back;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(new FlowLayout());

        choices = new JTabbedPane();
        frame.getContentPane().add(choices);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(12, 1));

        tab1Message = new JLabel("Cancel Your Order", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 20));
        p1.add(tab1Message);
        title1 = new JTextField();
        date1 = new JTextField();
        time_label = new JLabel("Time:");
        String[] hours = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
        JComboBox patternList = new JComboBox(hours);
        patternList.setEditable(false);

        title_of_event_l = new JLabel("Title:");
        date_l = new JLabel("Date (dd-mm-yyyy):");
        cancel_order = new JButton("Cancel Order");
        back = new JButton("Back");
        p1.add(title_of_event_l);
        p1.add(title1);
        p1.add(date_l);
        p1.add(date1);
        p1.add(time_label);
        p1.add(patternList);
        p1.add(cancel_order);
        p1.add(back);

        choices.addTab("Cancel your Order", null, p1, "");

        cancel_order.addActionListener(new ActionListener() {//Cancel an event button 
            public void actionPerformed(ActionEvent e) {
                if (title1.getText().equals("") || date1.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "Please, Fill the blanks!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (date1.getText().matches(date_pattern)) {

                        try {
                            String clock = hours[patternList.getSelectedIndex()];
                            Booking toCancel = new Booking(title1.getText(), date1.getText(), clock, 0, 0, current_user.getUsername());
                            look_up.writeobj(toCancel);

                            float cancel = look_up.resultorder();//receiving result of booking an order 
                            if (cancel == 0) {// if the event is on the same day 
                                JOptionPane.showMessageDialog(frame, "Not able to cancel the Booking!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                            } else {// succesful cancellation 
                                JOptionPane.showMessageDialog(null, "The Booking was succesfully cancelled",
                                        "Result", JOptionPane.INFORMATION_MESSAGE);
                            }
                            look_up.writeobj(null);
                            look_up.readfromserver();
                            frame.dispose();
                            user_menu(look_up, current_user);
                        } catch (RemoteException ex) {
                            Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {// λάθος ημερομηνία

                        JOptionPane.showMessageDialog(frame, "Wrong Date Format!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        back.addActionListener(new ActionListener() {// Button that goes back  to the previous window 
            public void actionPerformed(ActionEvent e) {
                try {
                    Lists.list.clear();
                    frame.dispose();
                    look_up.writeobj(null);
                    look_up.readfromserver();
                    user_menu(look_up, current_user);
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

    }

    protected static void register_menu(Operations look_up) { //  Registration Menu Graphs 
        JFrame frame = new JFrame();
        JLabel tab1Message;
        JTabbedPane choices;
        JPanel p1;
        JLabel username_l;
        JLabel password_l;
        JLabel email_l;
        JLabel name_l;
        JTextField username;
        JTextField password;
        JTextField email;
        JTextField name;
        JButton create_button;
        JButton cancel;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(new FlowLayout());

        choices = new JTabbedPane();
        frame.getContentPane().add(choices);
        p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
        tab1Message = new JLabel("Please , Insert Your Info", JLabel.CENTER);
        tab1Message.setFont(new Font("Arial", Font.BOLD, 18));
        p1.add(tab1Message);
        username = new JTextField();
        password = new JTextField();
        email = new JTextField();
        name = new JTextField();
        username_l = new JLabel("Username:");
        password_l = new JLabel("Password:");
        email_l = new JLabel("Email:");
        name_l = new JLabel("Name:");
        p1.add(username_l);
        p1.add(username);
        p1.add(password_l);
        p1.add(password);
        p1.add(email_l);
        p1.add(email);
        p1.add(name_l);
        p1.add(name);

        create_button = new JButton("Create");
        cancel = new JButton("Cancel");
        p1.add(create_button);
        p1.add(cancel);

        choices.addTab("Register", null, p1, "Register as a new User");

        create_button.addActionListener(new ActionListener() {// Button that takes inputs from the client and checks if there's such a user in the db , otherwise it creates a new user  
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((username.getText()).equals("") || (password.getText()).equals("") || (email.getText()).equals("") || (name.getText()).equals("")) {
                        JOptionPane.showMessageDialog(frame, "Please, Fill the gaps!!!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    } else {//loads up the user 
                        User user1 = new User(username.getText(), password.getText(), "USER", email.getText(), name.getText());
                        if (look_up.adduser(user1)) {//checks if the user exists in the db 
                            if (look_up.user_string("USER")) {//opens up the user menu 
                                frame.dispose();
                                user_menu(look_up, user1);
                            } else {//in case of any issue 
                                JOptionPane.showMessageDialog(frame, "Wrong Credentials!", "Error Message", JOptionPane.ERROR_MESSAGE);

                            }
                        } else {// if the user already exists 
                            JOptionPane.showMessageDialog(frame, "Username Already Exists!", "Error Message", JOptionPane.ERROR_MESSAGE);

                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Button to exit the process 
        cancel.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                firstframe(look_up);
            }
        });

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
    }

    protected static void booking_menu(Operations look_up, User current_user) {//Booking Menu 
        String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] years = {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        String[] hours = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

        JFrame frame = new JFrame();
        JPanel row1, row2, row3, row4, row6, row7;
        JLabel info, title, date, time_label;
        JTextField title1, date1;
        JButton search, cancel;

        JComboBox dayslist = new JComboBox(days);
        JComboBox monthlist = new JComboBox(months);
        JComboBox yearlist = new JComboBox(years);
        JComboBox time = new JComboBox(hours);

        time_label = new JLabel("Time:");

        ButtonGroup sgroup; 

        JRadioButton horror, action, comedy, other;// check buttons

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.setTitle("Search");
        frame.setLayout(new FlowLayout());

        row1 = new JPanel();
        info = new JLabel("Pick your search criteria");

        row2 = new JPanel();
        horror = new JRadioButton("Horror");
        horror.setMnemonic(KeyEvent.VK_B);
        horror.setActionCommand("Horror");
        horror.setSelected(true);

        action = new JRadioButton("Action");
        action.setMnemonic(KeyEvent.VK_C);
        action.setActionCommand("Action");

        comedy = new JRadioButton("Comedy");
        comedy.setMnemonic(KeyEvent.VK_D);
        comedy.setActionCommand("Comedy");

        other = new JRadioButton("Other");
        other.setMnemonic(KeyEvent.VK_E);
        other.setActionCommand("Other");

        sgroup = new ButtonGroup();//button group for each event category 
        sgroup.add(horror);
        sgroup.add(action);
        sgroup.add(comedy);
        sgroup.add(other);

        row3 = new JPanel();
        title = new JLabel("Title: ");
        title1 = new JTextField(20);

        row4 = new JPanel();
        row6 = new JPanel();
        date = new JLabel("Event Date (dd-mm-yyyy):");
        date1 = new JTextField(20);

        row7 = new JPanel();
        search = new JButton("Search Event");
        cancel = new JButton("Cancel");

        Container panel = frame.getContentPane();
        GridLayout layout = new GridLayout(7, 2);
        panel.setLayout(layout);
        FlowLayout flowlayout = new FlowLayout();

        row1.setLayout(flowlayout);
        row1.add(info);

        row2.setLayout(flowlayout);
        row2.add(horror);
        row2.add(action);
        row2.add(comedy);
        row2.add(other);

        row3.setLayout(flowlayout);
        row3.add(title);
        row3.add(title1);

        row4.setLayout(flowlayout);
        row4.add(time_label);
        row4.add(time);

        row6.setLayout(flowlayout);
        row6.add(date);
        row6.add(dayslist);
        row6.add(monthlist);
        row6.add(yearlist);

        row7.setLayout(flowlayout);
        row7.add(search);
        row7.add(cancel);

        frame.add(row1);
        frame.add(row2);
        frame.add(row3);
        frame.add(row6);
        frame.add(row4);
        frame.add(row7);

        search.addActionListener(new ActionListener() {//search button 
            public void actionPerformed(ActionEvent e) {
                //Converts the button into date Strings 
                String fullDate = days[dayslist.getSelectedIndex()] + "-" + months[monthlist.getSelectedIndex()] + "-" + years[yearlist.getSelectedIndex()];
                System.out.println("fulldate: " + fullDate);
                if (horror.isSelected()) {// horror type event 
                    //String title, String genre, String event_date, int available_seats, int cost_per_seat
                    String clock = hours[time.getSelectedIndex()];
                    Event toSearch = new Event(title1.getText(), "Horror", fullDate, clock, 0, 0);
                    try {
                        look_up.writeobj(toSearch);
                        look_up.readfromserver();
                        boolean check = look_up.readobj();
                        if (check) {
                            Lists.list = look_up.searchlist();

                            for (int i = 0; i < Lists.list.size(); i++) {
                                System.out.println("The Search button : \n" + (Lists.list.get(i)).toString());
                            }
                            resultSearch(frame, look_up);//Puts the result in a window 
                        } else {// if there's no results 
                            JOptionPane.showMessageDialog(null, "NOT FOUND!", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
                            Lists.list.clear();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (action.isSelected()) {// if its an action type 
                    //String title, String genre, String event_date, int available_seats, int cost_per_seat
                    String clock = hours[time.getSelectedIndex()];
                    Event toSearch = new Event(title1.getText(), "Action", fullDate, clock, 0, 0);
                    try {
                        look_up.writeobj(toSearch);
                        look_up.readfromserver();
                        boolean check = look_up.readobj();
                        if (check) {
                            Lists.list = look_up.searchlist();

                            for (int i = 0; i < Lists.list.size(); i++) {
                                System.out.println("The Search button: \n" + (Lists.list.get(i)).toString());
                            }
                            resultSearch(frame, look_up);
                        } else {
                            JOptionPane.showMessageDialog(null, "NOT FOUND!", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
                            Lists.list.clear();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (comedy.isSelected()) {// if its a comedy type of event 
                    //String title, String genre, String event_date, int available_seats, int cost_per_seat
                    String clock = hours[time.getSelectedIndex()];
                    Event toSearch = new Event(title1.getText(), "Comedy", fullDate, clock, 0, 0);
                    try {
                        look_up.writeobj(toSearch);
                        look_up.readfromserver();
                        boolean check = look_up.readobj();
                        if (check) {
                            Lists.list = look_up.searchlist();

                            for (int i = 0; i < Lists.list.size(); i++) {
                                System.out.println("The Search button: \n" + (Lists.list.get(i)).toString());
                            }
                            resultSearch(frame, look_up);
                        } else {
                            JOptionPane.showMessageDialog(null, "NOT FOUND!", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
                            Lists.list.clear();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (other.isSelected()) {//if it belongs to any other type of category  
                    //String title, String genre, String event_date, int available_seats, int cost_per_seat
                    String clock = hours[time.getSelectedIndex()];
                    Event toSearch = new Event(title1.getText(), "Other", fullDate, clock, 0, 0);
                    try {
                        look_up.writeobj(toSearch);
                        look_up.readfromserver();
                        boolean check = look_up.readobj();
                        if (check) {
                            Lists.list = look_up.searchlist();

                            for (int i = 0; i < Lists.list.size(); i++) {
                                System.out.println("The Search button: \n" + (Lists.list.get(i)).toString());
                            }
                            resultSearch(frame, look_up);
                        } else {
                            JOptionPane.showMessageDialog(null, "NOT FOUND!", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
                            Lists.list.clear();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        
        cancel.addActionListener(new ActionListener() {//Exit button 
            public void actionPerformed(ActionEvent e) {
                try {
                    Lists.list.clear();// empties out the list 
                    frame.dispose();
                    look_up.writeobj(null);
                    look_up.readfromserver(); //ενημερώνει τον σερβερ ότι πάει μια θέση πίσω  
                    user_menu(look_up, current_user); //επιστρέφει στο μενου χρήστη 
                  
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } 

    // Method that shows the results that the user searched for 
    protected static void resultSearch(JFrame parentframe, Operations look_up) {
        parentframe.setEnabled(false);

        JFrame search = new JFrame();
        JPanel panel = new JPanel();
        JButton ok;
        String[][] rec = new String[Lists.list.size()][6];
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Results", TitledBorder.CENTER, TitledBorder.TOP));
       //filling the panel with event info 
        for (int i = 0; i < Lists.list.size(); i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    rec[i][j] = ((Event) Lists.list.get(i)).getTitle();
                } else if (j == 1) {
                    rec[i][j] = ((Event) Lists.list.get(i)).getGenre();
                } else if (j == 2) {
                    rec[i][j] = ((Event) Lists.list.get(i)).getEvent_date();
                } else if (j == 3) {
                    rec[i][j] = ((Event) Lists.list.get(i)).getTime();
                } else if (j == 4) {
                    rec[i][j] = valueOf(((Event) Lists.list.get(i)).getAvailable_seats());// int to String
                } else if (j == 5) {
                    rec[i][j] = valueOf(((Event) Lists.list.get(i)).getCost_per_seat());
                }
            }
        }
        String[] header = {"Title", "Genre", "Event Date", "Time", "Available Seats", "Cost Per Seat"};
        JTable table = new JTable(rec, header);
        table.setEnabled(false);//doesnt allow the table to be edited 
        panel.add(new JScrollPane(table));
        search.add(panel);

        ok = new JButton("OK");
        panel.add(ok);
        search.add(panel);

        
        ok.addActionListener(new ActionListener() {// Button that clears the list and goes back to the menu 
            public void actionPerformed(ActionEvent e) {
                Lists.list.clear();
                try {
                    look_up.clearlist();
                } catch (RemoteException ex) {
                    Logger.getLogger(PClient2_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                search.dispose();
                parentframe.setEnabled(true);
                parentframe.setVisible(true);
            }
        });

        search.setSize(550, 400);
        search.setVisible(true);
        search.setLocationRelativeTo(null);
        search.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
