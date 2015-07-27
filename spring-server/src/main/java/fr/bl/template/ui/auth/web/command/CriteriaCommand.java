package fr.bl.template.ui.auth.web.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CriteriaCommand {

	@Getter @Setter private String search;
	@Getter @Setter private String staff;
	@Getter @Setter private String superUser;
	@Getter @Setter private String active;
	
	public Boolean hasStaffFilter() {
		return ("TRUE".equals(this.staff) || "FALSE".equals(this.staff));
	}
	public Boolean hasSuperUserFilter() {
		return ("TRUE".equals(this.superUser) || "FALSE".equals(this.superUser));
	}
	public Boolean hasActiveFilter() {
		return ("TRUE".equals(this.active) || "FALSE".equals(this.active));
	}
	
	public Boolean isStaff() {
		return "TRUE".equals(this.staff);
	}
	public Boolean isSuperUser() {
		return "TRUE".equals(this.superUser);
	}
	public Boolean isActive() {
		return "TRUE".equals(this.active);
	}
}
