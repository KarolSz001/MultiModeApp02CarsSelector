package com.app.model;


import com.app.enums.CarBodyColor;
import com.app.enums.CarBodyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CarBody {

    private CarBodyColor color;
    private CarBodyType type;
    private List<String> components;

}
