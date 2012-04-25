package pe.com.avances.sampleapp.doc;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

import org.moonmonkeylabs.ui.container.ViewMainScreen;

import pe.com.avances.sampleapp.EventCatalog;
import pe.com.avances.sampleapp.model.DocumentDetail;
import pe.com.avances.sampleapp.view.BaseView;
import devcom.sample.device.api.ui.container.FieldSet;
import devcom.sample.device.api.ui.container.TwoColumnField;
import devcom.sample.device.api.ui.container.TwoColumnFieldManager;

public class DocumentDetailView extends BaseView {

	private static final String SCREEN_TITLE = "Detalle del Documento";
	private static final String DETAIL_LABEL = "Información";
	private static final String INVOICE_LABEL = "Factura : ";
	private static final String BLOCKED_BECAUSE_LABEL = "Bloqueado por: ";
	private static final String EXPIRATION_DATE_LABEL = "F. de Vencimiento: ";
	private static final String DOCUMENT_DATE_LABEL = "F. de Documento: ";
	private static final String REQUESTED_BY_LABEL = "Solicitado por: ";
	private static final String MOBILE_LABEL = "Celular/RPM: ";
	private static final String SAP_NUMBER_LABEL = "Documento SAP: ";
	private static final String REASON_LABEL = "Motivo: ";
	private static final String CANCEL_BUTTON_LABEL = "Cancelar";
	private static final String FREE_BUTTON_LABEL = "Liberar";
	protected static final String ARE_YOU_SURE_MSG = "¿Está seguro que desea liberar este documento?";

	public DocumentDetailView(DocumentDetail documentDetail) {
		super(SCREEN_TITLE, true);
		Border titleBorder = BorderFactory.createBitmapBorder(new XYEdges(8, 9,
				3, 9), Bitmap.getBitmapResource("fieldset_title_border.png"));
		Border contentBorder = BorderFactory
				.createBitmapBorder(new XYEdges(6, 9, 10, 9), Bitmap
						.getBitmapResource("fieldset_body_border.png"));

		FieldSet documentFieldSet = new FieldSet(DETAIL_LABEL, titleBorder,
				contentBorder, USE_ALL_WIDTH);
		documentFieldSet.setMargin(10, 10, 10, 10);
		TwoColumnFieldManager columns = new TwoColumnFieldManager(Display
				.getWidth() / 2, USE_ALL_WIDTH);

		LabelField invoiceLabelField = new LabelField(INVOICE_LABEL,
				FIELD_VCENTER);
		invoiceLabelField.setMargin(2, 2, 2, 2);
		LabelField invoiceValueField = new LabelField(documentDetail
				.getInvoice(), FIELD_RIGHT);
		invoiceLabelField.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(invoiceLabelField, invoiceValueField,
				USE_ALL_WIDTH));

		LabelField leftField0 = new LabelField(BLOCKED_BECAUSE_LABEL,
				FIELD_VCENTER);
		leftField0.setMargin(2, 2, 2, 2);
		LabelField rightField0 = new LabelField(documentDetail
				.getBlockedBecause(), FIELD_RIGHT);
		rightField0.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField0, rightField0, USE_ALL_WIDTH));

		LabelField leftField = new LabelField(REASON_LABEL, FIELD_VCENTER);
		leftField.setMargin(2, 2, 2, 2);
		LabelField rightField = new LabelField(documentDetail.getReason(),
				FIELD_RIGHT);
		rightField.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField, rightField, USE_ALL_WIDTH));

		LabelField leftField3 = new LabelField(EXPIRATION_DATE_LABEL,
				FIELD_VCENTER);
		leftField3.setMargin(2, 2, 2, 2);
		LabelField rightField3 = new LabelField(documentDetail
				.getExpirationDate(), FIELD_RIGHT);
		rightField3.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField3, rightField3, USE_ALL_WIDTH));

		LabelField leftField2 = new LabelField(DOCUMENT_DATE_LABEL,
				FIELD_VCENTER);
		leftField2.setMargin(2, 2, 2, 2);
		LabelField rightField2 = new LabelField(documentDetail
				.getCreationDate(), FIELD_RIGHT);
		rightField2.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField2, rightField2, USE_ALL_WIDTH));

		LabelField leftField4 = new LabelField(REQUESTED_BY_LABEL,
				FIELD_VCENTER);
		leftField4.setMargin(2, 2, 2, 2);
		LabelField rightField4 = new LabelField(
				documentDetail.getRequestedBy(), FIELD_RIGHT);
		rightField4.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField4, rightField4, USE_ALL_WIDTH));

		LabelField leftField5 = new LabelField(MOBILE_LABEL, FIELD_VCENTER);
		leftField5.setMargin(2, 2, 2, 2);
		LabelField rightField5 = new LabelField(documentDetail.getMobile(),
				FIELD_RIGHT);
		rightField5.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField5, rightField5, USE_ALL_WIDTH));

		LabelField leftField6 = new LabelField(SAP_NUMBER_LABEL, FIELD_VCENTER);
		leftField6.setMargin(2, 2, 2, 2);
		LabelField rightField6 = new LabelField(documentDetail.getSAPNumber(),
				FIELD_RIGHT);
		rightField6.setMargin(2, 2, 2, 2);
		columns.add(new TwoColumnField(leftField6, rightField6, USE_ALL_WIDTH));

		ButtonField cancelButton = new ButtonField(CANCEL_BUTTON_LABEL,
				FIELD_VCENTER);
		cancelButton.setMargin(5, 5, 5, 5);
		cancelButton.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				notifyViewListeners(ViewMainScreen.ScreenClose);
			}
		});

		ButtonField freeButton = new ButtonField(FREE_BUTTON_LABEL, FIELD_RIGHT);
		freeButton.setMargin(5, 5, 5, 5);
		freeButton.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				int proceed = Dialog.ask(Dialog.D_YES_NO, ARE_YOU_SURE_MSG,
						Dialog.NO);
				if (proceed == Dialog.YES) {
					notifyViewListeners(EventCatalog.FREE_DOCUMENT);
				}
			}
		});

		columns
				.add(new TwoColumnField(cancelButton, freeButton, USE_ALL_WIDTH));
		documentFieldSet.add(columns);

		getMainManager().add(documentFieldSet);

	}
}
