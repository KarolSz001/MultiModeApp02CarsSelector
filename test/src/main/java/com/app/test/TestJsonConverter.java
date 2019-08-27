package com.app.test;
import com.app.exception.MyUncheckedException2;
import com.app.model.Car2;
import com.app.utility.CarJsonConverter2;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class TestJsonConverter {

    private final String fileName = "App02jsonTestFile21.json";

    @Test(expected = MyUncheckedException2.class)
    @DisplayName("Test 1 -> shouldThrowExceptionForNullArgument")
    public void shouldThrowExceptionForNullArgument() {
        CarJsonConverter2 carsJson = new CarJsonConverter2 (fileName);
        carsJson.toJson(null);
    }

    @Test(expected = MyUncheckedException2.class)
    @DisplayName("Test 2 -> shouldThrowExceptionForNullFile")
    public void shouldThrowExceptionForNullFile() {
        CarJsonConverter2  carsJson = new CarJsonConverter2 (null);
        Car2 car = new Car2();
        carsJson.toJson(car);
    }

    @Test
    @DisplayName("Test 3 -> shouldThrowExceptionForNullFile2")
    public void shouldThrowExceptionForNullFile2() {
        MyUncheckedException2 e = Assertions.assertThrows(MyUncheckedException2.class, ()-> new CarJsonConverter2 (null));
        Assertions.assertEquals( "name file is null",e.getMessage());
    }

}
