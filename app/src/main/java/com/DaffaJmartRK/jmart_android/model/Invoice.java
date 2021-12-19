package com.DaffaJmartRK.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Model Class invoice
 * @author M. Daffa Ajiputra
 * @version Final
 */
public abstract class Invoice extends Serializable{
    /**
     * Instance variable untk model class invoice
     */
    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;

    /**
     * Enum class untuk status
     */
    public enum Status{
        WAITING_CONFIRMATION, CANCELLED, ON_PROGRESS, ON_DELIVERY, COMPLAINT, FINISHED, FAILED, DELIVERED;
    }

    /**
     * Enum class untuk Rating
     */
    public enum Rating{
        NONE, BAD, NEUTRAL, GOOD;
    }

    /**
     * Constructor method untuk invoice
     * @param buyerId   id pembeli
     * @param productId id produk
     */
    protected Invoice(int buyerId, int productId){
        this.buyerId = buyerId;
        this.productId = productId;
        this.complaintId = -1;
        this.date = new Date();
        this.rating = Rating.NONE;
    }
}
