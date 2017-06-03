package hu.gergo.takacs.cashregister;

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
        List<BlockItem> expected = Collections.singletonList(new TotalBlockItem(0));
        //when
        List<BlockItem> actual = underTest.getBlock(items);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockIfOneItem() throws Exception {
        //given
        String name = "name";
        double unitPrice = 2.1;
        double quantity = 2;
        ItemDescription itemDescription = new ItemDescription(name, unitPrice);
        Item item = new Item(itemDescription, quantity);
        List<Item> items = Collections.singletonList(item);
        List<BlockItem> expected = Arrays.asList(
                new PricedItem(item, 4.2),
                new TotalBlockItem(4.2)
        );
        //when
        List<BlockItem> actual = underTest.getBlock(items);
        //then
        assertEquals(expected, actual);
    }
}