package pe.com.avances.sampleapp.doc;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.Dialog;

import org.moonmonkeylabs.bbmvc.Controller;
import org.moonmonkeylabs.bbmvc.Navigator;
import org.moonmonkeylabs.bbmvc.View;
import org.moonmonkeylabs.ui.container.ViewMainScreen;

import pe.com.avances.sampleapp.EventCatalog;

public class DocumentDetailController extends Controller {

	protected static final String SUCCESS_MSG = "Documento liberado correctamente";

	public DocumentDetailController(View view, int controllerId) {
		super(view, controllerId);
		// TODO Auto-generated constructor stub
	}

	protected void onInitialize(Object[] parameters) {
		// TODO Auto-generated method stub

	}

	protected void onUpdate(Object[] parameters) {
		// TODO Auto-generated method stub

	}

	protected void onViewStateChanged(int event) {
		Navigator navigator = Navigator.getInstance();

		switch (event) {
		case ViewMainScreen.ScreenClose:
			navigator.goBack();
			break;
		case EventCatalog.FREE_DOCUMENT:
			Bitmap icon = Bitmap.getBitmapResource("accepted_48.png");
			Dialog d = new Dialog(Dialog.D_OK, SUCCESS_MSG, Dialog.OK, icon, 0);
			d.doModal();
			navigator.goBack();
			break;
		}
	}

}
