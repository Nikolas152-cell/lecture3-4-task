import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

class Violent{

    public Violent(String date_time, String first_name, String last_name, String type, double fine_amount) {
        this.date_time = date_time;
        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
        this.fine_amount = fine_amount;
    }
        Violent()
        {

        }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFineAmount() {
        return fine_amount;
    }

    public void setFineAmount(double fine_amount) {
        this.fine_amount = fine_amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date_time, first_name, last_name, type, fine_amount);
    }

    private String date_time;
    private String first_name;
    private String last_name;
    private String type;
    private double fine_amount;
}
