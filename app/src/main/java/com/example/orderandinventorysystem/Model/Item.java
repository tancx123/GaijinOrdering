package com.example.orderandinventorysystem.Model;

import java.io.Serializable;

public class Item implements Serializable {

    private String itemName, itemID, itemUnit, itemDesc;
    private double sellPrice, costPrice;
    private int quantity = 0, quantityPHY = 0;

    public Item(String itemID, String itemName,String itemUnit, String itemDesc, double sellPrice, double costPrice) {

        this.itemID = itemID;
        this.itemName = itemName;
        this.itemUnit = itemUnit;
        this.itemDesc = itemDesc;
        this.sellPrice = sellPrice;
        this.costPrice = costPrice;
    }

    public Item(String itemID, String itemName,String itemUnit, String itemDesc, int quantity, int quantityPHY,double sellPrice, double costPrice) {

        this.itemID = itemID;
        this.itemName = itemName;
        this.itemUnit = itemUnit;
        this.itemDesc = itemDesc;
        this.quantity = quantity;
        this.quantityPHY = quantityPHY;
        this.sellPrice = sellPrice;
        this.costPrice = costPrice;
    }

    public int getQuantityPHY() {
        return quantityPHY;
    }

    public void setQuantityPHY(int quantityPHY) {
        this.quantityPHY = quantityPHY;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemSKU(String itemID) {
        this.itemID = itemID;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

}
