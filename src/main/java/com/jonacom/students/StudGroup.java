
package com.jonacom.students;

import java.io.Serializable;

public class StudGroup implements Serializable {
    
    private String name;
    private String faculty;

    public StudGroup(String name, String faculty) {
        this.name = name;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(getName())
                    .append(" ")
                    .append(getFaculty());
        
        return  sb.toString();
        
    }
    
}
