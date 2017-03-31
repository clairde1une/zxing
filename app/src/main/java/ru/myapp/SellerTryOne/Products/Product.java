package ru.myapp.SellerTryOne.Products;

import com.j256.ormlite.field.DatabaseField;

public class Product {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    String barcode;

    @DatabaseField
    int amount;

    public Product(){};

    public Product(final String barcode, final int amount){
        this.barcode = barcode;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", barcode=" + barcode +
                ", amount=" + amount +
                '}';
    }
}
