package com.archsystemsinc.pqrs.model;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by MurugarajKandaswam on 5/11/2017.
 */
//@Document(collection = "pchpsa")
public class Pchpsa {

  //  @Id
    private String id;

    private String zipCode;
    private String state;

    public Pchpsa(String zipCode, String state) {this.zipCode = zipCode; this.state = state;}

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
