package com.DaffaJmartRK.jmart_android.model;

/**
 * Model Class Product
 * @author M. Daffa Ajiputra
 * @version Final
 */
public class Product extends Serializable{
    /**
     * Instance variable class Product
     */
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    /**
     * override method untuk mengubah variable menjadi string
     * @return name
     */
    @Override
    public String toString(){
        return name;
    }

}
