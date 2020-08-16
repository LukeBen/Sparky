package me.lukeben.invite;

import me.lukeben.backend.module.Module;

public class InviteModule extends Module {

    public InviteModule() {
        super("Invite");
    }

    @Override
    public void moduleStart() {
        new InviteCommand().registerCommand();
    }

}
