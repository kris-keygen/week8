package week08.kristian.id.ac.umn;

public abstract class Payment {
	protected double amount;
	
	public Payment(double amount) {
		this.amount= amount;	
	}
	
	abstract void processPayment();
	
	public void paymentDetails() {
		System.out.println("Processing Payment of $" + amount);
	}
}
