package hu.gergo.takacs.cashregister.purchase;

public class Sku {
    private final String name;
    private final double unitPrice;

    public Sku(String id, double unitPrice) {
        this.name = id;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sku that = (Sku) o;

        if (Double.compare(that.unitPrice, unitPrice) != 0) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(unitPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
