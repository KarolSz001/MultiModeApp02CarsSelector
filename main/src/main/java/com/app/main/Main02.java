package com.app.main;


import com.app.service.ControlAppService;

public class Main02 {
    public static void main(String[] args) {
        final String appName = "CarsApp02 v1.01 31.08.2019 _K.Szot";
        ControlAppService ca2 = new ControlAppService();
        System.out.println(appName);
        ca2.runApp();
    }
}
