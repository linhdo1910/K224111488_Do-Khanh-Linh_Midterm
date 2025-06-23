package models;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String username;
    private String password;
    private int typeOfAccount;

    // Constructors
    public Account() {}
    public Account(int id, String username, String password, int typeOfAccount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.typeOfAccount = typeOfAccount;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getTypeOfAccount() { return typeOfAccount; }
    public void setTypeOfAccount(int typeOfAccount) { this.typeOfAccount = typeOfAccount; }
}

