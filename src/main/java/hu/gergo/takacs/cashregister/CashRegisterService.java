package hu.gergo.takacs.cashregister;


import java.util.List;
import java.util.stream.Collectors;

public class CashRegisterService {

    public List<BlockItem> getBlock(List<Item> items) {
        List<BlockItem> pricedItems = items.stream()
                .map(item -> new PricedItem(item, calculatePrice(item)))
                .collect(Collectors.toList());
        addTotal(pricedItems);
        return pricedItems;
    }

    private void addTotal(List<BlockItem> pricedItems) {
        double total = pricedItems.stream().mapToDouble(BlockItem::getPrice).sum();
        pricedItems.add(new TotalBlockItem(total));
    }

    private double calculatePrice(Item item) {
        return item.getQuantity() * item.getItemDescription().getUnitPrice();
    }
}
