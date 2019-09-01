package com.app.model;


import com.app.enums.TyreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Wheel {

    private String model;
    private int size;
    private TyreType tyreType;


}
