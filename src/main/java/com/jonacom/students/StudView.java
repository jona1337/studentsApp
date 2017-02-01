package com.jonacom.students;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StudView {

    private static final String START_MESSAGE = "Students and groups app. Welcome.";
    private static final String STOP_MESSAGE = "Goodbye.";

    private static final String[] COMMANDS_INFO = {
        "show [object type]",
        "show [object type] [number in the list]",
        "add student [name] [group number]",
        "add student [name] [group number] [enrollment date]",
        "add group [name] [faculty]",
        "delete [object type] [number in the list]",
        "set [object type] [number in the list] [key] [value]",
        "deleteall",
        "help",
        "q",
    };

    private static final String DATE_INFO = "dd.mm.yyyy";
    
    private static final StudView instance = new StudView();
    private static StudController controller;


    private StudView() {}
    
    public static StudView getInstance() {
        return instance;
    }

    public void addController(StudController controller) {
        this.controller = controller;
    }
    
    public void listenConsole() {
        
        Scanner sc = new Scanner(System.in);
      
        String args;

        while (!(args = sc.nextLine()).equals("q")) {
            controller.onUserCommand(args);
        }
        
    }

    /*--------------------*/
    
    public void message(String data) {
        System.out.println(data);
    }

    public void printWelcome() {
        message(START_MESSAGE);
    }

    public void printGoodbye() {
        message(STOP_MESSAGE);
    }

    public void printCommands() {
        StringBuilder sb = new StringBuilder("Available commands:\n");
        for (String str : COMMANDS_INFO) {
            sb.append("   ").append(str).append("\n");
        }
        message(sb.toString());
    }

    public void printDateInfo() {
        StringBuilder sb = new StringBuilder("Date format: ");
        sb.append(DATE_INFO);
        message(sb.toString());
    }

}
