package week08.kristian.id.ac.umn;

public class Credit extends Payment {

    private int installment;
    private int maxInstallmentAmount;

    public Credit(Item item, int maxInstallmentAmount) {
        super(item);
        this.maxInstallmentAmount = maxInstallmentAmount;
        this.installment = 0;
    }

    public int pay() {
        if (isPaidOff) {
            return 0;
        }

        this.installment++;

        if (this.installment >= this.maxInstallmentAmount) {
            isPaidOff = true;
        }

        return item.getPrice() / this.maxInstallmentAmount;
    }

    public int getRemainingAmount() {
        if (isPaidOff) {
            return 0;
        }

        int installmentValue = item.getPrice() / this.maxInstallmentAmount;
        int amountPaid = this.installment * installmentValue;
        return item.getPrice() - amountPaid;
    }

    public int getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }
}