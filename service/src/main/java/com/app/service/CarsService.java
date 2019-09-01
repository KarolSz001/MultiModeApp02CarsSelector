package com.app.service;


import com.app.enums.CarBodyType;
import com.app.enums.Criterion2;
import com.app.enums.EngineType;
import com.app.enums.TyreType;
import com.app.exception.MyUncheckedException;
import com.app.model.Car;
import com.app.utility.CarJsonConverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CarsService {

    private Set<Car> carSet = new HashSet<>();

    public CarsService(List<String> dataList) {
        if(dataList == null){
            throw  new MyUncheckedException("Argument is null in CarsService Constructor");
        }
        this.carSet = dataLoader(dataList);
    }

    public CarsService() {
    }

    public Set<Car> getCarSet() {
        return carSet;
    }

    public void addItem(Car car){
        carSet.add(car);
    }

    @Override
    public String toString() {
        return "CarService{" +
                "carSet=" + carSet +
                '}';
    }

    private static Set<Car> dataLoader(List<String> filesNameList) {
        if(filesNameList == null){
            throw  new MyUncheckedException(" Argument is null in CarsService DataLoader ");
        }
        return filesNameList.stream()
                .map(m->new CarJsonConverter(m)
                .fromJson()
                .orElseThrow(()-> new MyUncheckedException(" dataLoader Error")))
                .collect(Collectors.toSet());
    }

    //////////////////////////////////////////////////TASK1///////////////////////////////////////////////////////

    /**
     * This method return Set Collection of filtered by number of components, power of engine or wheels size
     * and sort natural or reverse order
     * @param choice
     * @param isReversOrder
     * @return Set<Car2>
     */
    public Set<Car> sortMethodByParam(Criterion2 choice, boolean isReversOrder) {
        System.out.println("solution for task nr 1 --------------->>>>>>>>>>>>");
//  PRICE,MILEAGE,POWER,NUMBER_COMPONENTS,SIZE_WHEEL,

        if (choice == null) {
            throw new MyUncheckedException(" null argument in sortMethodByParam method ");
        }
        List<Car> carsList = new ArrayList<>();
                switch (choice) {
                    case PRICE : {
                        carsList = carSet.stream().sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case MILEAGE : {
                        carsList = carSet.stream().sorted(Comparator.comparing(Car::getMileage)).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case POWER : {
                        carsList = carSet.stream().sorted(Comparator.comparing(s -> s.getEngine().getPower())).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case NUMBER_COMPONENTS : {
                        carsList =  carSet.stream().sorted(Comparator.comparing(m -> m.getCarBody().getComponents().size())).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case SIZE_WHEEL : {
                        carsList  = carSet.stream().sorted(Comparator.comparing(c -> c.getWheel().getSize())).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
        };
        if (isReversOrder) {
            Collections.reverse(carsList);
        }
        return new LinkedHashSet<>(carsList);
    }

    //////////////////////////////////////////////////TASK2///////////////////////////////////////////////////////

    /**
     * This method return Set Collection of filtered by CarBodyType and min , max price
     * @param cBT -> CarBodyType
     * @param min -> minimum price
     * @param max -> minimum price
     * @return Set<String>
     */
    public Set<Car> carBodyCollectionByPrice(CarBodyType cBT, BigDecimal min, BigDecimal max) {
        if(cBT == null || min == null || max == null){
            throw new MyUncheckedException("ARGUMENT IN carBodyCollectionByPrice is NULL");
        }
        if(min.compareTo(max) > 0){
            throw new MyUncheckedException("WRONG RANGE MIN IS BIGGER THAN MAX");
        }
        System.out.println("solution for task nr 2 --------------->>>>>>>>>>>>");
        return carSet.stream()
                .filter(f -> f.getCarBody().getType() == cBT)
                .filter(ff -> ff.getPrice().compareTo(min) > 0 && ff.getPrice().compareTo(max) < 0)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    //////////////////////////////////////////////////TASK3///////////////////////////////////////////////////////

    /**
     * This method return Set Collection of Model filtered by engineType
     * and sorted Collection by Model
     * @param engineType
     * @return Set<String>
     */
// try comparator and then
    public Set<String> carsWithEngineType(EngineType engineType) {
        System.out.println(" solution for task nr 3 --------------->>>>>>>>>>>> ");
        return carSet.stream()
                .filter(f -> f.getEngine().getEngineType() == engineType)
                .map(Car::getModel)
                .sorted(Comparator.comparing(Function.identity()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    //////////////////////////////////////////////////TASK4///////////////////////////////////////////////////////

    /**
     * This method show statistic by parameter , price, mileage, power
     * @param parameter
     * return information formatted in console
     */
    public void showStatisticByParameter(Criterion2 parameter) {
        System.out.println("solution for task nr 4 --------------->>>>>>>>>>>>");
        IntSummaryStatistics iss;
        DoubleSummaryStatistics dss;
        switch (parameter) {

            case PRICE: {
                BigDecimal aver = carSet.stream().map(Car::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).divideToIntegralValue(new BigDecimal(carSet.size()));
                BigDecimal min = carSet.stream().min(Comparator.comparing(Car::getPrice)).get().getPrice();
                BigDecimal max = carSet.stream().max(Comparator.comparing(Car::getPrice)).get().getPrice();
                System.out.println(MessageFormat.format(" PRICE -> aver {0}, max{1}, min{2} ", aver.setScale(2), max.setScale(2), min.setScale(2)));
                break;
            }
            case MILEAGE: {
                iss = carSet.stream().collect(Collectors.summarizingInt(Car::getMileage));
                System.out.println(" MILEAGE ->/aver/max/min  " + iss.getAverage() + " " + iss.getMax() + " " + iss.getMin());
                break;
            }
            case POWER: {
                dss = carSet.stream().collect(Collectors.summarizingDouble(m -> m.getEngine().getPower()));
                DecimalFormat dc =  new DecimalFormat("#0.00");
                System.out.println(" POWER ->/aver/max/min  " + dc.format(dss.getAverage()) + " " + dc.format(dss.getMax()) + " " + dc.format(dss.getMin()));
                break;
            }
        }
    }
    //////////////////////////////////////////////////TASK5///////////////////////////////////////////////////////
    /**
     * This method return  Map Car and number of Mileage
     * sorted by mileage
     * @return Map<Car2, Integer>
     */

    public Map<Car, Integer> mapByCarsAndMileage() {
        System.out.println("solution for task nr 5 --------------->>>>>>>>>>>>");
        return carSet.stream()
                .collect(Collectors.toMap(
                       Function.identity(),
                        Car::getMileage
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::max,
                        LinkedHashMap::new
                ));
    }
    //////////////////////////////////////////////////TASK6///////////////////////////////////////////////////////

    /**
     * This method return  Map , Key TyreType and Value list of cars how have this TyreType
     * sorted by number of Cars
     * @return Map<TyreType, List < Car2>>
     */

    public Map<TyreType, List<Car>> groupingByTyreType() {
        System.out.println("solution for task nr 6 --------------->>>>>>>>>>>>");

        return carSet.stream()
                .collect(Collectors.groupingBy(e -> e.getWheel().getTyreType()
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().filter(f -> f.getWheel().getTyreType().equals(e.getKey())).collect(Collectors.toCollection(ArrayList::new))
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(c -> c.getValue().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
   }

}
