package com.example.demo.entity;

public class Container {

    private String ID;
    private String Image;
    private String Names;
    private String RunningFor;
    private String State;
    private String Status;

    public Container() {
    }

    public Container(String ID, String Image, String Names, String RunningFor, String State, String Status) {
        this.ID = ID;
        this.Image = Image;
        this.Names = Names;
        this.RunningFor = RunningFor;
        this.State = State;
        this.Status = Status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String Names) {
        this.Names = Names;
    }

    public String getRunningFor() {
        return RunningFor;
    }

    public void setRunningFor(String RunningFor) {
        this.RunningFor = RunningFor;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
