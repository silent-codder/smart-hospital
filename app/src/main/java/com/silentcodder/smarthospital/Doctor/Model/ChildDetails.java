package com.silentcodder.smarthospital.Doctor.Model;

public class ChildDetails {
    String ChildName,ChildDOB,ChildGender,ChildWeight,FileNumber,ParentId;

    public ChildDetails() {
    }

    public ChildDetails(String childName, String childDOB, String childGender, String childWeight, String fileNumber, String parentId) {
        ChildName = childName;
        ChildDOB = childDOB;
        ChildGender = childGender;
        ChildWeight = childWeight;
        FileNumber = fileNumber;
        ParentId = parentId;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }

    public String getChildDOB() {
        return ChildDOB;
    }

    public void setChildDOB(String childDOB) {
        ChildDOB = childDOB;
    }

    public String getChildGender() {
        return ChildGender;
    }

    public void setChildGender(String childGender) {
        ChildGender = childGender;
    }

    public String getChildWeight() {
        return ChildWeight;
    }

    public void setChildWeight(String childWeight) {
        ChildWeight = childWeight;
    }

    public String getFileNumber() {
        return FileNumber;
    }

    public void setFileNumber(String fileNumber) {
        FileNumber = fileNumber;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }
}
