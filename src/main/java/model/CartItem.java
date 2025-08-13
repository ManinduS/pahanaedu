package model;

import java.math.BigDecimal;

public class CartItem {
    private Item item;
    private int qty;

    public CartItem(Item item, int qty) {
        this.item = item;
        this.qty = Math.max(1, qty);
    }

    public Item getItem() { return item; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = Math.max(1, qty); }

    public BigDecimal getUnitPrice() { return item.getPrice(); }
    public BigDecimal getAmount() { return getUnitPrice().multiply(new BigDecimal(qty)); }
}
