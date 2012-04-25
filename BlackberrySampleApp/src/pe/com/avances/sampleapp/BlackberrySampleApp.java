package pe.com.avances.sampleapp;

import org.moonmonkeylabs.bbmvc.Controller;
import org.moonmonkeylabs.bbmvc.Navigator;

import pe.com.avances.sampleapp.login.LoginController;
import pe.com.avances.sampleapp.login.LoginView;

/**
 * @author cgavidia
 * 
 */
public class BlackberrySampleApp {

	public static void main(String[] args) {
		Navigator navigator = Navigator.getInstance();
		Controller loginController = new LoginController(new LoginView(),
				ControllerCatalog.LOGIN_CONTROLLER);
		try {
			navigator.addController(loginController);
			navigator.navigate(ControllerCatalog.LOGIN_CONTROLLER);
			navigator.enterEventDispatcher();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
