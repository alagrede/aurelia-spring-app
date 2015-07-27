package fr.bl.template.ui.demo.web.user;

import org.junit.Test;
import org.springframework.stereotype.Controller;

/**
 * Exemple de test sur un {@link Controller}
 * 
 */
public class UserControllerTest extends AbstractWebControllerTest {

	@Test
	public void mock() {
		
	}
	
//	@Autowired
//	private WebApplicationContext wac;
//
//	private MockMvc mockMvc;
//
//	@Mock
//	UserDao userDao;
//	
//	@Mock
//	UserService userService;
//	
//	@InjectMocks
//	UserController userController = new UserController();
//	
//	
//	@Before
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//		//We have to reset our mock between tests because the mock objects
//		//are managed by the Spring container. If we would not reset them,
//		//stubbing and verified behavior would "leak" from one test to another.
//		//Mockito.reset(userDao);
//		Mockito.when(userDao.save((User)anyObject())).thenReturn(new User());
//		Mockito.when(userService.referenceData()).thenReturn(new UserService().referenceData());
//
//		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//		
////		ReflectionTestUtils
//	}
//
//
//	@Test
//	public void testGetAddUserForm() throws Exception {
//	    this.mockMvc.perform(get("/user/initadd"))
//	            .andExpect(status().isOk())
//	            .andExpect(model().attribute("userCommand", isA(UserCommand.class)));//@ModelAttribute("user")
//	}
//	 
//	@Test
//	public void testCreateUserFormOk() throws Exception {
//	 
//	  this.mockMvc.perform(post("/user/add")
//	            .param("birthDate", "01/01/2014")
//	            .param("firstname", "tony")
//	            .param("lastname", "tony"))
//	            .andExpect(status().is2xxSuccessful())
//	            .andExpect(view().name("user/userAdd"));
//	            //.andExpect(model().attributeHasFieldErrors("user", "firstname"));
//	}
//	
//	/**
//	 * Dès l'instant où on utilise un "custom validator" pour la commande, il n'est plus possible d'utiliser mockMVC pour la validation de formulaire.
//	 * Il faut alors passer le test à la main pour vérifier s'il y a des erreurs de validation. 
//	 */
//	@Test
//	public void testCreateUserFormErrors() throws Exception {
//
//		// Ne fonctionne plus à cause de la personnalisation de UserCommandValidator
////		this.mockMvc.perform(post("/user/add")
////				.param("birthDate", "01/01/2014")
////				.param("firstname", "")
////				.param("lastname", "tony"))
////				.andExpect(status().isOk())
////				.andExpect(view().name("user/userAdd"))
////				.andExpect(model().attributeHasFieldErrors("user", "firstname"));
//		
//		
//		
//		   MockHttpServletRequest request = new MockHttpServletRequest("POST", "/edit");
//
//		    // populate the request, use the same names as you would for the form elements in the corresponding .jsp
//		    request.setParameter("firstname", "tony");
//		    request.setParameter("lastname", "");
//		    request.setParameter("brithDate", "01/01/2001");
//
//		    // create a new instance of your form object
//		    UserCommand command = new UserCommand();
//
//		    WebDataBinder binder = new WebDataBinder(command);
//		    binder.setValidator(new UserCommandValidator()); // use the validator from the context
//		    binder.bind(new MutablePropertyValues(request.getParameterMap()));
//
//		    // validation must be triggered manually
//		    binder.getValidator().validate(binder.getTarget(), binder.getBindingResult());
//
//		    // if you want to test if the validation works as expected you can check the binding result
//		    // eg. if no errors are expected:
//		    Assert.assertEquals(3, binder.getBindingResult().getErrorCount());
//
//		    // now call your controller method and store the return type if it matters
//		    // f. ex. a different view might be returned depending on whether there are validation errors
//		    ModelAndView mav = userController.add((UserCommand)binder.getTarget(), binder.getBindingResult());
//
//		    Assert.assertEquals("user/userAdd", mav.getViewName());
//		
//	}
	
}
