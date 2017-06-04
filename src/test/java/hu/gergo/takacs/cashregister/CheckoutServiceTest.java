package hu.gergo.takacs.cashregister;

import hu.gergo.takacs.cashregister.block.*;
import hu.gergo.takacs.cashregister.offer.Offer;
import hu.gergo.takacs.cashregister.offer.PackagePriceOffer;
import hu.gergo.takacs.cashregister.purchase.Item;
import hu.gergo.takacs.cashregister.purchase.Sku;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CheckoutServiceTest {
    private CheckoutService underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new CheckoutService();
    }

    @Test
    public void getBlockIfNoItemAndNoOffer() throws Exception {
        //given
        List<Item> items = Collections.emptyList();
        List<Offer> offers = Collections.emptyList();
        List<BlockItem> expected = Collections.singletonList(createTotalBlockItem(0));
        //when
        List<BlockItem> actual = underTest.getBlock(items, offers);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockIfMultipleItemsWithoutOffers() throws Exception {
        //given
        double unitPriceA = 3.1;
        double quantityA = 3;
        double unitPriceB = 3.5;
        double quantityB = 2;
        Item itemA = createItem("A", unitPriceA, quantityA);
        Item itemB = createItem("B", unitPriceB, quantityB);
        List<Item> items = Arrays.asList(itemA, itemB);
        List<BlockItem> expected = Arrays.asList(
                createPricedItem(itemA, 9.3),
                createPricedItem(itemB, 7),
                createTotalBlockItem(16.3)
        );
        List<Offer> offers = Collections.emptyList();
        //when
        List<BlockItem> actual = underTest.getBlock(items, offers);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockIfMultipleItemsAndMultipleOffer() throws Exception {
        //given
        double unitPriceA = 3.1;
        double quantityA = 3;
        double unitPriceB = 3.5;
        double quantityB = 10;
        String idA = "A";
        String idB = "B";
        Item itemA = createItem(idA, unitPriceA, quantityA);
        Item itemB = createItem(idB, unitPriceB, quantityB);
        List<Item> items = Arrays.asList(itemA, itemB);
        List<BlockItem> expected = Arrays.asList(
                createPricedItem(itemA, 9.3),
                createPricedItem(itemB, 35),
                new TotalBlockItem(44.3, BlockTotalType.SUB_TOTAL),
                new DiscountBlockItem(itemA.getSku(), 2, -1.7),
                new DiscountBlockItem(itemB.getSku(), 10, -5),
                new TotalBlockItem(-6.7, BlockTotalType.DISCOUNT_TOTAL),
                createTotalBlockItem(37.6)
        );
        List<Offer> offers = Arrays.asList(
                new PackagePriceOffer(itemA.getSku(), 2, 4.5),
                new PackagePriceOffer(itemB.getSku(), 5, 15)
        );
        //when
        List<BlockItem> actual = underTest.getBlock(items, offers);
        //then
        assertEquals(expected, actual);
    }

    private TotalBlockItem createTotalBlockItem(double total) {
        return new TotalBlockItem(total, BlockTotalType.GRAND_TOTAL);
    }

    private PricedItem createPricedItem(Item item, double price) {
        return new PricedItem(item, price);
    }

    private Item createItem(String id, double unitPrice, double quantity) {
        Sku sku = new Sku(id, unitPrice);
        return new Item(sku, quantity);
    }


}