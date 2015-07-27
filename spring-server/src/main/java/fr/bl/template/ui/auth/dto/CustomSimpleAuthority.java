package fr.bl.template.ui.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

@NoArgsConstructor
public class CustomSimpleAuthority implements GrantedAuthority {


	@Setter private String authority;

    public CustomSimpleAuthority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    @Override public String getAuthority() {
        return authority;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof CustomSimpleAuthority) {
            return authority.equals(((CustomSimpleAuthority) obj).authority);
        }

        return false;
    }

    public int hashCode() {
        return this.authority.hashCode();
    }

    public String toString() {
        return this.authority;
    }

}
