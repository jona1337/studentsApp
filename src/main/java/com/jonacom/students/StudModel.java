/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jonacom.students;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Johny
 */
public class StudModel {

    private static final StudModel instance = new StudModel();

    private ArrayList<Student> students;
    private ArrayList<StudGroup> groups;

    private void createStudents() {

        ObjectInputStream in = null;
        try {
            File file = new File("students.txt");
            if (file.exists()) {
                in = new ObjectInputStream(new FileInputStream(file));
                students = (ArrayList<Student>) in.readObject();
            }

        } catch (IOException e) {
            e.printStackTrace();
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

            File file = new File("students.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

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

    private void createGroups() {

        ObjectInputStream in = null;
        try {
            File file = new File("groups.txt");
            if (file.exists()) {
                in = new ObjectInputStream(new FileInputStream(file));
                groups = (ArrayList<StudGroup>) in.readObject();
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    private void updateGroups() {

        ObjectOutputStream out = null;
        try {

            File file = new File("groups.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

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
        ArrayList<Student> newStudents = new ArrayList<Student>();
        for (Student student : students) {
            if (!(student.getGroup().equals(group))) {
                newStudents.add(student);
            }
        }
        students = newStudents;
        updateStudents();
    }
    
    /*--------------------*/
    
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
    
    public void deleteAll() {

        students.clear();
        groups.clear();

        File file = new File("groups.txt");
        if (file.exists()) {
            file.delete();
        }

        file = new File("students.txt");
        if (file.exists()) {
            file.delete();
        }

    }

    /*-----*/
    
    public Student getStudentByNumber(int number) {

        Student student = students.get(number);
        return student;

    }

    public StudGroup getGroupByNumber(int number) {

        StudGroup group = groups.get(number);
        return group;

    }

    /*-----*/
    
    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<StudGroup> getGroups() {
        return groups;
    }

    /*-----*/
    
    public int getStudentsCount() {
        return students.size();
    }

    public int getGroupsCount() {
        return groups.size();
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
    
    public void deleteStudent(int number) {

        students.remove(number);
        updateStudents();

    }

    public void deleteGroup(int number) {
        StudGroup group = getGroupByNumber(number);
        deleteStudentsByGroup(group);
        groups.remove(number);
        updateGroups();

    }

    /*-----*/
    
    public void setStudentData(int number, String key, Object value) {

        Student student = getStudentByNumber(number);

        if (key.equals("name")) {

            student.setName((String) value);

        } else if (key.equals("group")) {

            int groupNumber = (int) value;
            StudGroup group = getGroupByNumber(number);
            student.setGroup(group);

        } else if (key.equals("date")) {

            Date date = new Date();
            if (value != null) {
                date = (Date) value;
            }

            student.setEnrollmentDate(date);

        }

        updateStudents();

    }

    public void setGroupData(int number, String key, Object value) {

        StudGroup group = getGroupByNumber(number);

        if (key.equals("name")) {
            group.setName((String) value);

        } else if (key.equals("faculty")) {
            group.setFaculty((String) value);

        }

        updateGroups();

    }

}
