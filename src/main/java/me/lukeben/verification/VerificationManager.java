package me.lukeben.verification;

import lombok.Getter;
import me.lukeben.verification.utils.PurchasedResource;

import java.util.HashMap;
import java.util.List;

@Getter
public class VerificationManager {

    @Getter
    private static final VerificationManager instance = new VerificationManager();

    private final HashMap<String, List<PurchasedResource>> pendingVerification;

    public VerificationManager() {
        this.pendingVerification = new HashMap<>();
    }



}
