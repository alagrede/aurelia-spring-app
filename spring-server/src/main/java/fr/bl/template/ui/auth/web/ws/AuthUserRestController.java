package fr.bl.template.ui.auth.web.ws;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.bl.template.ui.auth.dao.AuthGroupDao;
import fr.bl.template.ui.auth.dao.AuthPermissionDao;
import fr.bl.template.ui.auth.dao.AuthUserDao;
import fr.bl.template.ui.auth.domain.AuthGroup;
import fr.bl.template.ui.auth.domain.AuthPermission;
import fr.bl.template.ui.auth.domain.AuthUser;
import fr.bl.template.ui.auth.web.command.ChangePasswordCommand;
import fr.bl.template.ui.auth.web.validator.ChangePasswordValidator;
import fr.bl.template.ui.util.PaginationData;
import fr.bl.template.ui.util.SpringContext;

/**
 * Exemple de WS REST retournant du JSON.
 * Les Beans java sont automatiquement convertis en JSON grâce à jackson-databind
 * 
 * https://github.com/FasterXML/jackson-databind/
 * 
 * @author anthony.lagrede
 *
 */
@RestController
@RequestMapping("/ws/")
public class AuthUserRestController {

	@Autowired private AuthUserDao authUserDao;
	@Autowired private AuthGroupDao authGroupDao;
	@Autowired private AuthPermissionDao authPermissionDao;

	@InitBinder("changePasswordCommand")
	public void initBinderUserCommand(WebDataBinder binder) {
		binder.setValidator(new ChangePasswordValidator());
	}

	
	@RequestMapping(value = "/authuser/list", produces = "application/json")  
	public List<AuthUser> listAuthUsers(PaginationData p) {
		if (p == null || p.getSortField() == "") {
			p.setSortField("id");
			p.setSortDirection("Desc");
		}
		Sort.Direction direction = "Asc".equals(p.getSortDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = new PageRequest(p.getPageNumber(), p.getPageSize(), direction, p.getSortField());
		Page<AuthUser> page = authUserDao.findAll(pageRequest);
		return page.getContent();
	}  



	@Transactional
	@RequestMapping(value = "/authuser/edit/{id}", method = RequestMethod.GET, produces = "application/json")
	public AuthUser findOne(@PathVariable String id) {
		AuthUser authUser = authUserDao.findOneFetchGroups(Long.valueOf(id));
		authUser.loadSelectedGroups();
		authUser.loadSelectedPermissions();
		return authUser;
	}

	@Transactional
	@RequestMapping(value = "/authuser/delete/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity deleteUser(@PathVariable String id) {
		AuthUser authUser = authUserDao.findOne(Long.valueOf(id));
		authUser.getGroups().clear();
		authUser.getPermissions().clear();
		authUserDao.delete(authUser);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@Transactional
	@RequestMapping(value = "/authuser/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity add(@Valid @RequestBody AuthUser authUser, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
		}

		if (authUser.getId() != null) {
			// sur une modif on reprend le mot de passe existant
			String currentEncodedPassword = authUserDao.loadPasswordForUser(authUser.getId());
			authUser.setPassword(currentEncodedPassword); // on reprend le password
		} else {
			// sur une création on encrypte le mdp
			PasswordEncoder passwordEncoder = SpringContext.getBean(BCryptPasswordEncoder.class);
			String encodedPassword = passwordEncoder.encode(authUser.getPassword());
			authUser.setPassword(encodedPassword);
		}


		addGroupsToUser(authUser); // chargement des groupes à sauver
		addPermissionsToUser(authUser); // chargement des permissions à sauver

		authUserDao.save(authUser);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


	
    // Changement du mot de passe
	@Transactional
    @RequestMapping(value = "/authuser/changePassword", method = RequestMethod.POST)
    public ResponseEntity changePasswordPost(@Valid @RequestBody ChangePasswordCommand changePasswordCommand, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
		}

		AuthUser authUser = authUserDao.findOne(changePasswordCommand.getUserId());

		// Update du nouveau mot de passe 
		PasswordEncoder passwordEncoder = SpringContext.getBean(BCryptPasswordEncoder.class);
		String encodedPassword = passwordEncoder.encode(changePasswordCommand.getPassword());
		authUser.setPassword(encodedPassword);
		authUserDao.save(authUser);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
    

	


	@RequestMapping(value = "/authuser/groupscombo", method = RequestMethod.GET, produces = "application/json")
	public List<AuthGroup> getAvailableGroups() {
		return authGroupDao.findAll();
	}

	@RequestMapping(value = "/authuser/permissionscombo", method = RequestMethod.GET, produces = "application/json")
	public List<AuthPermission> getAvailablePermissions() {
		return authPermissionDao.findAll();
	}




	private void addGroupsToUser(AuthUser authUser) {
		if (authUser.getSelectedGroups2() != null) {
			authUser.getGroups().clear();
			for (String groupId : authUser.getSelectedGroups2()) {
				AuthGroup g = authGroupDao.findOne(Long.valueOf(groupId));
				authUser.getGroups().add(g);
			}
		}
	}

	private void addPermissionsToUser(AuthUser authUser) {
		if (authUser.getSelectedPermissions2() != null) {
			authUser.getPermissions().clear();
			for (String permissionId : authUser.getSelectedPermissions2()) {
				AuthPermission p = authPermissionDao.findOne(Long.valueOf(permissionId));
				authUser.getPermissions().add(p);
			}
		}
	}



}
