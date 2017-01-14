
package com.jonacom.students;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Serializable {
    
    private String name;
    private StudGroup group;
    private Date enrollmentDate;

    public Student(String name, StudGroup group) {
        this.name = name;
        this.group = group;
        this.enrollmentDate = new Date();
    }
    
    public Student(String name, StudGroup group, Date enrollmentDate) {
        this.name = name;
        this.group = group;
        this.enrollmentDate = enrollmentDate;
    }

    public String getName() {
        return name;
    }

    public StudGroup getGroup() {
        return group;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(StudGroup group) {
        this.group = group;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateStr = sdf.format(getEnrollmentDate());
        
        sb.append(getName())
                    .append(" ")
                    .append(getGroup().getName())
                    .append(" ")
                    .append(dateStr);
        
        return  sb.toString();
        
    }
    
    
}
