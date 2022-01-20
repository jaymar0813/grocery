package com.example.DCOD3;

public class Order {
    private String item;
    private String price;
    private String quantity;
    private String sum;

    public Order() {
    }

    public Order(String item, String price, String quantity, String sum){
        this.item = item;
        this.price = price;
        this.quantity =quantity;
        this.sum = sum;

    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
