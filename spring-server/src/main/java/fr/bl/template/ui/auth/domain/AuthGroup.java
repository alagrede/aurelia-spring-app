package fr.bl.template.ui.auth.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "auth_group")
@ToString(exclude = "permissions")
@EqualsAndHashCode(exclude = "permissions")
@NoArgsConstructor
public class AuthGroup {

	@Id @GeneratedValue
	@Getter @Setter private Long id;
	
	@NotNull
	@NotEmpty
	@Size(max = 80)
	@Column(nullable = false, unique = true)
	@Getter @Setter private String name;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="auth_group_permissions", 
				joinColumns = @JoinColumn(name="group_id"), 
				inverseJoinColumns = @JoinColumn(name="permission_id"))
	@Getter @Setter private Set<AuthPermission> permissions = new HashSet<AuthPermission>();


	@Transient @NotNull @NotEmpty
	@Getter @Setter private String selectedPermissions = "";
	
	public void loadSelectedPermissions() {
		for (AuthPermission p : this.getPermissions()) {
			this.selectedPermissions += (p.getId() + ",");
		}
		if (this.selectedPermissions.endsWith(",")) {
			this.selectedPermissions = this.selectedPermissions.substring(0, this.selectedPermissions.length()-1);
		}
	}

	
}
