package com.jonacom.students;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudController {

    private static final StudController instance = new StudController();

    private static StudModel model;
    private static StudView view;

    private StudController() {
    }

    public static StudController getInstance() {
        return instance;
    }

    /*--------------------*/

    public void addModel(StudModel model) {
        StudController.model = model;
    }

    public void addView(StudView view) {
        StudController.view = view;
    }

    public void start() {
        view.printWelcome();
        view.printCommands();
        view.printDateInfo();
        view.listenConsole();
        view.printGoodbye();
    }

    /*--------------------*/

    private String[] getCommandNextStepArgs(String[] args) {

        //just delete first argument

        if (args.length < 1) return args;

        String[] newArgs = new String[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            newArgs[i - 1] = args[i];
        }
        return newArgs;

    }


    public void onUserCommand(String command) {

        String[] args = command.split(" ");

        if (args.length < 1) return;

        String mainCommand = args[0];
        args = getCommandNextStepArgs(args);

        switch (mainCommand) {
            case "show":
                showCommand(args);
                break;
            case "add":
                addCommand(args);
                break;
            case "delete":
                deleteCommand(args);
                break;
            case "set":
                setDataCommand(args);
                break;
            case "deleteall":
                deleteAllCommand(args);
                break;
            case "help":
                helpCommand(args);
                break;
            default:
                view.message("Invalid command!");
                break;
        }

    }

    /*--------------------*/

    private String getStudListString(ArrayList<Student> students) {

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

    private String getGroupListString(ArrayList<StudGroup> groups) {
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

    private void helpCommand(String[] args) {
        view.printCommands();
        view.printDateInfo();
    }


    private void showCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write object type!(student or group)");
            return;
        }

        String objectType = args[0];
        args = getCommandNextStepArgs(args);

        switch (objectType) {
            case "student":
                if (args.length > 0) {
                    showStudentCommand(args);
                } else {
                    showStudentsListCommand(args);
                }
                break;
            case "group":
                if (args.length > 0) {
                    showGroupCommand(args);
                } else {
                    showGroupsListCommand(args);
                }
                break;
            default:
                view.message("Invalid object type! Must be student or group.");
                break;
        }


    }

    private void showStudentCommand(String[] args) {

        if (args.length < 1) return;

        try {

            int studentNumber = Integer.valueOf(args[0]);

            Student student = model.getStudentByNumber(studentNumber);
            if (student != null) {
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

    private void showStudentsListCommand(String[] args) {

        ArrayList<Student> students = model.getStudents();

        StringBuilder sb = new StringBuilder();
        if (students.isEmpty()) {
            sb.append("No students.");
        } else {
            sb.append("Students list(").
                    append(students.size()).append(" elements):\n").
                    append(getStudListString(students));
        }
        view.message(sb.toString());

    }

    private void showGroupCommand(String[] args) {

        if (args.length < 1) return;

        try {

            int groupNumber = Integer.valueOf(args[0]);
            StudGroup group = model.getGroupByNumber(groupNumber);
            if (group != null) {

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

    private void showGroupsListCommand(String[] args) {

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

    private void addCommand(String[] args) {

        int argsCount = args.length;
        if (argsCount < 1) {
            view.message("Write object type!(student or group)");
            return;
        }

        String objectType = args[0];
        args = getCommandNextStepArgs(args);

        switch (objectType) {
            case "student":
                addStudentCommand(args);
                break;
            case "group":
                addGroupCommand(args);
                break;
            default:
                view.message("Invalid object type! Must be student or group.");
                break;
        }
    }

    private void addStudentCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write student name!");
            return;
        }

        String name = args[0];
        args = getCommandNextStepArgs(args);

        if (args.length < 1) {
            view.message("Write group number!");
            return;
        }

        try {

            int groupNumber = Integer.valueOf(args[0]);
            StudGroup group = model.getGroupByNumber(groupNumber);

            args = getCommandNextStepArgs(args);

            if (group != null) {

                Date date = new Date();

                if (args.length > 0) {

                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        date = format.parse(args[0]);
                    } catch (ParseException e) {
                        view.message("Invalid date format!");
                        view.printDateInfo();
                        return;
                    }
                }

                model.addStudent(name, group, date);
                view.message("Student was successfully created.");


            } else {
                view.message("Invalid group number!");
            }
        } catch (NumberFormatException e) {
            view.message("Invalid group number format!");
        }


    }

    private void addGroupCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write group name!");
            return;
        }

        String name = args[0];
        args = getCommandNextStepArgs(args);

        if (args.length < 1) {
            view.message("Write group faculty!");
            return;
        }

        String faculty = args[0];
        args = getCommandNextStepArgs(args);

        model.addGroup(name, faculty);
        view.message("Group was successfully created.");

    }

    /*-----*/

    private void deleteAllCommand(String[] args) {
        model.deleteAll();
        view.message("All objects was deleted.");
    }

    private void deleteCommand(String[] args) {

        int argsCount = args.length;
        if (argsCount < 1) {
            view.message("Write object type!(student or group)");
            return;
        }

        String objectType = args[0];
        args = getCommandNextStepArgs(args);

        switch (objectType) {
            case "student":
                deleteStudentCommand(args);
                break;
            case "group":
                deleteGroupCommand(args);
                break;
            default:
                view.message("Invalid object type! Must be student or group.");
                break;
        }

    }

    private void deleteStudentCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write student number!");
            return;
        }

        try {

            int studentNumber = Integer.valueOf(args[0]);
            Student student = model.getStudentByNumber(studentNumber);

            args = getCommandNextStepArgs(args);

            if (student != null) {
                model.deleteStudent(studentNumber);
                view.message("Student was successfully deleted.");
            } else {
                view.message("Invalid student mumber!");
            }

        } catch (NumberFormatException e) {
            view.message("Invalid student mumber format!");
        }


    }

    private void deleteGroupCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write group number!");
            return;
        }

        try {

            int groupNumber = Integer.valueOf(args[0]);
            StudGroup group = model.getGroupByNumber(groupNumber);

            args = getCommandNextStepArgs(args);

            if (group != null) {

                model.deleteGroup(groupNumber);
                view.message("Group was successfully deleted.");

            } else {
                view.message("Invalid group mumber!");
            }

        } catch (NumberFormatException e) {
            view.message("Invalid group mumber format!");
        }


    }

    /*-----*/

    private void setDataCommand(String[] args) {

        int argsCount = args.length;
        if (argsCount < 1) {
            view.message("Write object type!(student or group)");
            return;
        }

        String objectType = args[0];
        args = getCommandNextStepArgs(args);

        switch (objectType) {
            case "student":
                setStudentDataCommand(args);
                break;
            case "group":
                setGroupDataCommand(args);
                break;
            default:
                view.message("Invalid object type! Must be student or group.");
                break;
        }

    }

    private void setStudentDataCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write student number!");
            return;
        }

        int studentNumber = 0;
        try {
            studentNumber = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            view.message("Invalid student mumber format!");
            return;
        }

        Student student = model.getStudentByNumber(studentNumber);
        if (student == null) {
            view.message("Invalid student mumber!");
            return;
        }

        args = getCommandNextStepArgs(args);

        if (args.length < 1) {
            view.message("Write data name! Must be name, group or date.");
            return;
        }

        String dataKey = args[0];
        args = getCommandNextStepArgs(args);

        switch (dataKey) {
            case "name":

                if (args.length < 1) {
                    view.message("Write student name!");
                    return;
                }

                model.setStudentName(studentNumber, args[0]);
                break;

            case "group":

                if (args.length < 1) {
                    view.message("Write group number!");
                    return;
                }

                int groupNumber = 0;

                try {
                    groupNumber = Integer.valueOf(args[0]);
                } catch (NumberFormatException e) {
                    view.message("Invalid group mumber format!");
                    return;
                }

                args = getCommandNextStepArgs(args);

                StudGroup group = model.getGroupByNumber(groupNumber);
                if (group == null) {
                    view.message("Invalid group number!");
                    return;
                }

                model.setStudentGroup(studentNumber, groupNumber);
                break;

            case "date":

                Date date = new Date();

                if (args.length > 0) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        date = format.parse(args[0]);
                    } catch (ParseException e) {
                        view.message("Invalid date format!");
                        view.printDateInfo();
                        return;
                    }
                }

                model.setStudentEnrollmentDate(studentNumber, date);
                break;

            default:
                view.message("Invalid data name! Must be name, group or date.");
                break;
        }
    }

    private void setGroupDataCommand(String[] args) {

        if (args.length < 1) {
            view.message("Write group number!");
            return;
        }

        int groupNumber = 0;
        try {
            groupNumber = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            view.message("Invalid group number format!");
            return;
        }

        StudGroup group = model.getGroupByNumber(groupNumber);
        if (group == null) {
            view.message("Invalid group number!");
            return;
        }

        args = getCommandNextStepArgs(args);

        if (args.length < 1) {
            view.message("Write data name! Must be name or faculty.");
            return;
        }

        String dataKey = args[0];
        args = getCommandNextStepArgs(args);

        switch (dataKey) {
            case "name":

                if (args.length < 1) {
                    view.message("Write group name!");
                    return;
                }

                model.setGroupName(groupNumber, args[0]);
                break;

            case "faculty":

                if (args.length < 1) {
                    view.message("Write group faculty name!");
                    return;
                }

                model.setGroupFaculty(groupNumber, args[0]);
                break;

            default:
                view.message("Invalid data name! Must be name or faculty.");
                break;
        }

    }

}
