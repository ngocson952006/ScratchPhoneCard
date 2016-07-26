package com.example.truongngoc.scratchphonecard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.truongngoc.scratchphonecard.service.PromotionManagerService;

/**
 * Created by Truong Ngoc Son on 7/21/2016.
 */
public class PortalCardStockServiceStartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // start the service when the device completes booting
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent promotionIntentService = new Intent(context, PromotionManagerService.class);
            // start the service
            context.startActivity(promotionIntentService);
        }
    }
}
