package hu.gergo.takacs.cashregister.purchase;

public class Item {
    private final Sku sku;
    private final double quantity;

    public Item(Sku sku, double quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public Sku getSku() {
        return sku;
    }

    public double getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.quantity, quantity) != 0) return false;
        return sku != null ? sku.equals(item.sku) : item.sku == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = sku != null ? sku.hashCode() : 0;
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "sku=" + sku +
                ", quantity=" + quantity +
                '}';
    }
}
