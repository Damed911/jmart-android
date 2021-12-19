package com.DaffaJmartRK.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;
import com.DaffaJmartRK.jmart_android.model.Invoice.Status;

/**
 * Model Class Payment
 * @author M. Daffa Ajiputra
 * @version final
 */
public class Payment extends Invoice{
    /**
     * Instance Variable class payment
     */
    public ArrayList<Record> history = new ArrayList<>();
    public Shipment shipment;
    public int productCount;

    /**
     * Inner class record
     */
    public static class Record{
        /**
         * Instance variable inner class record
         */
        public final Date date;
        public String message;
        public Status status;

        /**
         * Constructor method untuk Record
         * @param status
         * @param message
         */
        public Record(Status status, String message) {
            date = new Date();
            this.status = status;
            this.message = message;

        }
    }

    /**
     * Constructor method untuk Payment
     * @param buyerId       id pembeli
     * @param productId     id produk
     * @param productCount  jumlah produk yang dibeli
     * @param shipment      jenis pengiriman
     */
    public Payment(int buyerId, int productId, int productCount, Shipment shipment){
        super(buyerId, productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }
}
