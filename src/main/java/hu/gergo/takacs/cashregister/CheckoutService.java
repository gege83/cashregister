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
        SubTotalBlockItem subTotalBlockItem = getSubTotal(pricedItems);
        List<DiscountBlockItem> discounts = calculateDiscounts(items, offers);
        DiscountTotalBlockItem discountTotal = getDiscountTotal(discounts);
        TotalBlockItem totalBlockItem = calculateTotal(subTotalBlockItem, discountTotal);
        if (!discounts.isEmpty()) {
            pricedItems.add(subTotalBlockItem);
            pricedItems.addAll(discounts);
            pricedItems.add(discountTotal);
        }
        pricedItems.add(totalBlockItem);
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

    private DiscountTotalBlockItem getDiscountTotal(List<DiscountBlockItem> discounts) {
        double total = getSum(discounts);
        return new DiscountTotalBlockItem(total);
    }

    private SubTotalBlockItem getSubTotal(List<BlockItem> blockItems) {
        double total = getSum(blockItems);
        return new SubTotalBlockItem(total);
    }

    private double getSum(List<? extends BlockItem> blockItems) {
        return blockItems.stream().mapToDouble(BlockItem::getPrice).sum();
    }

    private double calculatePrice(Item item) {
        return item.getQuantity() * item.getSku().getUnitPrice();
    }

    private TotalBlockItem calculateTotal(SubTotalBlockItem subTotalBlockItem, DiscountTotalBlockItem discountTotalBlockItem) {
        double total = subTotalBlockItem.getPrice() + discountTotalBlockItem.getPrice();
        total = Math.round(total * 1000.) / 1000.;
        return new TotalBlockItem(total);
    }
}
