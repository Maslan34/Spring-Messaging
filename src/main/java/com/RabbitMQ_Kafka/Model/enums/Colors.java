package com.RabbitMQ_Kafka.Model.enums;

public enum Colors {

    BLACK("Black"),
    WHITE("White"),
    ORANGE("Orange"),
    GREY("Grey"),
    RED("Red"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BROWN("Brown");

    Colors(String colorName) {
        this.colorName = colorName;
    }

    private final String colorName;

    public String getColorName() {
        return colorName;
    }
}
