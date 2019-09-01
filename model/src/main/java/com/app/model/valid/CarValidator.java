package com.app.model.valid;

import com.app.exception.MyUncheckedException;
import com.app.model.Car;

import java.math.BigDecimal;
import java.util.List;


public class CarValidator {

    private final String MODEL_REGEX = "[A-Z]+";
    private final String COMPONENT_REGEX = "[A-Z]+";
    private final BigDecimal MIN_PRICE = new BigDecimal(100000);
    private final Integer MIN_MILEAGE = 100000;
    private final String WHEEL_REGEX = "[A-Z]+";

    public boolean isValidate(Car car) throws MyUncheckedException {

        if (!isModelCorrect(car.getModel())) {
            throw new MyUncheckedException("model's name only work with Letters");
        }
        if (!isPriceCorrect(car.getPrice())) {
            throw new MyUncheckedException("component's name only work with Letters");
        }
        if (!isMileageCorrect(car.getMileage())) {
            throw new MyUncheckedException("mileage parameter is incorrect");
        }
        if (!allComponentsCorrect(car.getCarBody().getComponents())) {
            throw new MyUncheckedException("model's name only work with Letters");
        }
        if (!isEnginePowerCorrect(car.getEngine().getPower())) {
            throw new MyUncheckedException("engine power must be higher");
        }
        if (!isModelWheelCorrect(car.getWheel().getModel())) {
            throw new MyUncheckedException("model's wheel name only work with Letters");
        }
        return true;
    }

    private boolean isPriceCorrect(BigDecimal price) {
        return price.compareTo(MIN_PRICE) > 0;
    }

    private boolean isMileageCorrect(Integer mileage) {
        return mileage > MIN_MILEAGE;
    }

    private boolean isModelCorrect(String model) {
        return model.matches(MODEL_REGEX);
    }

    private boolean allComponentsCorrect(List<String> components) {
        return components.stream().allMatch(f -> f != null && f.matches(COMPONENT_REGEX));
    }
    private boolean isEnginePowerCorrect(Double power){
        return power > 0;
    }
    private boolean isModelWheelCorrect(String modelWheel) {
        return modelWheel.matches(WHEEL_REGEX);
    }

}
