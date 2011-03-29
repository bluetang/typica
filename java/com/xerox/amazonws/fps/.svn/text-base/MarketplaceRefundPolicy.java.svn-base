package com.xerox.amazonws.fps;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum MarketplaceRefundPolicy {
    MASTER_TXN_ONLY("MasterTxnOnly"),
    MARKETPLACE_TXN_ONLY("MarketplaceTxnOnly"),
    MASTER_AND_MARKETPLACE_TXN("MasterAndMarketplaceTxn");

    private final String value;

    MarketplaceRefundPolicy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MarketplaceRefundPolicy fromValue(String v) {
        for (MarketplaceRefundPolicy c: MarketplaceRefundPolicy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
