package hu.gergo.takacs.cashregister;

import hu.gergo.takacs.cashregister.block.BlockItem;
import hu.gergo.takacs.cashregister.block.PricedItem;
import hu.gergo.takacs.cashregister.block.TotalBlockItem;
import hu.gergo.takacs.cashregister.purchase.Item;
import hu.gergo.takacs.cashregister.purchase.ItemDescription;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CashRegisterServiceTest {
    private CashRegisterService underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new CashRegisterService();
    }

    @Test
    public void getBlockIfNoItem() throws Exception {
        //given
        List<Item> items = Collections.emptyList();
        List<BlockItem> expected = Collections.singletonList(createTotalBlockItem(0));
        //when
        List<BlockItem> actual = underTest.getBlock(items);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockIfOneItem() throws Exception {
        //given
        double unitPrice = 2.1;
        double quantity = 2D;
        Item item = createItem("name", unitPrice, quantity);
        List<Item> items = Collections.singletonList(item);
        List<BlockItem> expected = Arrays.asList(
                createPricedItem(item, 4.2),
                createTotalBlockItem(4.2)
        );
        //when
        List<BlockItem> actual = underTest.getBlock(items);
        //then
        assertEquals(expected, actual);
    }

    private TotalBlockItem createTotalBlockItem(double total) {
        return new TotalBlockItem(total);
    }

    private PricedItem createPricedItem(Item item, double price) {
        return new PricedItem(item, price);
    }

    private Item createItem(String name, double unitPrice, double quantity) {
        ItemDescription itemDescription = new ItemDescription(name, unitPrice);
        return new Item(itemDescription, quantity);
    }


}