package hu.gergo.takacs.cashregister.block;

import hu.gergo.takacs.cashregister.purchase.Item;

public class PricedItem implements BlockItem {
    private final Item item;
    private final double price;

    public PricedItem(Item item, double price) {
        this.item = item;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PricedItem that = (PricedItem) o;

        if (Double.compare(that.price, price) != 0) return false;
        return item != null ? item.equals(that.item) : that.item == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = item != null ? item.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PricedItem{" +
                "item=" + item +
                ", price=" + price +
                '}';
    }
}
