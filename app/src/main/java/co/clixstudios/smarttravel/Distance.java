package co.clixstudios.smarttravel;


public class Distance {

    private String text;
    private Long value;

    /**
     * No args constructor for use in serialization
     *
     */
    public Distance() {
    }

    /**
     *
     * @param text
     * @param value
     */
    public Distance(String text, Long value) {
        this.text = text;
        this.value = value;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The value
     */
    public Long getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Long value) {
        this.value = value;
    }

}