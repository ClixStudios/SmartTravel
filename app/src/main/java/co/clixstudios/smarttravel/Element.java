package co.clixstudios.smarttravel;

public class Element {

    private Distance distance;
    private Duration duration;
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public Element() {
    }

    /**
     *
     * @param duration
     * @param distance
     * @param status
     */
    public Element(Distance distance, Duration duration, String status) {
        this.distance = distance;
        this.duration = duration;
        this.status = status;
    }

    /**
     *
     * @return
     * The distance
     */
    public Distance getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     * The duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     * The duration
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}