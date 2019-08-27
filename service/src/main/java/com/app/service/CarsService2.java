package com.app.service;


import com.app.enums.CarBodyType;
import com.app.enums.Criterion2;
import com.app.enums.EngineType;
import com.app.enums.TyreType;
import com.app.exception.MyUncheckedException2;
import com.app.model.Car2;
import com.app.utility.CarJsonConverter2;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CarsService2 {

    private Set<Car2> car2Set;

    public CarsService2(List<String> dataList) {
        if(dataList == null){
            throw  new MyUncheckedException2(" Argument is null in CarsService2 Constructor ");
        }
        this.car2Set = dataLoader(dataList);
    }

    public Set<Car2> getCar2Set() {
        return car2Set;
    }

    @Override
    public String toString() {
        return "CarService{" +
                "car2Set=" + car2Set +
                '}';
    }

    private static Set<Car2> dataLoader(List<String> filesNameList) {
        if(filesNameList == null){
            throw  new MyUncheckedException2(" Argument is null in CarsService2 DataLoader ");
        }
        return filesNameList.stream()
                .map(m->new CarJsonConverter2(m)
                .fromJson()
                .orElseThrow(()-> new MyUncheckedException2(" dataLoader Error")))
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
    public Set<Car2> sortMethodByParam(Criterion2 choice, boolean isReversOrder) {
        System.out.println("solution for task nr 1 --------------->>>>>>>>>>>>");
//  PRICE,MILEAGE,POWER,NUMBER_COMPONENTS,SIZE_WHEEL,

        if (choice == null) {
            throw new MyUncheckedException2(" null argument in sortMethodByParam method ");
        }
        List<Car2> carsList = new ArrayList<>();
                switch (choice) {
                    case PRICE : {
                        carsList = car2Set.stream().sorted(Comparator.comparing(Car2::getPrice)).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case MILEAGE : {
                        carsList = car2Set.stream().sorted(Comparator.comparing(Car2::getMileage)).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case POWER : {
                        carsList = car2Set.stream().sorted(Comparator.comparing(s -> s.getEngine().getPower())).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case NUMBER_COMPONENTS : {
                        carsList =  car2Set.stream().sorted(Comparator.comparing(m -> m.getCarBody().getComponents().size())).collect(Collectors.toCollection(ArrayList::new));
                        break;
                    }
                    case SIZE_WHEEL : {
                        carsList  = car2Set.stream().sorted(Comparator.comparing(c -> c.getWheel().getSize())).collect(Collectors.toCollection(ArrayList::new));
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
    public Set<Car2> carBodyCollectionByPrice(CarBodyType cBT, BigDecimal min, BigDecimal max) {
        if(cBT == null || min == null || max == null){
            throw new MyUncheckedException2("ARGUMENT IN carBodyCollectionByPrice is NULL");
        }
        if(min.compareTo(max) > 0){
            throw new MyUncheckedException2("WRONG RANGE MIN IS BIGGER THAN MAX");
        }
        System.out.println("solution for task nr 2 --------------->>>>>>>>>>>>");
        return car2Set.stream()
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
        return car2Set.stream()
                .filter(f -> f.getEngine().getEngineType() == engineType)
                .map(Car2::getModel)
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
                BigDecimal aver = car2Set.stream().map(Car2::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).divideToIntegralValue(new BigDecimal(car2Set.size()));
                BigDecimal min = car2Set.stream().min(Comparator.comparing(Car2::getPrice)).get().getPrice();
                BigDecimal max = car2Set.stream().max(Comparator.comparing(Car2::getPrice)).get().getPrice();
                System.out.println(MessageFormat.format(" PRICE -> aver {0}, max{1}, min{2} ", aver.setScale(2), max.setScale(2), min.setScale(2)));
                break;
            }
            case MILEAGE: {
                iss = car2Set.stream().collect(Collectors.summarizingInt(Car2::getMileage));
                System.out.println(" MILEAGE ->/aver/max/min  " + iss.getAverage() + " " + iss.getMax() + " " + iss.getMin());
                break;
            }
            case POWER: {
                dss = car2Set.stream().collect(Collectors.summarizingDouble(m -> m.getEngine().getPower()));
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

    public Map<Car2, Integer> mapByCarsAndMileage() {
        System.out.println("solution for task nr 5 --------------->>>>>>>>>>>>");
        return car2Set.stream()
                .collect(Collectors.toMap(
                       Function.identity(),
                        Car2::getMileage
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

    public Map<TyreType, List<Car2>> groupingByTyreType() {
        System.out.println("solution for task nr 6 --------------->>>>>>>>>>>>");

        return car2Set.stream()
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
