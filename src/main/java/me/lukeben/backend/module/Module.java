package me.lukeben.backend.module;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Module implements ModuleController {

    /**
     * Contains the of the current module name.
     */
    protected String moduleName;

    @Setter
    /**Nn
     * Contains the state of the current module.
     */
    protected State moduleState = State.ENABLED;

    protected Module(final String moduleName) {
        this.moduleName = moduleName;
        ModuleRegistry.getInstance().registerModule(this);
        System.out.println("[Module] Starting " + getModuleName() + " module.");
    }

    public void loadByClass(final Class... classes) {
        for (final Class clazz : classes)
            loadByClass(clazz);
    }

    public <T> T loadByClass(final Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (final Exception ex) {
            throw new IllegalStateException(ex.getClass().getSimpleName() + " : " + ex.getMessage());
        }
    }


}
