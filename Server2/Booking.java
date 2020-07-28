
import java.io.Serializable;
import java.util.Date;


public class Booking implements Serializable {
    //attributes  
    private String title;
    private String event_date;
    private String time;
    private int booked_seats;
    private float cost;
    private String username;

    public Booking(String title, String event_date,String time, int booked_seats, float cost, String username) {//constructor κράτησης 
        this.title = title;
        this.event_date = event_date;
        this.time = time;
        this.booked_seats = booked_seats;
        this.cost = cost;
        this.username = username;

    }
    // Accessors 
    public String getTitle() {
        return title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public int getBooked_seats() {
        return booked_seats;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Booking{" + "title=" + title + ", event_date=" + event_date + ", time=" + time + ", booked_seats=" + booked_seats + ", cost=" + cost + ", username=" + username + '}';
    }

}
