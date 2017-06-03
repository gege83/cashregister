package hu.gergo.takacs.cashregister;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        List<Item> items = new ArrayList<>();
        List<BlockItem> expected = Collections.singletonList(new TotalBlockItem(0));
        //when
        List<BlockItem> actual = underTest.getBlock(items);
        //then
        assertEquals(expected, actual);
    }
}