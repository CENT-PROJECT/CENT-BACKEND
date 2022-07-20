package goingmerry.cent.user;

public enum Role {

    ROLE_USER("USER"),
    UNVERIFIED_USER("UNVERIFIED_USER"),
    ROLE_LEADER("LEADER"),
    ROLE_ADMIN("ADMIN");

    private String grantedAuthority;

    Role(String grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    public String getGrantedAuthority() {
        return grantedAuthority;
    }
}

