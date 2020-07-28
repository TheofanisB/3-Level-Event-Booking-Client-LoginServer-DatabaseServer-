
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Functions {

    private static int counter = 0;// giving an id to the clients that make bookings

    protected static boolean addevent(Object obj) {//adds an event 
            synchronized((Event)obj){//synchronizing just so we dont get multiple changes at events at the same time 
        //  Event(String title, String genre, String event_date, String time, int available_seats, int cost_per_seat)
        Event newevent = new Event(((Event) obj).getTitle(), ((Event) obj).getGenre(), ((Event) obj).getEvent_date(), ((Event) obj).getTime(), ((Event) obj).getAvailable_seats(), ((Event) obj).getCost_per_seat());
        Lists.event_list.add(newevent);
        return true;
            }
    }

    protected static boolean deleteEvent(Object obj) {// Deleting an event 
        for (int i = 0; i < Lists.event_list.size(); i++) {//Checks if it the event has a match in the list and then deleted it 
            if ((((Event) obj).getTitle().equals(Lists.event_list.get(i).getTitle())) && (((Event) obj).getEvent_date().equals(Lists.event_list.get(i).getEvent_date()))) {
                Lists.event_list.remove(Lists.event_list.get(i));
                return true;//  returns if the object was deleted or not 
            }
        }
        return false;
    }

    protected static void searchEvent(Object obj) {//search an event 
         synchronized((Event)obj){// 
        for (int i = 0; i < Lists.event_list.size(); i++) {
            if ((((Event) obj).getGenre().equals(Lists.event_list.get(i).getGenre()))//if their info match 
                    && (((Event) obj).getEvent_date().equals(Lists.event_list.get(i).getEvent_date()))
                    && (((Event) obj).getTime().equals(Lists.event_list.get(i).getTime()))) {
                if ((((Event) obj).getTitle().equals(""))) {//if there's no title  
                    Lists.searchevent.add(Lists.event_list.get(i));
                } else if ((Lists.event_list.get(i).getTitle()).contains(((Event) obj).getTitle())) {// if there's a title 
                    Lists.searchevent.add(Lists.event_list.get(i));
                }
            }
        }
         }
    }

    protected static float makeABooking(Object obj) throws ParseException {
        int discount = 0;//discount status 
 synchronized((Booking)obj){
        for (int i = 0; i < Lists.event_list.size(); i++) {
            if ((((Booking) obj).getTitle().equals(Lists.event_list.get(i).getTitle())) && (((Booking) obj).getEvent_date().equals(Lists.event_list.get(i).getEvent_date()))) {
                if ((((Booking) obj).getBooked_seats() <= Lists.event_list.get(i).getAvailable_seats())) {
                    String[] todaydate = ((Booking) obj).getEvent_date().split("-");

                    String sDate1 = Lists.event_list.get(i).getEvent_date();
                    Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);  // format declaration 
                    System.out.println(sDate1 + "\t" + date1);  //printing the  full date
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date1); // convertion se calendar
                    // System.out.println(calendar.get(Calendar.DAY_OF_WEEK)); // printing day of the week ( sunday =1 )
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                   if (Lists.event_list.get(i).getAvailable_seats() <= 10 && (day != 7 && day != 1)) {//less than 20 seats and day is saturday or sunday 
                        discount = 1;
                        System.out.println("Printing!!!!");
                    }
                    System.out.println("theseis PRIN : " + Lists.event_list.get(i).getAvailable_seats());
                    Lists.event_list.get(i).setAvailable_seats(Lists.event_list.get(i).getAvailable_seats() - ((Booking) obj).getBooked_seats());
                    System.out.println("theseis META : " + Lists.event_list.get(i).getAvailable_seats());
                    if (discount == 0) {//if there's no discount 
                        ((Booking) obj).setCost(((Booking) obj).getBooked_seats() * Lists.event_list.get(i).getCost_per_seat());
                    } else {// if there's discount
                        ((Booking) obj).setCost((float) (((Booking) obj).getBooked_seats() * (Lists.event_list.get(i).getCost_per_seat() * (0.6))));
                    }
                    Lists.bookings.add((Booking) obj);
                    return ((Booking) obj).getCost();// returns the cost  
                } else {// if there's no seats 
                    return -1;
                }
            }
        }
        return -2;// if the event doesnt exist 
 }
    }

    protected static int cancelorder(Object obj) throws ParseException {// method that cancels the order
        int cancel = 0;// counter of deletions 
        int seats = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");//date formats 
        String dateString = format.format(new Date());// Convert to string 
        System.out.println("Imerominia :" + dateString);
        synchronized((Booking)obj){
        for (int i = 0; i < Lists.bookings.size(); i++) {
            if (Lists.bookings.get(i).getUsername().equals(((Booking) obj).getUsername())
                    && Lists.bookings.get(i).getTitle().equals(((Booking) obj).getTitle())
                    && Lists.bookings.get(i).getTime().equals(((Booking) obj).getTime())) {
                if (Lists.bookings.get(i).getEvent_date().equals(((Booking) obj).getEvent_date())) {
                    if (Lists.bookings.get(i).getEvent_date().equals(dateString)) {
                        return cancel; //returns the counter of cancellations 
                    } else {
                        seats = seats + Lists.bookings.get(i).getBooked_seats();
                        Lists.bookings.remove(Lists.bookings.get(i));
                        cancel++;// increases the counter of cancellations by 1 
                    }
                }

            }
        }
        for (int i = 0; i < Lists.event_list.size(); i++) {// για κάθε στοιχείο της  λίστας 
            if (Lists.event_list.get(i).getTitle().equals(((Booking) obj).getTitle())
                    && Lists.event_list.get(i).getTime().equals(((Booking) obj).getTime())) {
                Lists.event_list.get(i).setAvailable_seats(Lists.event_list.get(i).getAvailable_seats() + seats);
            }
        }

        return cancel;
    }
    }
  
}
