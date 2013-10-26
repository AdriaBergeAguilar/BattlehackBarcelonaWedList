package es.catmobil.wedlist.model;

import java.util.Date;

import es.catmobil.wedlist.model.Gift;
import es.catmobil.wedlist.model.Person;

/**
 * Created by mferran on 26/10/13.
 */
public class Buy extends  Persistant{
    private Gift gift;
    private float importGiven;
    private String currency;
    private Person buyer;
    private Date date;

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public float getImportGiven() {
        return importGiven;
    }

    public void setImportGiven(float importGiven) {
        this.importGiven = importGiven;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
