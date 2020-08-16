package me.lukeben.administration;

import me.lukeben.backend.module.Module;

public class AdministrationModule extends Module {

    public AdministrationModule() {
        super("Administration");
    }

    @Override
    public void moduleStart() {
        new EmbedCommand().registerCommand();
    }

}
