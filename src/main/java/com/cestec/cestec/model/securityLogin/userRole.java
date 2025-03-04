package com.cestec.cestec.model.securityLogin;

public enum userRole {
    ADMIN("admin"),
    SUPER("super"),
    COORD("coord"),
    DIREC("direc"),
    USER("user");

    private String role;

    userRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
