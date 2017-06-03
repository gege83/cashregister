package hu.gergo.takacs.cashregister.block;

public class DiscountTotalBlockItem implements BlockItem {
    private double total;

    public DiscountTotalBlockItem(double total) {
        this.total = total;
    }


    @Override
    public double getPrice() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountTotalBlockItem that = (DiscountTotalBlockItem) o;

        return Double.compare(that.total, total) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(total);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "DiscountTotalBlockItem{" +
                "total=" + total +
                '}';
    }
}
