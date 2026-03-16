package com.crmsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;
    private Long courseId;
    private Course course;


    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", commentary='" + commentary + '\'' +
                ", phone='" + phone + '\'' +
                ", handled=" + handled +
                ", courseId=" + courseId +
                ", course=" + course +
                '}';
    }
}
