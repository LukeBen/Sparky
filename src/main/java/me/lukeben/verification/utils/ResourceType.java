package me.lukeben.verification.utils;

public enum  ResourceType {

    IMMORTAL_TAGS("ImmortalTags", 6.99);

    private final String name;
    private final double price;
    ResourceType(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
