package com.example.codereview.willowtree.dataModels;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;


//@Generated("org.jsonschema2pojo")

public class LaureateList {

    @Expose
    private List<Laureate> laureates = new ArrayList<Laureate>();

    /**
     *
     * @return
     * The laureates
     */
    public List<Laureate> getLaureates() {
        return laureates;
    }

    /**
     *
     * @param laureates
     * The laureates
     */
    public void setLaureates(List<Laureate> laureates) {
        this.laureates = laureates;
    }

}
