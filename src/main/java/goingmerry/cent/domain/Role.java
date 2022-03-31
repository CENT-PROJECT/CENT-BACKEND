package goingmerry.cent.domain;

public enum Role {

    USER("ROLE_USER"),
    LEADER("ROLE_LEADER"),
    ADMIN("ROLE_ADMIN");

    private String grantedAuthority;

    Role(String grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public String getGrantedAuthority() {
        return grantedAuthority;
    }
}

