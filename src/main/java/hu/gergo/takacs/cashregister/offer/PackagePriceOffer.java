package hu.gergo.takacs.cashregister.offer;

import hu.gergo.takacs.cashregister.RoundUtil;
import hu.gergo.takacs.cashregister.block.DiscountBlockItem;
import hu.gergo.takacs.cashregister.purchase.Sku;

import java.util.Map;

public class PackagePriceOffer implements Offer {
    private final Sku sku;
    private final double quantity;
    private final double discountPrice;

    public PackagePriceOffer(Sku sku, double quantity, double discountPrice) {
        this.sku = sku;
        this.quantity = quantity;
        this.discountPrice = discountPrice;
    }

    @Override
    public boolean isApplicable(Map<Sku, Double> itemSummary) {
        return itemSummary.getOrDefault(sku, 0D) >= quantity;
    }

    @Override
    public DiscountBlockItem calculateDiscount(Map<Sku, Double> items) {
        Double itemQuantity = items.get(sku);
        int packageCount = getPackageCount(itemQuantity);
        double discountedQuantity = getDiscountedQuantity(packageCount);
        double discountTotal = calculateDiscountTotal(packageCount, discountedQuantity);
        return new DiscountBlockItem(sku, discountedQuantity, discountTotal);
    }

    private int getPackageCount(Double itemQuantity) {
        return (int) Math.floor(itemQuantity / quantity);
    }

    private double getDiscountedQuantity(int packageCount) {
        return quantity * packageCount;
    }

    private double calculateDiscountTotal(int packageCount, double discountedQuantity) {
        double total = packageCount * discountPrice - sku.getUnitPrice() * discountedQuantity;
        total = RoundUtil.roundToBusinessDecimal(total);
        return total;
    }
}
