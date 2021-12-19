package com.DaffaJmartRK.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

public abstract class Invoice extends Serializable{
    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;

    public enum Status{
        WAITING_CONFIRMATION, CANCELLED, ON_PROGRESS, ON_DELIVERY, COMPLAINT, FINISHED, FAILED, DELIVERED;
    }
    public enum Rating{
        NONE, BAD, NEUTRAL, GOOD;
    }
    protected Invoice(int buyerId, int productId){
        this.buyerId = buyerId;
        this.productId = productId;
        this.complaintId = -1;
        this.date = new Date();
        this.rating = Rating.NONE;
    }
}
