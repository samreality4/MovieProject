package com.example.android.hxh.model;

public class Height {
    private int inches;
    private String readable;

    public Height(int inches) {
        this.inches = inches;
        setReadable();
    }

    public int getInches() {
        return inches;
    }

    public void setReadable() {
        this.readable = String.format("%d'%d\"", getInches() / 12, getInches() % 12);
    }

    @Override
    public String toString() {
        return readable;
    }
}
