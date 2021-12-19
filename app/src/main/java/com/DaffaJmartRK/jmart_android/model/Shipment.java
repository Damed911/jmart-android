package com.DaffaJmartRK.jmart_android.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Model class Shipment
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class Shipment {
    /**
     * Instance Variable class Shipment
     */
    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    /**
     * Constructor method untuk Shipment
     * @param address   alamat pengiriman
     * @param cost      Biaya pengiriman
     * @param plan      plan shipment
     * @param receipt   receipt shipment
     */
    public Shipment(String address, int cost, byte plan, String receipt) {
        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;
    }
}
