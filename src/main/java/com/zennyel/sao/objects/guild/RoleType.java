package com.zennyel.sao.objects.guild;

public enum RoleType {

    OWNER("Fundador"),
    CAPTAIN("Capitão"),
    MEMBER("Membro");

    private String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }
}
