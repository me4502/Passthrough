package com.me4502.Passthrough.config;

public class WorldProperties {

    private String name;

    private int blockModifier;

    public WorldProperties(String worldName) {

        name = worldName;
    }

    public String getName() {

        return name;
    }

    public void setBlockModifier(int blockModifier) {

        this.blockModifier = blockModifier;
    }

    public int getBlockModifier() {

        return blockModifier;
    }

    @Override
    public int hashCode() {

        return name.hashCode();
    }
}