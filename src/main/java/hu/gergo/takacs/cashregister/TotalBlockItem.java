package hu.gergo.takacs.cashregister;

public class TotalBlockItem implements BlockItem {
    private double total;

    public TotalBlockItem(double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalBlockItem that = (TotalBlockItem) o;

        return Double.compare(that.total, total) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(total);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "TotalBlockItem{" +
                "total=" + total +
                '}';
    }
}
