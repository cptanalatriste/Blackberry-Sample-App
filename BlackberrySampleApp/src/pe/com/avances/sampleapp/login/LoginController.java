package pe.com.avances.sampleapp.login;

import java.util.Vector;

import org.moonmonkeylabs.bbmvc.Controller;
import org.moonmonkeylabs.bbmvc.Navigator;
import org.moonmonkeylabs.bbmvc.View;
import org.moonmonkeylabs.ui.container.ViewMainScreen;

import pe.com.avances.sampleapp.ControllerCatalog;
import pe.com.avances.sampleapp.EventCatalog;
import pe.com.avances.sampleapp.dao.DocumentDAO;
import pe.com.avances.sampleapp.doclist.DocumentListController;
import pe.com.avances.sampleapp.doclist.DocumentListView;

/**
 * @author cgavidia
 * 
 */
public class LoginController extends Controller {

	private DocumentDAO documentDAO = new DocumentDAO();

	public LoginController(View view, int controllerId) {
		super(view, controllerId);
	}

	protected void onViewStateChanged(int event) {
		Navigator navigator = Navigator.getInstance();
		switch (event) {
		case ViewMainScreen.ScreenClose:
			navigator.goBack();
			break;
		case EventCatalog.CREDENTIALS_SENT:
			try {
				if (!navigator
						.hasController(ControllerCatalog.DOCUMENT_LIST_CONTROLLER)) {
					Vector documents = documentDAO.getAllDocuments();
					DocumentListView documentView = new DocumentListView(
							documents);
					DocumentListController documentListController = new DocumentListController(
							documentView,
							ControllerCatalog.DOCUMENT_LIST_CONTROLLER);
					navigator.addController(documentListController);

				}
				navigator.navigate(ControllerCatalog.DOCUMENT_LIST_CONTROLLER);
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}

	}

	protected void onInitialize(Object[] parameters) {
		// TODO Auto-generated method stub

	}

	protected void onUpdate(Object[] parameters) {
		// TODO Auto-generated method stub

	}
}
