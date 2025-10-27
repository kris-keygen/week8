package week08.kristian.id.ac.umn;

public abstract class Payment {

    protected boolean isPaidOff = false;
    protected final Item item;

    public abstract int pay();

    public Payment(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        this.item = item;
    }

    public boolean isPaidOff() {
        return isPaidOff;
    }

    public Item getItem() {
        return item;
    }

    public String getItemName() {
        return item.getName();
    }

    public String getStatus() {
        if (isPaidOff) {
            return "FINISHED";
        }
        return "IN PROGRESS";
    }

    public int getRemainingAmount() {
        if (isPaidOff) {
            return 0;
        }
        return item.getPrice();
    }
}
