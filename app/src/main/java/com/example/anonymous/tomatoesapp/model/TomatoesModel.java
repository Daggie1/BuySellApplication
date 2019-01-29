package com.example.anonymous.tomatoesapp.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TomatoesModel {
    String productName, description, sellerId, productId, lat, longt, mapmarker_name,price,product_pictureUrl,quantity;

    public TomatoesModel() {
    }

    public TomatoesModel(String productName, String description, String sellerId, String productId, String lat, String longt, String mapmarker_name,String price, String product_pictureUrl,String quantity) {
        this.productName = productName;
        this.description = description;
        this.sellerId = sellerId;
        this.productId = productId;
        this.lat = lat;
        this.longt = longt;
        this.mapmarker_name=mapmarker_name;
        this.price = price;
        this.product_pictureUrl=product_pictureUrl;
        this.quantity=quantity;
    }

    public TomatoesModel(String productName, String description, String productId, String price, String product_pictureUrl) {
        this.productName = productName;
        this.description = description;
        this.productId = productId;
        this.price = price;
        this.product_pictureUrl = product_pictureUrl;
    }



    public String getMapmarker_name() {
        return mapmarker_name;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getLat() {
        return lat;
    }

    public String getLongt() {
        return longt;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_pictureUrl() {
        return product_pictureUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productName", productName);
        result.put("productPrice", price);
        result.put("productDesc", description);
        result.put("productPicUrl", product_pictureUrl);

        return result;
    }
}


