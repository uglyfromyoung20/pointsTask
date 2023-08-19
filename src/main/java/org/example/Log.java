package org.example;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "point_id")
    private int pointId;
    @Column(name = "method")
    private String method;
    @Column(name = "distance")
    private double distance;
    @Column(name = "first_x")
    private double firstX;
    @Column(name = "first_y")
    private double firstY;
    @Column(name = "second_x")
    private double secondX;
    @Column(name = "second_y")
    private double secondY;
    @Column(name = "first_z")
    private double firstZ;
    @Column(name = "second_z")
    private double secondZ;
    @Column(name = "start_calc")
    private Date start;
    @Column(name = "end_calc")
    private Date end;

    public Log(String fileName, int pointId, String method, double distance, double firstX, double firstY, double secondX, double secondY, double firstZ, double secondZ, Date start, Date end) {
        this.fileName = fileName;
        this.pointId = pointId;
        this.method = method;
        this.distance = distance;
        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
        this.firstZ = firstZ;
        this.secondZ = secondZ;
        this.start = start;
        this.end = end;
    }

    public Log() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public double getFirstX() {
        return firstX;
    }

    public void setFirstX(double firstX) {
        this.firstX = firstX;
    }

    public double getFirstY() {
        return firstY;
    }

    public void setFirstY(double firstY) {
        this.firstY = firstY;
    }

    public double getSecondX() {
        return secondX;
    }

    public void setSecondX(double secondX) {
        this.secondX = secondX;
    }

    public double getSecondY() {
        return secondY;
    }

    public void setSecondY(double secondY) {
        this.secondY = secondY;
    }

    public double getFirstZ() {
        return firstZ;
    }

    public void setFirstZ(double firstZ) {
        this.firstZ = firstZ;
    }

    public double getSecondZ() {
        return secondZ;
    }

    public void setSecondZ(double secondZ) {
        this.secondZ = secondZ;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", fileName = '" + fileName + '\'' +
                ", pointId = " + pointId +
                ", method = '" + method + '\'' +
                ", distance = " + distance +
                ", firstX = " + firstX +
                ", firstY = " + firstY +
                ", secondX = " + secondX +
                ", secondY = " + secondY +
                ", firstZ=" + firstZ +
                ", secondZ = " + secondZ +
                ", start = " + start +
                ", end = " + end +
                '}' + "\n";
    }
}
