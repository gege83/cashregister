package hu.gergo.takacs.cashregister.offer;

import hu.gergo.takacs.cashregister.block.DiscountBlockItem;
import hu.gergo.takacs.cashregister.purchase.Sku;

import java.util.Map;

public interface Offer {
    boolean isApplicable(Map<Sku, Double> item);

    DiscountBlockItem calculateDiscount(Map<Sku, Double> items);
}
