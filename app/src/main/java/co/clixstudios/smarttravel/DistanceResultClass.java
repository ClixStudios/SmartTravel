package co.clixstudios.smarttravel;

import java.util.ArrayList;
import java.util.List;

public class DistanceResultClass {

    private List<String> destinationAddresses = new ArrayList<String>();
    private List<String> originAddresses = new ArrayList<String>();
    private List<Row> rows = new ArrayList<Row>();
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public DistanceResultClass() {
    }

    /**
     *
     * @param destinationAddresses
     * @param status
     * @param originAddresses
     * @param rows
     */
    public DistanceResultClass(List<String> destinationAddresses, List<String> originAddresses, List<Row> rows, String status) {
        this.destinationAddresses = destinationAddresses;
        this.originAddresses = originAddresses;
        this.rows = rows;
        this.status = status;
    }

    /**
     *
     * @return
     * The destinationAddresses
     */
    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    /**
     *
     * @param destinationAddresses
     * The destination_addresses
     */
    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    /**
     *
     * @return
     * The originAddresses
     */
    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    /**
     *
     * @param originAddresses
     * The origin_addresses
     */
    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    /**
     *
     * @return
     * The rows
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     *
     * @param rows
     * The rows
     */
    public void setRows(List<Row> rows) {
        this.rows = rows;
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