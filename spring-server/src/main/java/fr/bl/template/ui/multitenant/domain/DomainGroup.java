package fr.bl.template.ui.multitenant.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DomainGroup {

	@Id @GeneratedValue
	@Getter @Setter private Long id;
	
	@NotNull
	@Size(max = 50)
	@Column(nullable = false, unique = true)
	@Getter @Setter private String username;
	
	@NotNull
	@Size(max = 50)
	@Column(nullable = false)
	@Getter @Setter private String tenantName;
	

	
	
}
