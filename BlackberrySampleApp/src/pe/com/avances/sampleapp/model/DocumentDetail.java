package pe.com.avances.sampleapp.model;

import java.util.Date;

/**
 * @author cgavidia
 *
 */
public class DocumentDetail {

	private String invoice;
	private String blockedBecause;
	private String reason;
	private String expirationDate;
	private String creationDate;
	private String requestedBy;
	private String mobile;
	private String SAPNumber;

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getBlockedBecause() {
		return blockedBecause;
	}

	public void setBlockedBecause(String blockedBecause) {
		this.blockedBecause = blockedBecause;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSAPNumber() {
		return SAPNumber;
	}

	public void setSAPNumber(String sAPNumber) {
		SAPNumber = sAPNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
