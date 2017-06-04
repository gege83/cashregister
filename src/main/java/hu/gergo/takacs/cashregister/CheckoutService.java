package hu.gergo.takacs.cashregister;

import hu.gergo.takacs.cashregister.block.*;
import hu.gergo.takacs.cashregister.offer.Offer;
import hu.gergo.takacs.cashregister.purchase.Item;
import hu.gergo.takacs.cashregister.purchase.Sku;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckoutService {

    public List<BlockItem> getBlock(List<Item> items, List<Offer> offers) {
        List<BlockItem> pricedItems = items.stream()
                .map(item -> new PricedItem(item, calculatePrice(item)))
                .collect(Collectors.toList());
        TotalBlockItem subTotalBlockItem = getSubTotal(pricedItems);
        List<DiscountBlockItem> discounts = calculateDiscounts(items, offers);
        TotalBlockItem discountTotal = getDiscountTotal(discounts);
        TotalBlockItem grandTotalBlockItem = calculateTotal(subTotalBlockItem, discountTotal);
        if (!discounts.isEmpty()) {
            pricedItems.add(subTotalBlockItem);
            pricedItems.addAll(discounts);
            pricedItems.add(discountTotal);
        }
        pricedItems.add(grandTotalBlockItem);
        return pricedItems;
    }

    private List<DiscountBlockItem> calculateDiscounts(List<Item> items, List<Offer> offers) {
        Map<Sku, Double> itemSummary = items.stream()
                .collect(Collectors.groupingBy(Item::getSku, Collectors.summingDouble(Item::getQuantity)));
        List<DiscountBlockItem> discountBlockItems = offers.stream()
                .filter(skuOfferEntry -> skuOfferEntry.isApplicable(itemSummary))
                .map(skuOfferEntry -> skuOfferEntry.calculateDiscount(itemSummary))
                .collect(Collectors.toList());
        return discountBlockItems;
    }

    private TotalBlockItem getDiscountTotal(List<DiscountBlockItem> discounts) {
        double total = getSum(discounts);
        return new TotalBlockItem(total, BlockTotalType.DISCOUNT_TOTAL);
    }

    private TotalBlockItem getSubTotal(List<BlockItem> blockItems) {
        double total = getSum(blockItems);
        return new TotalBlockItem(total, BlockTotalType.SUB_TOTAL);
    }

    private double getSum(List<? extends BlockItem> blockItems) {
        return blockItems.stream().mapToDouble(BlockItem::getPrice).sum();
    }

    private double calculatePrice(Item item) {
        return item.getQuantity() * item.getSku().getUnitPrice();
    }

    private TotalBlockItem calculateTotal(TotalBlockItem subTotalBlockItem, TotalBlockItem discountTotalBlockItem) {
        double total = subTotalBlockItem.getPrice() + discountTotalBlockItem.getPrice();
        total = Math.round(total * 1000.) / 1000.;
        return new TotalBlockItem(total, BlockTotalType.GRAND_TOTAL);
    }
}
