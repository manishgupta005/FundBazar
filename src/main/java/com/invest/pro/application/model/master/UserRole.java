package com.invest.pro.application.model.master;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 7/7/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public enum UserRole {

    GUEST("G"),
    REGISTERED("R"),
    ADMIN("A");

    private final String codeType;

    private UserRole(String codeType){
            this.codeType = codeType;
    }

    public boolean isGuest(){
      return  GUEST.codeType.equals(getCodeType());
    }

    public boolean isRegistered(){
       return REGISTERED.codeType.equals(getCodeType());
    }

    public boolean isAdmin(){
        return ADMIN.codeType.equals(getCodeType());
    }

    public String getCodeType() {
        return codeType;
    }
}
