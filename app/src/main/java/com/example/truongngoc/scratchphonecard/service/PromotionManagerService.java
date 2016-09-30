package com.example.truongngoc.scratchphonecard.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.truongngoc.scratchphonecard.PortalCardStockAidlService;
import com.example.truongngoc.scratchphonecard.domain.PortalCard;


/**
 * Service do backgroud tasks to get promotion and save into database, show notification to user
 * Created by Truong Ngoc Son on 7/19/2016.
 */
public class PromotionManagerService extends Service {
    private static final String TAG = PromotionManagerService.class.getSimpleName(); // tag


    /**
     * An implementation of AIDL Interface that contains operations
     */
    private final class PortalCardStockServiceImpl extends PortalCardStockAidlService.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            // doing something here
            Log.d(TAG, "PortalCardStockServiceImpl -> working at basicTypes method");
        }

        @Override
        public void addNewHistory(PortalCard newHistoryCard) throws RemoteException {
            Log.d(TAG, "PortalCardStockServiceImpl -> working at addNewHistory method");
        }

        @Override
        public void saveForNextPromotionEvent(PortalCard card) throws RemoteException {
            Log.d(TAG, "PortalCardStockServiceImpl -> working at saveForNextPromotionEvent method");
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, TAG + " started", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PortalCardStockServiceImpl(); // return the implementation of the aidl interface
    }
}
