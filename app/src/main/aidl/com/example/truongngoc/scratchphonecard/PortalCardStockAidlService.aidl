// PortalCardStockAidlService.aidl
package com.example.truongngoc.scratchphonecard;

// Declare any non-default types here with import statements
import com.example.truongngoc.scratchphonecard.domain.PortalCard;

interface PortalCardStockAidlService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
            /*
            * insert new user recent paied portal card
            */
    void addNewHistory(in PortalCard newHistoryCard);

    void saveForNextPromotionEvent(in PortalCard card);

}
