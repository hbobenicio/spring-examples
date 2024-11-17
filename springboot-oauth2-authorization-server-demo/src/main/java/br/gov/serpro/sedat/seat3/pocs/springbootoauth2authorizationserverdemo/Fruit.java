package br.gov.serpro.sedat.seat3.pocs.springbootoauth2authorizationserverdemo;

/**
 * A simple POJO that models a simple entity for our demo
 */
public class Fruit {

    private String name;

    private float price;

    public Fruit(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
