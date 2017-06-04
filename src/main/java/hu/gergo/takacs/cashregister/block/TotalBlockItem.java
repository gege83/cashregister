package hu.gergo.takacs.cashregister.block;

public class TotalBlockItem implements BlockItem {
    private double total;
    private BlockTotalType type;

    public TotalBlockItem(double total, BlockTotalType type) {
        this.total = total;
        this.type = type;
    }

    @Override
    public double getPrice() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalBlockItem that = (TotalBlockItem) o;

        if (Double.compare(that.total, total) != 0) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(total);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TotalBlockItem{" +
                "total=" + total +
                ", type=" + type +
                '}';
    }
}
