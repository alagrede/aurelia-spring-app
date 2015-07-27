package fr.bl.template.ui.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "auth_permission")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class AuthPermission {

		@Id @GeneratedValue
		@Getter @Setter private Long id;
		
		@NotNull
		@NotEmpty
		@Size(max = 100)
		@Column(nullable = false)
		@Getter @Setter private String name;

		@NotNull
		@NotEmpty
		@Column(nullable = false)
		@Getter @Setter private Integer contentTypeId;

		@NotNull
		@NotEmpty
		@Size(max = 100)
		@Column(nullable = false, unique = true)
		@Getter @Setter private String codename;
	
}
