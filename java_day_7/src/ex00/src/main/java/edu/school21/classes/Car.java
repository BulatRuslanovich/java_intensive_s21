package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
    private final String mark;
    private final boolean isNew;
    private final int price;

    public Car() {
        this.mark = "NoMark";
        this.isNew = false;
        this.price = 0;
    }

    public Car(String mark, boolean isNew, int price) {
        this.mark = mark;
        this.isNew = isNew;
        this.price = price;
    }

    public double getPriceInUSD() {
        return price / 100.0;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("mark='" + mark + "'")
                .add("isNew=" + isNew)
                .add("price=" + price)
                .toString();
    }
}
