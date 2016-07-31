package ru.stefa.tizarhunter.stefasms.data.models;

/**
 * Created by Danil on 25.07.2016.
 */
public class NumbersBaseModel {
    private String name;
    private int countNumbers;
    private long lastUse;

    public String getName() {
        return name;
    }

    public NumbersBaseModel setName(String name) {
        this.name = name;
        return this;
    }

    public int getCountNumbers() {
        return countNumbers;
    }

    public NumbersBaseModel setCountNumbers(int countNumbers) {
        this.countNumbers = countNumbers;
        return this;
    }

    public long getLastUse() {
        return lastUse;
    }

    public NumbersBaseModel setLastUse(long lastUse) {
        this.lastUse = lastUse;
        return this;
    }
}
