
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {// event class
// attributes of class
    private String title;//title
    private String genre;
    private String event_date;
    private int available_seats;
    private int cost_per_seat;
    private String time;

    public Event(String title, String genre, String event_date, String time, int available_seats, int cost_per_seat) {// constructor εκδήλωσης 
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
