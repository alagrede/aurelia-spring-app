package fr.bl.template.ui.auth.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "auth_user")
@ToString(exclude = {"permissions", "groups"})
@EqualsAndHashCode(exclude = {"permissions", "groups"})
@NoArgsConstructor
public class AuthUser {

	
	@Id @GeneratedValue
	@Getter @Setter private Long id;
	
	@NotNull
	@Size(max = 50)
	@Column(nullable = false, unique = true)
	@Getter @Setter private String username;
	
	//@NotNull
	@Size(max = 250)
	@Column(nullable = false)
	@Getter @Setter private String password;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter private Date lastLogin;

	@Column
    @Size(max = 70)
    @Getter @Setter private String firstname;
	
	@Column
	@Size(max = 70)
    @Getter @Setter private String lastname;

	@Column(unique = true)
	@Size(max = 70)
	@Getter @Setter private String email;

	@Column
	@Getter @Setter private Boolean active = false;

	@Column
	@Getter @Setter private Boolean staff = false;

	@Column
	@Getter @Setter private Boolean superUser = false;
	
	@Column
	@Temporal(TemporalType.DATE)
    @Getter @Setter private Date dateJoined;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="auth_user_user_permissions", 
				joinColumns = @JoinColumn(name="user_id"), 
				inverseJoinColumns = @JoinColumn(name="permission_id"))
	@Getter @Setter private Set<AuthPermission> permissions = new HashSet<AuthPermission>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="auth_user_groups", 
				joinColumns = @JoinColumn(name="user_id"), 
				inverseJoinColumns = @JoinColumn(name="group_id"))
	@Getter @Setter private Set<AuthGroup> groups = new HashSet<AuthGroup>();
	
	
	@Transient
	@Getter @Setter private List<String> selectedGroups2 = new ArrayList<String>();
	
	@Transient
	@Getter @Setter private List<String> selectedPermissions2 = new ArrayList<String>();
	
	
	@Transient
	@Getter @Setter private String selectedGroups = "";
	
	public void loadSelectedGroups() {
		for (AuthGroup g : this.getGroups()) {
			this.selectedGroups2.add(g.getId().toString());
			this.selectedGroups += (g.getId() + ",");
		}
		if (this.selectedGroups.endsWith(",")) {
			this.selectedGroups = this.selectedGroups.substring(0, this.selectedGroups.length()-1);
		}
	}
	
	@Transient
	@Getter @Setter private String selectedPermissions = "";
	
	public void loadSelectedPermissions() {
		for (AuthPermission p : this.getPermissions()) {
			this.selectedPermissions2.add(p.getId().toString());
			this.selectedPermissions += (p.getId() + ",");
		}
		if (this.selectedPermissions.endsWith(",")) {
			this.selectedPermissions = this.selectedPermissions.substring(0, this.selectedPermissions.length()-1);
		}
	}
	
	
}
