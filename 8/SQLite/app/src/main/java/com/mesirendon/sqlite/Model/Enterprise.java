package com.mesirendon.sqlite.Model;

public class Enterprise {
    private long id;
    private String name;
    private String url;
    private String phone;
    private String email;
    private String productsAndServices;
    private String type;

    public Enterprise() {}

    public Enterprise(long id, String name, String url, String phone, String email, String productsAndServices, String type) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.phone = phone;
        this.email = email;
        this.productsAndServices = productsAndServices;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductsAndServices() {
        return productsAndServices;
    }

    public void setProductsAndServices(String productsAndServices) {
        this.productsAndServices = productsAndServices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Enterprise ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Url: " + getUrl() + "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Products and Services: " + getProductsAndServices() + "\n" +
                "Type: " + getType();
    }
}
