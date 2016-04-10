package co.clixstudios.smarttravel;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private List<Element> elements = new ArrayList<Element>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Row() {
    }

    /**
     *
     * @param elements
     */
    public Row(List<Element> elements) {
        this.elements = elements;
    }

    /**
     *
     * @return
     * The elements
     */
    public List<Element> getElements() {
        return elements;
    }

    /**
     *
     * @param elements
     * The elements
     */
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

}