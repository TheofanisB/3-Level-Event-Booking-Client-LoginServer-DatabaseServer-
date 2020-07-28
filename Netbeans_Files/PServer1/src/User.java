
import java.io.Serializable;


public class User implements Serializable{// κλάση λογαριασμού 
    private String username;
    private String password;
    private String type;// admin/user 
    private String email;
    private String name;

    public User(String username, String password, String type, String email, String name) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.email = email;
    }
// accessors 
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", type=" + type + ", email=" + email + ", name=" + name + '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

    
    
    
    
    
}
