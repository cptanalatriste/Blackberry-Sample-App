package pe.com.avances.sampleapp.dao;

import java.util.Date;
import java.util.Vector;

import pe.com.avances.sampleapp.model.Document;
import pe.com.avances.sampleapp.model.DocumentDetail;

/**
 * @author cgavidia
 * 
 */
public class DocumentDAO {

	public Vector getAllDocuments() {
		Vector documentVector = new Vector();
		Document aDocument = new Document("PEN", 2082.50, "Ace Peru SAC");
		documentVector.addElement(aDocument);

		Document anotherDocument = new Document("USD", 79.73,
				"Cargill Americas INC.");
		documentVector.addElement(anotherDocument);

		Document yetAnotherDocument = new Document("USD", 119.00,
				"Cargill Americas INC.");
		documentVector.addElement(yetAnotherDocument);

		return documentVector;
	}

	public DocumentDetail getDocumentDetail() {
		DocumentDetail detail = new DocumentDetail();
		detail.setInvoice("001-0125406");
		detail.setBlockedBecause("Bloq. por pago");
		detail.setReason("");
		detail.setCreationDate("25/11/2007");
		detail.setExpirationDate("26/10/2007");
		detail.setRequestedBy("DEV. TSNET");
		detail.setMobile("/");
		detail.setSAPNumber("0200001973");
		return detail;
	}

}
