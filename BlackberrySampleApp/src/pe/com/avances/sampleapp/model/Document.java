package pe.com.avances.sampleapp.model;

public class Document {

	private String currency;
	private double mount;
	private String supplier;

	public Document(String currency, double mount, String supplier) {
		this.currency = currency;
		this.mount = mount;
		this.supplier = supplier;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getMount() {
		return mount;
	}

	public void setMount(double mount) {
		this.mount = mount;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String toString() {
		return supplier + " \n" + mount + " " + currency;
	}

}
