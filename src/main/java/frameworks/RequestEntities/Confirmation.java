package frameworks.RequestEntities;

public class Confirmation {
    private Long flightId;
    private Long userId;
    public Confirmation(){

    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
