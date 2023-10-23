package de.vepexlegit.chestespdebug;

public enum ChestESP {
    INSTANCE;

    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
