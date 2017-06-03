package hu.gergo.takacs.cashregister;


import java.util.Collections;
import java.util.List;

public class CashRegisterService {

    public List<BlockItem> getBlock(List<Item> items) {
        return Collections.singletonList(new TotalBlockItem(0));
    }
}
