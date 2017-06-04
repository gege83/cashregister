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
        List<BlockItem> blockItems = calculatePrices(items);
        TotalBlockItem subTotalBlockItem = getSubTotal(blockItems);
        List<DiscountBlockItem> discounts = calculateDiscounts(items, offers);
        TotalBlockItem discountTotal = getDiscountTotal(discounts);
        TotalBlockItem grandTotalBlockItem = calculateTotal(subTotalBlockItem, discountTotal);
        addDiscountItems(blockItems, subTotalBlockItem, discounts, discountTotal);
        blockItems.add(grandTotalBlockItem);
        return blockItems;
    }

    private List<BlockItem> calculatePrices(List<Item> items) {
        return items.stream()
                .map(item -> new PricedItem(item, calculatePrice(item)))
                .collect(Collectors.toList());
    }

    private double calculatePrice(Item item) {
        return item.getQuantity() * item.getSku().getUnitPrice();
    }

    private TotalBlockItem getSubTotal(List<BlockItem> blockItems) {
        double total = getSum(blockItems);
        return new TotalBlockItem(total, BlockTotalType.SUB_TOTAL);
    }

    private List<DiscountBlockItem> calculateDiscounts(List<Item> items, List<Offer> offers) {
        Map<Sku, Double> itemSummary = items.stream()
                .collect(Collectors.groupingBy(Item::getSku, Collectors.summingDouble(Item::getQuantity)));
        return offers.stream()
                .filter(skuOfferEntry -> skuOfferEntry.isApplicable(itemSummary))
                .map(skuOfferEntry -> skuOfferEntry.calculateDiscount(itemSummary))
                .collect(Collectors.toList());
    }

    private TotalBlockItem getDiscountTotal(List<DiscountBlockItem> discounts) {
        double total = getSum(discounts);
        return new TotalBlockItem(total, BlockTotalType.DISCOUNT_TOTAL);
    }

    private double getSum(List<? extends BlockItem> blockItems) {
        return blockItems.stream().mapToDouble(BlockItem::getPrice).sum();
    }

    private TotalBlockItem calculateTotal(TotalBlockItem subTotalBlockItem, TotalBlockItem discountTotalBlockItem) {
        double total = subTotalBlockItem.getPrice() + discountTotalBlockItem.getPrice();
        total = RoundUtil.roundToBusinessDecimal(total);
        return new TotalBlockItem(total, BlockTotalType.GRAND_TOTAL);
    }

    private void addDiscountItems(List<BlockItem> pricedItems, TotalBlockItem subTotalBlockItem, List<DiscountBlockItem> discounts, TotalBlockItem discountTotal) {
        if (!discounts.isEmpty()) {
            pricedItems.add(subTotalBlockItem);
            pricedItems.addAll(discounts);
            pricedItems.add(discountTotal);
        }
    }
}
