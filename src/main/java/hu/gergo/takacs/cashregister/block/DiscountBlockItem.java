package hu.gergo.takacs.cashregister.block;

import hu.gergo.takacs.cashregister.purchase.Sku;

public class DiscountBlockItem implements BlockItem {
    private final Sku sku;
    private final double discountedQuantity;
    private final double discountTotal;

    public DiscountBlockItem(Sku sku, double discountedQuantity, double discountTotal) {

        this.sku = sku;
        this.discountedQuantity = discountedQuantity;
        this.discountTotal = discountTotal;
    }

    @Override
    public double getPrice() {
        return discountTotal;
    }

    public Sku getSku() {
        return sku;
    }

    public double getDiscountedQuantity() {
        return discountedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountBlockItem that = (DiscountBlockItem) o;

        if (Double.compare(that.discountedQuantity, discountedQuantity) != 0) return false;
        if (Double.compare(that.discountTotal, discountTotal) != 0) return false;
        return sku != null ? sku.equals(that.sku) : that.sku == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = sku != null ? sku.hashCode() : 0;
        temp = Double.doubleToLongBits(discountedQuantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(discountTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "DiscountBlockItem{" +
                "sku=" + sku +
                ", discountedQuantity=" + discountedQuantity +
                ", discountTotal=" + discountTotal +
                '}';
    }
}
