package com.app.test;

import com.app.enums.CarBodyType;
import com.app.enums.Criterion2;
import com.app.exception.MyUncheckedException;
import com.app.model.Car;
import com.app.service.CarsService;
import com.app.utility.CarJsonConverter;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TestCarService {

    CarsService carsService = new CarsService(List.of("App02jsonTestFile21.json", "App02jsonTestFile22.json"));
    private final String fileName = "App02jsonTestFile21.json";
    private final CarJsonConverter carsJson = new CarJsonConverter(fileName);


    @Test()
    @DisplayName("Test -> shouldNotThrowNoException")
    public void shouldNotThrowNoException() {
        System.out.println(carsService.toString());
    }


    @Test()
    @DisplayName("Test -> shouldThrowExceptionForNullArgsInServiceConstructor")
    public void shouldThrowExceptionForNullArgsInConstructor() {

        MyUncheckedException e = Assertions.assertThrows(MyUncheckedException.class, () -> new CarsService(null));
        Assertions.assertEquals("Argument is null in CarsService Constructor", e.getMessage());
    }

    @Test()
    @DisplayName("Test -> shouldThrowExceptionForNullArgsInMethod")
    public void shouldThrowExceptionForNullArgsInMethod() {
        MyUncheckedException e = Assertions.assertThrows(MyUncheckedException.class, () -> carsService.sortMethodByParam(null, true));
        Assertions.assertEquals(" null argument in sortMethodByParam method ", e.getMessage());
    }

    @Test()
    @DisplayName("Test -> shouldReturnCorrectNmberOfCarsWithProperEngine")
    public void shouldReturnCorrectNumberOfCars() {

        Assertions.assertEquals(2, carsService.sortMethodByParam(Criterion2.MILEAGE, true).size());

    }

    @Test()
    @DisplayName("Test -> shouldThrowExceptionForWrongArgsInMethod")
    public void shouldThrowExceptionForWrongArgsInMethod() {

        MyUncheckedException e = Assertions.assertThrows(MyUncheckedException.class, () -> carsService.carBodyCollectionByPrice(CarBodyType.COMBI, new BigDecimal(100), new BigDecimal(50)));
        Assertions.assertEquals("WRONG RANGE MIN IS BIGGER THAN MAX", e.getMessage());

    }

    @Test()
    @DisplayName("Test -> shouldThrowExceptionForWrongArgsInMethod")
    public void shouldThrowExceptionForNullArgsInMethod2() {

        MyUncheckedException e = Assertions.assertThrows(MyUncheckedException.class, () -> carsService.carBodyCollectionByPrice(null, new BigDecimal(1), new BigDecimal(50)));
        Assertions.assertEquals("ARGUMENT IN carBodyCollectionByPrice is NULL", e.getMessage());

    }

    @Test()
    @DisplayName("Test -> shouldReturnCorrectNUmberOfCarsWithProperEngine")
    public void shouldReturnCorrectNUmberOfCarsWithProperEngine() {

        System.out.println(carsService.getCarSet().size());
        Assertions.assertEquals(2, carsService.getCarSet().size());
    }

    @Test()
    @DisplayName("Test -> shouldReturnWrongNumberOfCars")
    public void shouldReturnWrongNumberOfCars() {

        System.out.println(carsService.getCarSet().size());
        Assertions.assertNotEquals(4, carsService.getCarSet().size());
    }



    @Mock
    private CarsService carsServiceSetMock;

    @Test
    @DisplayName("Test - test mock carService add new car")
    public void mockCarService() {
//      verify that the add method was called with argument 'Car'
        carsServiceSetMock.addItem(Car.builder().model("AA").build());
        Mockito.verify(carsServiceSetMock).addItem(Car.builder().model("AA").build());
        Assert.assertEquals(0,carsServiceSetMock.getCarSet().size());


    }

    @Spy
    private CarsService carsServiceSetSpy;

    @Test
    @DisplayName("Test - test spy carService add new car")
    public void spyCarsService() {
//      verify that the add method was called with argument 'Car'
        carsServiceSetSpy.addItem(Car.builder().model("AA").build());
        Mockito.verify(carsServiceSetSpy).addItem(Car.builder().model("AA").build());
        Assert.assertEquals(1,carsServiceSetSpy.getCarSet().size());

    }


}
