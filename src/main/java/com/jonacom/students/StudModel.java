/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jonacom.students;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class StudModel {

    private static final String DATA_PACKAGE = "studentsData";
    private static final String STUDENTS_DATA_FILE = "students.txt";
    private static final String GROUPS_DATA_FILE = "groups.txt";

    private static final StudModel instance = new StudModel();

    private ArrayList<Student> students;
    private ArrayList<StudGroup> groups;

    private StudModel() {

        students = new ArrayList<Student>();
        groups = new ArrayList<StudGroup>();

        createGroups();
        createStudents();

    }

    public static StudModel getInstance() {
        return instance;
    }

    /*--------------------*/

    private String getAbsoluteDataCatalog() {
        return new File("").getAbsolutePath();
    }

    private void checkDataFilesCatalog() {
        String dataFilesCatalog = getAbsoluteDataCatalog() + "\\" + DATA_PACKAGE;
        File catalog = new File(dataFilesCatalog);
        if (!catalog.exists()) {
            catalog.mkdir();
        }
    }

    private File getStudentsDataFile() {
        checkDataFilesCatalog();
        String dataFilePatch = getAbsoluteDataCatalog() + "\\" + DATA_PACKAGE + "\\" + STUDENTS_DATA_FILE;
        File file = new File(dataFilePatch);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private File getGroupsDataFile() {
        checkDataFilesCatalog();
        String dataFilePatch = getAbsoluteDataCatalog() + "\\" + DATA_PACKAGE + "\\" + GROUPS_DATA_FILE;
        File file = new File(dataFilePatch);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void createStudents() {

        ObjectInputStream in = null;
        try {
            File file = getStudentsDataFile();

            in = new ObjectInputStream(new FileInputStream(file));
            students = (ArrayList<Student>) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void createGroups() {

        ObjectInputStream in = null;
        try {
            File file = getGroupsDataFile();

            in = new ObjectInputStream(new FileInputStream(file));
            groups = (ArrayList<StudGroup>) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    private void updateStudents() {

        ObjectOutputStream out = null;
        try {

            File file = getStudentsDataFile();

            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(students);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void updateGroups() {

        ObjectOutputStream out = null;
        try {

            File file = getGroupsDataFile();

            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(groups);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void deleteStudentsByGroup(StudGroup group) {
        ArrayList<Student> newStudents = new ArrayList<>();
        for (Student student : students) {
            if (!(student.getGroup().equals(group))) {
                newStudents.add(student);
            }
        }
        students = newStudents;
        updateStudents();
    }
    
    /*--------------------*/
    
    public int getStudentsCount() {
        return students.size();
    }

    public int getGroupsCount() {
        return groups.size();
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<StudGroup> getGroups() {
        return groups;
    }
    
    public Student getStudentByNumber(int number) {

        int studentsCount = getStudentsCount();
        if (number >= 0 && number < studentsCount) {
            Student student = students.get(number);
            return student;
        }
        return null;

    }

    public StudGroup getGroupByNumber(int number) {

        int groupsCount = getGroupsCount();
        if (number >= 0 && number < groupsCount) {
            StudGroup group = groups.get(number);
            return group;
        }
        return null;

    }

    /*--------------------*/
    
    public void addStudent(String name, StudGroup group, Date enrollmentDate) {
        Student student = new Student(name, group, enrollmentDate);
        students.add(student);
        updateStudents();
    }

    public void addStudent(String name, StudGroup group) {

        addStudent(name, group, new Date());

    }

    public void addGroup(String name, String faculty) {

        StudGroup group = new StudGroup(name, faculty);
        groups.add(group);
        updateGroups();

    }

    /*-----*/
    
    public void deleteAll() {

        students.clear();
        groups.clear();
        updateGroups();
        updateStudents();
        
    }
    
    public void deleteStudent(int number) {
        
        Student student = getStudentByNumber(number);
        if (student != null) {
            students.remove(number);
            updateStudents();
        }

    }

    public void deleteGroup(int number) {
        
        StudGroup group = getGroupByNumber(number);
        if (group != null) {
            deleteStudentsByGroup(group);
            groups.remove(number);
            updateGroups();
        }

    }

    /*-----*/
    
    public void setStudentName(int number, String name) {
        
        Student student = getStudentByNumber(number);
        if (student != null) {
            student.setName(name);
            updateStudents();
        }
        
    }
    
    public void setStudentGroup(int number, int groupNumber) {
        
        Student student = getStudentByNumber(number);
        if (student != null) {
            StudGroup group = getGroupByNumber(groupNumber);
            if (group != null) {
                student.setGroup(group);
                updateStudents();
            }
        }
        
    }
    
    public void setStudentEnrollmentDate(int number, Date date) {
        
        Student student = getStudentByNumber(number);
        if (student != null) {
            student.setEnrollmentDate(date);
            updateStudents();
        }
        
    }

    /*-----*/
    
    public void setGroupName(int number, String name) {
        
        StudGroup group = getGroupByNumber(number);
        if (group != null) {
            group.setName(name);
            updateGroups();
        }
        
    }
    
    public void setGroupFaculty(int number, String faculty) {
        
        StudGroup group = getGroupByNumber(number);
        if (group != null) {
            group.setFaculty(faculty);
            updateGroups();
        }
        
    }

}
