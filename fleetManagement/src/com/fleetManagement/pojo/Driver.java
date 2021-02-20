package com.fleetManagement.pojo;

import java.time.LocalDate;

import com.fleetManagement.pojo.anno.TableFiled;

//˾��

public class Driver {

    @TableFiled(index = 0, name = "����")
    private String name;
    @TableFiled(index = 1, name = "�Ա�")
    private char sex;
    @TableFiled(index = 2, name = "��������")
    private LocalDate birthday;
    @TableFiled(index = 3, name = "��ַ")
    private String address;
    // ������֤ʱ��
    @TableFiled(index = 4, name = "������֤ʱ��")
    private LocalDate certificateTime;
    // ִ�պ���
    @TableFiled(index = 5, name = "ִ�պ���")
    private String licenseNumber;
    // ׼�ݳ���
    @TableFiled(index = 6, name = "׼�ݳ���")
    private String drivingLicense;
    // ��Ч��ʼʱ��
    @TableFiled(index = 7, name = "��Ч��ʼʱ��")
    private LocalDate startTime;
    // ��Ч����ʱ��
    @TableFiled(index = 8, name = "��Ч����ʱ��")
    private LocalDate endTime;

    public Driver(){}

    public Driver(String name, char sex, LocalDate birthday, String address, LocalDate certificateTime, String licenseNumber, String drivingLicense, LocalDate startTime, LocalDate endTime) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
        this.certificateTime = certificateTime;
        this.licenseNumber = licenseNumber;
        this.drivingLicense = drivingLicense;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCertificateTime() {
        return certificateTime;
    }

    public void setCertificateTime(LocalDate certificateTime) {
        this.certificateTime = certificateTime;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "{\r\n" +
                "name='" + name + '\'' +
                ", \r\n sex=" + sex +
                ", \r\n birthday=" + birthday +
                ", \r\n address='" + address + '\'' +
                ", \r\n certificateTime=" + certificateTime +
                ", \r\n licenseNumber='" + licenseNumber + '\'' +
                ", \r\n drivingLicense='" + drivingLicense + '\'' +
                ", \r\n startTime=" + startTime +
                ", \r\n endTime=" + endTime +
                "\r\n}";
    }
}
