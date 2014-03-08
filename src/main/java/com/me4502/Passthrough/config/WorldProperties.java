package com.me4502.Passthrough.config;

public class WorldProperties {

    private String name;

    private double blockModifier;

    public WorldProperties(String worldName) {

        name = worldName;
    }

    public String getName() {

        return name;
    }

    public void setBlockModifier(double blockModifier) {

        this.blockModifier = blockModifier;
    }

    public double getBlockModifier() {

        return blockModifier;
    }

    @Override
    public int hashCode() {

        return name.hashCode();
    }
}