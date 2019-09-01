package com.app.service;

import com.app.enums.CarBodyType;
import com.app.enums.Criterion2;
import com.app.enums.EngineType;
import com.app.exception.MyUncheckedException;
import com.app.model.Car;
import com.app.model.CarGenerator;
import com.app.utility.CarJsonConverter;
import com.app.utility.DataManager;
import com.app.utility.ScreenManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class ControlAppService {

    private final List<String> filesNameList = Arrays.asList("App02jsonTestFile21.json", "App02jsonTestFile22.json",
            "App02JsonFileCar3.json", "App02JsonFileCar4.json", "App02JsonFileCar5.json");

    private final DataManager dataManager = new DataManager();
    private CarsService carService = new CarsService(filesNameList);

    public ControlAppService() {
        saveDataToJsonFile();
    }

    public void runApp() {

        do {
            try {
                dataManager.getLine(" PRESS ENTER TO CONTINUE ");
                ScreenManager.printMenu();
                int number = dataManager.getInt(" MAKE A CHOICE PRESS FROM 0 TO 7 ");
                switch (number) {
                    case 0: {
                        ScreenManager.clearScreen2();
                        System.out.println(" -------------------GOOD BYE------------------------ ");
                        return;
                    }
                    case 1: {
                        task1();
                        break;
                    }
                    case 2: {
                        task2();
                        break;
                    }
                    case 3: {
                        task3();
                        break;
                    }
                    case 4: {
                        task4();
                        break;
                    }
                    case 5: {
                        task5();
                        break;
                    }
                    case 6: {
                        task6();
                        break;
                    }
                    case 7: {
                        printRawData(carService.getCarSet());
                        break;
                    }
                    default: {
                        System.out.println(" wrong choice try again ");
                        ScreenManager.clearScreen2();
                        break;
                    }
                }

            } catch (MyUncheckedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private void printRawData(Set<Car> store) {
        System.out.println(" Loading data.......... raw data \n");
        store.stream().forEach(System.out::println);
    }

    public void saveDataToJsonFile() {
        filesNameList.forEach(name -> {
            try {
                if (name == null) {
                    throw new MyUncheckedException("name file is null");
                }
                new CarJsonConverter(name).toJson(CarGenerator.carGenerator());
            } catch (MyUncheckedException e) {
                e.printStackTrace();
            }
        });
    }

    private void task1() {
        //components, power, size of wheel
        ScreenManager.clearScreen2();
        Criterion2 choice = dataManager.getChoice("1");
        boolean isRev = dataManager.getBoolean(" natural sort ? ");
        carService.sortMethodByParam(choice, isRev).stream().forEach(System.out::println);
    }

    private void task2() {
        ScreenManager.clearScreen2();
        //choose parameter -> SEDAN, HATCHBACK, COMBI //
        CarBodyType carBodyType = dataManager.getCarBodyType();
        BigDecimal minPrice = new BigDecimal(dataManager.getInt(" press minPrice "));
        BigDecimal maxPrice = new BigDecimal(dataManager.getInt(" press maxPrice "));
        carService.carBodyCollectionByPrice(carBodyType, minPrice, maxPrice).stream().forEach(System.out::println);
    }

    private void task3() {
        ScreenManager.clearScreen2();
        EngineType engineType = dataManager.getEngineType();
        //choose parameter -> SEDAN, HATCHBACK, COMBI //
        carService.carsWithEngineType(engineType).stream().forEach(System.out::println);
    }

    private void task4() {
        // price, mileage, power
        ScreenManager.clearScreen2();
        Criterion2 parameter = dataManager.getChoice("2");
        carService.showStatisticByParameter(parameter);
    }

    private void task5() {
        ScreenManager.clearScreen2();
        carService.mapByCarsAndMileage().entrySet().stream().forEach(System.out::println);
    }

    private void task6() {
        ScreenManager.clearScreen2();
        carService.groupingByTyreType().entrySet().stream().forEach(System.out::println);
    }

}
