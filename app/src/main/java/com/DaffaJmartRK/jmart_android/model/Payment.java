package com.DaffaJmartRK.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;
import com.DaffaJmartRK.jmart_android.model.Invoice.Status;

public class Payment extends Invoice{
    public ArrayList<Record> history = new ArrayList<>();
    public Shipment shipment;
    public int productCount;

    public static class Record{
        public final Date date;
        public String message;
        public Status status;

        public Record(Status status, String message) {
            date = new Date();
            this.status = status;
            this.message = message;

        }
    }

    public Payment(int buyerId, int productId, int productCount, Shipment shipment){
        super(buyerId, productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }
}
