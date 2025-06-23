package models;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private int isCalled;

    public Customer() {
    }

    public Customer(int id, String name, String phone, int isCalled) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.isCalled = isCalled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsCalled() {
        return isCalled;
    }

    public void setIsCalled(int isCalled) {
        this.isCalled = isCalled;
    }
}
