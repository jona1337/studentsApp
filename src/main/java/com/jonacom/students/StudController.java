package com.jonacom.students;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudController {

    private static StudModel model;
    private static StudView view;

    private static String getStudListString(ArrayList<Student> students) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            sb.append("   ")
                    .append(i)
                    .append(" ")
                    .append(student.toString())
                    .append("\n");
        }
        return sb.toString();

    }

    private static String getGroupListString(ArrayList<StudGroup> groups) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < groups.size(); i++) {
            StudGroup group = groups.get(i);
            sb.append("   ")
                    .append(i)
                    .append(" ")
                    .append(group.toString())
                    .append("\n");
        }
        return sb.toString();
    }

    /*--------------------*/
    
    private static void commandHandler(String command) {

        String[] words = command.split(" ");

        if (words.length > 0) {
            if (words[0].equals("show")) {

                if (words.length > 1) {
                    if (words[1].equals("student")) {

                        if (words.length > 2) {
                            showStudentCommand(words);
                        } else {
                            showStudentsListCommand(words);
                        }

                    } else if (words[1].equals("group")) {

                        if (words.length > 2) {
                            showGroupCommand(words);
                        } else {
                            showGroupsListCommand(words);
                        }

                    }
                }

            } else if (words[0].equals("add")) {

                if (words.length > 1) {
                    if (words[1].equals("student")) {
                        addStudentCommand(words);
                    } else if (words[1].equals("group")) {
                        addGroupCommand(words);
                    }
                }

            } else if (words[0].equals("delete")) {
                
                if (words.length > 1) {
                    if (words[1].equals("student")) {
                        deleteStudentCommand(words);
                    } else if (words[1].equals("group")) {
                        deleteGroupCommand(words);
                    }
                }
                
            } else if (words[0].equals("deleteall")) {

                deleteAllCommand(words);

            } else if (words[0].equals("help")) {

                view.printCommands();
                view.printDateInfo();
            }
            
        }

    }

    /*--------------------*/
    
    private static void deleteAllCommand(String[] args) {
        model.deleteAll();
        view.message("All objects was deleted.");
    }
    
    /*-----*/
    
    private static void showStudentCommand(String[] args) {

        try {

            int studentNumber = Integer.valueOf(args[2]);
            int studentsCount = model.getStudentsCount();

            if (studentNumber >= 0 && studentNumber < studentsCount) {

                Student student = model.getStudentByNumber(studentNumber);
                StringBuilder sb = new StringBuilder("Student number ");
                sb.append(studentNumber).
                        append(":\n").
                        append("   ").
                        append(student.toString());
                view.message(sb.toString());

            } else {
                view.message("Invalid student number!");
            }

        } catch (NumberFormatException e) {
            view.message("Invalid number format!");
        }

    }

    private static void showStudentsListCommand(String[] args) {

        ArrayList<Student> students = model.getStudents();

        StringBuilder sb = new StringBuilder();
        if (students.size() == 0) {
            sb.append("No students.");
        } else {
            sb.append("Students list(").
                    append(students.size()).append(" elements):\n").
                    append(getStudListString(students));
        }
        view.message(sb.toString());

    }

    /*-----*/
    
    private static void showGroupCommand(String[] args) {

        try {

            int groupNumber = Integer.valueOf(args[2]);
            int groupsCount = model.getGroupsCount();

            if (groupNumber >= 0 && groupNumber < groupsCount) {

                StudGroup group = model.getGroupByNumber(groupNumber);
                StringBuilder sb = new StringBuilder("Group number ");
                sb.append(groupNumber).
                        append(":\n").
                        append("   ").
                        append(group.toString());
                view.message(sb.toString());

            } else {
                view.message("Invalid group number!");
            }

        } catch (NumberFormatException e) {
            view.message("Invalid number format!");
        }

    }

    private static void showGroupsListCommand(String[] args) {

        ArrayList<StudGroup> groups = model.getGroups();

        StringBuilder sb = new StringBuilder();
        if (groups.size() == 0) {
            sb.append("No groups.");
        } else {
            sb.append("Groups list(").
                    append(groups.size()).append(" elements):\n").
                    append(getGroupListString(groups));
        }
        view.message(sb.toString());

    }

    /*-----*/
    
    private static void addStudentCommand(String[] args) {
        if (args.length >= 4) {

            String name = args[2];

            try {

                int groupNumber = Integer.valueOf(args[3]);
                int groupsCount = model.getGroupsCount();

                if (groupNumber >= 0 && groupNumber < groupsCount) {

                    StudGroup group = model.getGroupByNumber(groupNumber);
                    
                    Date date = null;
                    if (args.length >= 5) {

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        try {
                            date = format.parse(args[4]);
                        } catch (ParseException e) {
                            view.message("Invalid date format!");
                            view.printDateInfo();
                        }

                    } else {
                        date = new Date();
                    }
                    
                    if (date != null) {
                        model.addStudent(name, group, date);
                        view.message("Student was successfully created.");
                    }

                } else {
                    view.message("Invalid group number!");
                }

            } catch (NumberFormatException e) {
                view.message("Invalid group number format!");
            }

        } else {
            view.message("Invalid command!");
        }
    }

    private static void addGroupCommand(String[] args) {
        if (args.length >= 4) {

            String name = args[2];
            String faculty = args[3];
            
            model.addGroup(name, faculty);
            view.message("Group was successfully created.");

        } else {
            view.message("Invalid command!");
        }
    }

    /*-----*/
    
    private static void deleteStudentCommand(String[] args) {
        
        if (args.length >= 3) {
            
            try {

                int studentNumber = Integer.valueOf(args[2]);
                int studentsCount = model.getStudentsCount();

                if (studentNumber >= 0 && studentNumber < studentsCount) {
                    
                    model.deleteStudent(studentNumber);
                    view.message("Student was successfully deleted.");
                    
                } else {
                    view.message("Invalid student mumber!");
                }

            } catch (NumberFormatException e) {
                view.message("Invalid student mumber!");
            }
            
        } else {
            view.message("Invalid student mumber!");
        }
        
    }
    
    private static void deleteGroupCommand(String[] args) {
        
        if (args.length >= 3) {
            
            try {

                int groupNumber = Integer.valueOf(args[2]);
                int groupsCount = model.getGroupsCount();

                if (groupNumber >= 0 && groupNumber < groupsCount) {
                    
                    model.deleteGroup(groupNumber);
                    view.message("Group was successfully deleted.");
                    
                } else {
                    view.message("Invalid group mumber!");
                }

            } catch (NumberFormatException e) {
                view.message("Invalid group mumber!");
            }
            
        } else {
            view.message("Invalid group mumber!");
        }
        
    }
    
    
    /*-----------------------*/
    
    public static void main(String[] args) throws IOException {

        model = StudModel.getInstance();
        view = StudView.getInstance();

        view.printWelcome();
        view.printCommands();
        view.printDateInfo();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String text;
        while (!(text = br.readLine()).equals("q")) {
            commandHandler(text);
        }

        view.printGoodbye();

    }

}
