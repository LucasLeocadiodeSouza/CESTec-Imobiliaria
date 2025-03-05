package com.cestec.cestec.model.securityLogin;

public enum userRole {
    USER("user"),
    SALER("saler"),
    ADMIN("admin"),
    SUPER("super"),
    COORD("coord"),
    DIREC("direc");

    private String role;

    userRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
