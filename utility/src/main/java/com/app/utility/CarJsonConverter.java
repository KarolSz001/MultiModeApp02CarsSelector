package com.app.utility;


import com.app.model.Car;

public class CarJsonConverter extends JsonConverter<Car> {
    public CarJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
