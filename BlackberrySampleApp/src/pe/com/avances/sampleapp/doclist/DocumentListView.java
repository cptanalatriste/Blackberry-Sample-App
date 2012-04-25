package pe.com.avances.sampleapp.doclist;

import java.util.Enumeration;
import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import pe.com.avances.sampleapp.EventCatalog;
import pe.com.avances.sampleapp.model.Document;
import pe.com.avances.sampleapp.view.BaseView;
import devcon.sample.device.api.ui.component.ListStyleButtonField;

/**
 * @author cgavidia
 * 
 */
public class DocumentListView extends BaseView {

	private static final String SCREEN_TITLE = "Listado de Documentos";

	public DocumentListView(Vector documents) {
		super(SCREEN_TITLE, false);
		Bitmap caret = Bitmap
				.getBitmapResource("chevron_right_black_12x18.png");

		Bitmap leftIcon = Bitmap.getBitmapResource("BulletPointWineRed.png");

		Enumeration enumeration = documents.elements();
		while (enumeration.hasMoreElements()) {
			Document document = (Document) enumeration.nextElement();
			ListStyleButtonField documentListItem = new ListStyleButtonField(
					leftIcon, document.toString(), caret, 0);
			documentListItem.setChangeListener(new FieldChangeListener() {

				public void fieldChanged(Field field, int context) {
					notifyViewListeners(EventCatalog.DOCUMENT_SELECTED);
				}
			});
			add(documentListItem);
		}

	}

	public void updateView(int key) {
		// TODO Auto-generated method stub

	}

}
