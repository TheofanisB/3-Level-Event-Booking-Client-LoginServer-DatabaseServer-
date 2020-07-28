
import java.io.Serializable;
import java.util.Date;


public class Event implements Serializable {// κλάση εκδήλωσης 
// attributes εκδήλωσης 
    private String title;//τίτλος
    private String genre;//είδος
    private String event_date;//ημερομηνία
    private int available_seats;//Διαθέσιμες Θέσεις
    private int cost_per_seat;//Κόστος ανά θεατή 
    private String time;//Ώρα έναρξης εκδήλωσης 

    public Event(String title, String genre, String event_date, String time, int available_seats, int cost_per_seat) {// constructor of the event
        this.title = title;
        this.genre = genre;
        this.event_date = event_date;
        this.time = time;
        this.available_seats = available_seats;
        this.cost_per_seat = cost_per_seat;
    }
    // accessors 
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getEvent_date() {
        return event_date;
    }

    public int getAvailable_seats() {
        return available_seats;
    }

    public int getCost_per_seat() {
        return cost_per_seat;
    }

    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Event{" + "title=" + title + ", genre=" + genre + ", event_date=" + event_date + ", available_seats=" + available_seats + ", cost_per_seat=" + cost_per_seat + ", time=" + time + '}';
    }

    
    
    
}
