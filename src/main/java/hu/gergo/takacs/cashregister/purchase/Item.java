package hu.gergo.takacs.cashregister.purchase;

public class Item {
    private final ItemDescription itemDescription;
    private final double quantity;

    public Item(ItemDescription itemDescription, double quantity) {
        this.itemDescription = itemDescription;
        this.quantity = quantity;
    }

    public ItemDescription getItemDescription() {
        return itemDescription;
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
        return itemDescription != null ? itemDescription.equals(item.itemDescription) : item.itemDescription == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = itemDescription != null ? itemDescription.hashCode() : 0;
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemDescription=" + itemDescription +
                ", quantity=" + quantity +
                '}';
    }
}
