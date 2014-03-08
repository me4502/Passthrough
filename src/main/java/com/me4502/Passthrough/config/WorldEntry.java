package com.me4502.Passthrough.config;

public class WorldEntry {

    private String name;

    private String above,beneath;

    private double height,depth;

    public WorldEntry(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setAbove(String above) {

        this.above = above;
    }

    public String getAbove() {

        return above;
    }

    public void setBeneath(String beneath) {

        this.beneath = beneath;
    }

    public String getBeneath() {

        return beneath;
    }

    public void setHeight(double height) {

        this.height = height;
    }

    public double getHeight() {

        return height;
    }

    public void setDepth(double depth) {

        this.depth = depth;
    }

    public double getDepth() {

        return depth;
    }

    @Override
    public int hashCode() {

        return name.hashCode();
    }
}