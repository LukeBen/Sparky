package me.lukeben.verification.utils;

import lombok.Data;

import java.util.Date;

@Data
public class PurchasedResource {

    private final Date date;
    private final ResourceType type;


}
