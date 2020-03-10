package main;

import base.DBService;
import base.dataSets.User;
import dbService.DBServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println(status);

        dbService.save(new UserDataSet("First"));

        dataSet = dbService.readByName("First");
        System.out.println(dataSet);

        dbService.save(new UserDataSet("Second"));

        UserDataSet dataSet = dbService.read(2);
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();
    }
}