package pe.com.avances.sampleapp.login;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

import org.moonmonkeylabs.ui.container.ViewMainScreen;

import pe.com.avances.sampleapp.EventCatalog;
import pe.com.avances.sampleapp.view.BaseView;
import devcom.sample.device.api.ui.container.JustifiedHorizontalFieldManager;
import devcom.sample.device.api.ui.container.TwoColumnField;
import devcom.sample.device.api.ui.container.TwoColumnFieldManager;

public class LoginView extends BaseView {

	private static final String PAGE_TITLE = "Liberación de Documentos";
	private static final String USERNAME_LABEL = "Usuario: ";
	private static final String PASSWORD_LABEL = "Contraseña: ";
	private static final String LOG_IN_LABEL = "Ingresar";
	private static final String CANCEL_LABEL = "Cancelar";

	public LoginView() {
		super(PAGE_TITLE, true);

		Manager screenManager = getMainManager();

		TwoColumnFieldManager mainManager = new TwoColumnFieldManager(Display
				.getWidth() / 3, USE_ALL_WIDTH);
		Border twoColumnBorder = BorderFactory.createBitmapBorder(new XYEdges(
				10, 10, 10, 10), Bitmap
				.getBitmapResource("border_simple_white.png"));
		mainManager.setBorder(twoColumnBorder);
		mainManager.setMargin(10, 10, 10, 10);
		mainManager.setPadding(6, 6, 6, 6);

		Border borderForEditFields = BorderFactory.createBitmapBorder(
				new XYEdges(10, 9, 9, 11), Bitmap
						.getBitmapResource("border_edit.png"));

		LabelField usernameLabel = new LabelField(USERNAME_LABEL, FIELD_RIGHT
				| FIELD_VCENTER);
		EditField usernameEditField = new EditField(FIELD_VCENTER);
		usernameEditField.setBorder(borderForEditFields);
		usernameEditField.setMargin(0, 0, 0, 6);
		TwoColumnField usernameColumnField = new TwoColumnField(usernameLabel,
				usernameEditField, USE_ALL_WIDTH);
		usernameColumnField.setMargin(6, 0, 6, 0);
		mainManager.add(usernameColumnField);

		LabelField passwordLabel = new LabelField(PASSWORD_LABEL, FIELD_RIGHT
				| FIELD_VCENTER);
		PasswordEditField passwordEditField = new PasswordEditField();
		passwordEditField.setBorder(borderForEditFields);
		passwordEditField.setMargin(0, 0, 0, 6);
		TwoColumnField passwordColumnField = new TwoColumnField(passwordLabel,
				passwordEditField, USE_ALL_WIDTH);
		passwordColumnField.setMargin(6, 0, 6, 0);
		mainManager.add(passwordColumnField);

		Field separator = new SeparatorField();
		separator.setMargin(6, 0, 6, 0);
		mainManager.add(separator);

		ButtonField logInButton = new ButtonField(LOG_IN_LABEL,
				ButtonField.CONSUME_CLICK);
		logInButton.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				notifyViewListeners(EventCatalog.CREDENTIALS_SENT);
			}
		});

		ButtonField cancelButton = new ButtonField(CANCEL_LABEL,
				ButtonField.CONSUME_CLICK);
		cancelButton.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				notifyViewListeners(ViewMainScreen.ScreenClose);
			}
		});

		JustifiedHorizontalFieldManager buttonManager = new JustifiedHorizontalFieldManager(
				cancelButton, null, logInButton);
		mainManager.add(buttonManager);

		screenManager.add(mainManager);

	}

	public void updateView(int key) {
		// TODO Auto-generated method stub

	}

}
