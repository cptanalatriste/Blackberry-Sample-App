package pe.com.avances.sampleapp.doclist;

import org.moonmonkeylabs.bbmvc.Controller;
import org.moonmonkeylabs.bbmvc.Navigator;
import org.moonmonkeylabs.bbmvc.View;
import org.moonmonkeylabs.ui.container.ViewMainScreen;

import pe.com.avances.sampleapp.ControllerCatalog;
import pe.com.avances.sampleapp.EventCatalog;
import pe.com.avances.sampleapp.dao.DocumentDAO;
import pe.com.avances.sampleapp.doc.DocumentDetailController;
import pe.com.avances.sampleapp.doc.DocumentDetailView;
import pe.com.avances.sampleapp.model.DocumentDetail;

/**
 * @author cgavidia
 * 
 */
public class DocumentListController extends Controller {

	private DocumentDAO documentDAO = new DocumentDAO();

	public DocumentListController(View view, int controllerId) {
		super(view, controllerId);
	}

	protected void onViewStateChanged(int event) {
		Navigator navigator = Navigator.getInstance();

		switch (event) {
		case ViewMainScreen.ScreenClose:
			navigator.goBack();
			break;
		case EventCatalog.DOCUMENT_SELECTED:
			try {
				if (!navigator
						.hasController(ControllerCatalog.DOCUMENT_DETAIL_CONTROLLER)) {
					DocumentDetail documentDetail = documentDAO
							.getDocumentDetail();
					DocumentDetailView documentDetailView = new DocumentDetailView(documentDetail);
					DocumentDetailController documentDetailController = new DocumentDetailController(
							documentDetailView,
							ControllerCatalog.DOCUMENT_DETAIL_CONTROLLER);
					navigator.addController(documentDetailController);

				}
				navigator
						.navigate(ControllerCatalog.DOCUMENT_DETAIL_CONTROLLER);
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
