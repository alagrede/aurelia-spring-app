package fr.bl.template.ui.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NoArgsConstructor
public class OneTimeToken {

	@Id @GeneratedValue
	@Getter @Setter private Long id;

	
	@NotNull
	@Column(unique = true)
	@Getter @Setter private Long userId;
	
	@NotNull
	@NotEmpty
	@Size(max = 200)
	@Column(nullable = false)
	@Getter @Setter private String token;
	
}
