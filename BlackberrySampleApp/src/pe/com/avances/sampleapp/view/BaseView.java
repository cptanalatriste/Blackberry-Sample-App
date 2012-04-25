package pe.com.avances.sampleapp.view;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import org.moonmonkeylabs.ui.container.ViewMainScreen;

/**
 * @author cgavidia
 * 
 */
public class BaseView extends ViewMainScreen {

	public BaseView(String screenTitle, boolean useGreyBackground) {
		super(VERTICAL_SCROLL | USE_ALL_HEIGHT);
		LabelField titleLabel = new LabelField(screenTitle);
		titleLabel.setPadding(4, 0, 3, 4);
		Font titleFont = titleLabel.getFont().derive(Font.PLAIN,
				titleLabel.getFont().getHeight() + 2);
		titleLabel.setFont(titleFont);
		super.setTitle(titleLabel);

		if (useGreyBackground) {
			Manager screenManager = getMainManager();
			Background greyBackground = BackgroundFactory
					.createSolidBackground(0xDDDDDD);
			screenManager.setBackground(greyBackground);
		}

	}

	public boolean isDirty() {
		return false;
	}

	public void updateView(int key) {
		// TODO Auto-generated method stub

	}

}
