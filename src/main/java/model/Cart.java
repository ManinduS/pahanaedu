package model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();
    private BigDecimal discountPct = BigDecimal.ZERO; // 0..100

    public Collection<CartItem> getItems() { return items.values(); }
    public boolean isEmpty() { return items.isEmpty(); }

    public void add(Item item, int qty) {
        CartItem ci = items.get(item.getId());
        if (ci == null) items.put(item.getId(), new CartItem(item, qty));
        else ci.setQty(ci.getQty() + qty);
    }

    public void updateQty(int itemId, int qty) {
        CartItem ci = items.get(itemId);
        if (ci != null) ci.setQty(qty);
    }

    public void remove(int itemId) { items.remove(itemId); }
    public void clear() { items.clear(); discountPct = BigDecimal.ZERO; }

    public BigDecimal getSubtotal() {
        return items.values().stream()
                .map(CartItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountPct() { return discountPct; }
    public void setDiscountPct(BigDecimal pct) {
        if (pct == null) pct = BigDecimal.ZERO;
        if (pct.compareTo(new BigDecimal("100")) > 0) pct = new BigDecimal("100");
        if (pct.compareTo(BigDecimal.ZERO) < 0) pct = BigDecimal.ZERO;
        this.discountPct = pct;
    }

    public BigDecimal getDiscountAmount() {
        return getSubtotal().multiply(discountPct).divide(new BigDecimal("100"));
    }

    public BigDecimal getTotal() {
        return getSubtotal().subtract(getDiscountAmount());
    }
}
