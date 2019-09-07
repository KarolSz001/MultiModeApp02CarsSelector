package com.app.model;

import com.app.enums.CarBodyColor;
import com.app.enums.CarBodyType;
import com.app.enums.EngineType;
import com.app.enums.TyreType;
import com.app.exception.MyUncheckedException;
import com.app.model.valid.CarValidatorImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CarGenerator {



    public static Car carGenerator() {
        CarValidatorImpl carValidator = new CarValidatorImpl();
        String model = modelCarGenerator();
        BigDecimal price = priceGenerator();
        int mileage = mileageGenerator();
        Engine engine = EngineGenerator.engineGenerator();
        CarBody carBody = CarBodyGenerator.carBodyGenerator();
        Wheel wheel = WheelGenerator.wheelGenerator();
        Car car = Car.builder().model(model).price(price).mileage(mileage).engine(engine).carBody(carBody).wheel(wheel).build();

        if (carValidator.hasErrors()) {
            System.out.println(" list of errors with validation of car ");
            carValidator.getErrors().forEach((k, v) -> System.out.println(k + "::::::::::" + v));
            throw new MyUncheckedException("car is not validate");
        }

        return car;
    }

    private static String modelCarGenerator() {
        String[] modelCarArr = {"AUDI", "OPEL", "TESLA", "FIAT", "SYRENKA", "FERRARI"};
        int size = modelCarArr.length;
        int indexOfArr = new Random().nextInt(size);
        return modelCarArr[indexOfArr];
    }

    private static BigDecimal priceGenerator() {
        int minPrice = 300000;
        int maxPrice = 1000000;
        int price = new Random().nextInt(maxPrice - minPrice + 1) + minPrice;
        return new BigDecimal(price);
    }

    private static int mileageGenerator() {

        return new Random().nextInt(500000 - 100000 + 1) + 100000;
    }

    static class EngineGenerator {

        public static Engine engineGenerator() {
            EngineType engineType = EngineType.engineTypeGenerator();
            double power = powerNumberGenerator();
            return new Engine(engineType, power);
        }

        private static double powerNumberGenerator() {
            double[] powerArr = {81.0, 100.0, 102.2, 120.5};
            int arrIndex = new Random().nextInt(powerArr.length);
            return powerArr[arrIndex];
        }
    }

    static class WheelGenerator {

        public static Wheel wheelGenerator() {
            String model = modelGenerator();
            int size = sizeGenerator();
            TyreType tyreType = TyreType.tyreTypeGenerator();
            return new Wheel(model, size, tyreType);
        }

        private static String modelGenerator() {
            String[] modelArr = {"DEBICA", "MOTIV", "PACER"};
            int index = new Random().nextInt(modelArr.length);
            return modelArr[index];
        }

        private static int sizeGenerator() {
            int[] sizeArr = {17, 19, 21};
            int index = new Random().nextInt(sizeArr.length);
            return sizeArr[index];
        }
    }

    static class CarBodyGenerator {

        public static CarBody carBodyGenerator() {
            CarBodyColor color = CarBodyColor.carBodyColorGenerator();
            CarBodyType carBodyType = CarBodyType.carBodyTypeGenerator();
            List<String> componentList = componentsListCreator();
            return new CarBody(color, carBodyType, componentList);
        }

        private static String componentGenerator() {
            List<String> list = Arrays.asList("TV", "ABS", "RADIO", "GPS");
            int index = new Random().nextInt(list.size());
            return list.get(index);
        }

        private static List<String> componentsListCreator() {
            List<String> componentList = new ArrayList<>();
            int counter = 0;
            while (counter < 3) {
                String component = componentGenerator();
                if (!componentList.contains(component)) {
                    componentList.add(component);
                    counter++;
                }
            }
            return componentList;

        }

    }
}
