package me.lukeben.verification;

import me.lukeben.backend.module.Module;

public class VerificationModule extends Module {

    public VerificationModule() {
        super("Verification");
    }

    @Override
    public void moduleStart() {
        BuyerData.getInstance().load();
        new VerifyCommand().registerCommand();
    }

}
