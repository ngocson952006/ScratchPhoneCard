package com.example.truongngoc.scratchphonecard;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truongngoc.scratchphonecard.service.PromotionManagerService;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.truongngoc.scratchphonecard.R.id.see_more_textview;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class
            .getSimpleName(); // tag

    // Distances to the primary Floating Action Button
    private static float RIGHT_MARGIN_INDEX_1 = 1.7f;
    private static float BOTTOM_MARGIN_INDEX_1 = 0.25f;
    private static float RIGHT_MARGIN_INDEX_2 = 1.5f;
    private static float BOTTOM_MARGIN_INDEX_2 = 1.5f;
    private static float RIGHT_MARGIN_INDEX_3 = 0.25f;
    private static float BOTTOM_MARGIN_INDEX_3 = 1.75f;

    // view components
    private FloatingActionButton primaryFab;
    private FloatingActionButton subFabActionAdd;
    private FloatingActionButton subFabActionSaveForNextPromotion;
    private FloatingActionButton subFabForward;

    private boolean isBounded = false; // flag to identify that the service is bound
    private boolean shouldShowSubFloatingButtons = false; // flag to identify the sub floating action buttons was shown or hidden

    private PortalCardStockAidlService portalCardStockAidlService; // a generated service interface
    // a service connection which connects to service (Promotion )
    private final ServiceConnection portalCardStockServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // get the implementation of the aidl generated service interface
            portalCardStockAidlService = PortalCardStockAidlService.Stub.asInterface(service);
            isBounded = true;
            // start an operation with this
            Toast.makeText(MainActivity.this, "connected to Service", Toast.LENGTH_LONG).show();
            try {
                portalCardStockAidlService.addNewHistory(null); // simply,  just show a Toast
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            portalCardStockAidlService = null;
            isBounded = false;
            //
            Toast.makeText(MainActivity.this, "disconnected to Service", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // make transition animation for Android 5 and higher
            this.getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
            this.getWindow().setEnterTransition(new Explode());
            this.getWindow().setExitTransition(new Explode());
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up icon for action bar
        ActionBar actionBar = this.getSupportActionBar();


        // floating action button zone
        this.primaryFab = (FloatingActionButton) findViewById(R.id.fab);
        // build sub menu

        primaryFab.setOnClickListener(this);

        this.subFabActionAdd = (FloatingActionButton) this.findViewById(R.id.sub_fab_action_add);
        this.subFabActionAdd.setOnClickListener(this);
        this.subFabForward = (FloatingActionButton) this.findViewById(R.id.sub_fab_forward);
        this.subFabForward.setOnClickListener(this);
        this.subFabActionSaveForNextPromotion = (FloatingActionButton) this.findViewById(R.id.sub_fab_action_save_for_next_promotion);
        this.subFabActionSaveForNextPromotion.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    final ListView recentCardsListView = (ListView) this.findViewById(R.id.recent_cards_listview);
            //  recentCardsListView.setEmptyView(this.findViewById(R.id.empty_list_view_holder)); // handle when the list view is empty
            final RecyclerView recentRecyclerView = (RecyclerView) this.findViewById(R.id.recent_cards_listview);

        } else {
            final ListView recentListView = (ListView) this.findViewById(R.id.recent_cards_listview);
            if (recentListView != null) {
                recentListView.setEmptyView(this.findViewById(R.id.empty_list_view_holder));
                // start to set adapter
            }
        }

        final TextView seeMoreTextView = (TextView) this.findViewById(R.id.see_more_textview);
        final String promotionInformationLink = "<a href=\"https://vienthong.com.vn/tin-tuc/tin-khuyen-mai/\">" + this.getString(R.string.see_more) + "</a>";
        seeMoreTextView.setText(Html.fromHtml(promotionInformationLink));
        seeMoreTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();

                if (!this.shouldShowSubFloatingButtons) {
                    this.showAllSubFloatingActionButton();
                } else {
                    this.hideAllFloatingActionButton();
                }
                // this.primaryFab.setVisibility(View.INVISIBLE);
                break;
            case R.id.sub_fab_action_add:
                // move to recharge activity
                Intent cameraIntent = new Intent(MainActivity.this, RechargeActivity.class);
                // hide all the sub floating action buttons before changing activity
                if (this.shouldShowSubFloatingButtons) {
                    this.hideAllFloatingActionButton();
                }
                //  start with explode animationnimation
                startActivity(cameraIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.sub_fab_action_save_for_next_promotion:
                // move to recharge activity

                break;
            case R.id.sub_fab_forward:
                // move to recharge activity

                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // start to bind Service
        this.bindService(new Intent(this, PromotionManagerService.class), this.portalCardStockServiceConnection, Context.BIND_AUTO_CREATE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.truongngoc.scratchphonecard/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.truongngoc.scratchphonecard/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // unbind service
        if (this.isBounded) {
            this.unbindService(this.portalCardStockServiceConnection);
            this.isBounded = false;
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }


    /**
     * Set animation with fit location and transition for the sub menu action.
     * When use click the primary Floating Action Button , the sub Floating Action Button (sub menu) will
     * be transited and shown to user. Otherwise , it will be hidden.
     * CAUTION :
     * Caution!
     * While creating these animations I encountered the issue of touch events and small FABs.
     * When the animation finishes the actual position of the small FABs does not change, only the view appears in the new position so you can’t actually perform touch events on the correct position.
     * What I did to fix this issue was to set the layout parameters of each FAB to its new location and then perform the animation of pulling the view to the new position.
     * All the code is referenced by this link : https://www.sitepoint.com/animating-android-floating-action-button/
     *
     * @param view : the view to be shown
     */
    private void showSubFloatingActionButton(View view) {
        // first we need to get LayoutParams of the View to update
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        // update the right and left margin
        if (view.getId() == R.id.sub_fab_action_add) {
            layoutParams.rightMargin += (int) (view.getWidth() * RIGHT_MARGIN_INDEX_1);
            layoutParams.bottomMargin += (int) (view.getHeight() * BOTTOM_MARGIN_INDEX_1);
            view.setLayoutParams(layoutParams); // update the layoutparams
            // set in animation
            Animation showAnimation = AnimationUtils.loadAnimation(this, R.anim.sub_menu_fab1_show);
            view.startAnimation(showAnimation);
            view.setClickable(true);


        } else if (view.getId() == R.id.sub_fab_action_save_for_next_promotion) {
            layoutParams.rightMargin += (int) (view.getWidth() * RIGHT_MARGIN_INDEX_2);
            layoutParams.bottomMargin += (int) (view.getHeight() * BOTTOM_MARGIN_INDEX_2);
            view.setLayoutParams(layoutParams);  // update the layoutparams
            // set in animation
            Animation showAnimation = AnimationUtils.loadAnimation(this, R.anim.sub_menu_fab2_show);
            view.startAnimation(showAnimation);
            view.setClickable(true);

        } else if (view.getId() == R.id.sub_fab_forward) {
            layoutParams.rightMargin += (int) (view.getWidth() * RIGHT_MARGIN_INDEX_3);
            layoutParams.bottomMargin += (int) (view.getHeight() * BOTTOM_MARGIN_INDEX_3);
            view.setLayoutParams(layoutParams);  // update the layoutparams
            // set in animation
            Animation showAnimation = AnimationUtils.loadAnimation(this, R.anim.sub_menu_fab3_show);
            view.startAnimation(showAnimation);
            view.setClickable(true);
        }
    }


    /**
     * Set animation with fit location and transition for the sub menu action.
     * When use click the primary Floating Action Button , the sub Floating Action Button (sub menu) will
     * be transited and shown to user. Otherwise , it will be hidden.
     * CAUTION :
     * Caution!
     * While creating these animations I encountered the issue of touch events and small FABs.
     * When the animation finishes the actual position of the small FABs does not change, only the view appears in the new position so you can’t actually perform touch events on the correct position.
     * What I did to fix this issue was to set the layout parameters of each FAB to its new location and then perform the animation of pulling the view to the new position.
     * All the code is referenced by this link : https://www.sitepoint.com/animating-android-floating-action-button/
     *
     * @param view : the view to be hidden
     */
    private void hideSubFloatingActionButton(View view) {
        // first we need to get LayoutParams of the View to update
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        // update the right and left margin
        if (view.getId() == R.id.sub_fab_action_add) {
            layoutParams.rightMargin -= (int) (view.getWidth() * RIGHT_MARGIN_INDEX_1);
            layoutParams.bottomMargin -= (int) (view.getHeight() * BOTTOM_MARGIN_INDEX_1);
            view.setLayoutParams(layoutParams); // update the layoutparams
            // set in animation
            Animation hiddenAnimation = AnimationUtils.loadAnimation(this, R.anim.sub_menu_fab1_hidden);
            view.startAnimation(hiddenAnimation);
            view.setClickable(false);


        } else if (view.getId() == R.id.sub_fab_action_save_for_next_promotion) {
            layoutParams.rightMargin -= (int) (view.getWidth() * RIGHT_MARGIN_INDEX_2);
            layoutParams.bottomMargin -= (int) (view.getHeight() * BOTTOM_MARGIN_INDEX_2);
            view.setLayoutParams(layoutParams);  // update the layoutparams
            // set in animation
            Animation hiddenAnimation = AnimationUtils.loadAnimation(this, R.anim.sub_menu_fab2_hidden);
            view.startAnimation(hiddenAnimation);
            view.setClickable(false);

        } else if (view.getId() == R.id.sub_fab_forward) {
            layoutParams.rightMargin -= (int) (view.getWidth() * RIGHT_MARGIN_INDEX_3);
            layoutParams.bottomMargin -= (int) (view.getHeight() * BOTTOM_MARGIN_INDEX_3);
            view.setLayoutParams(layoutParams);  // update the layoutparams
            // set in animation
            Animation hiddenAnimation = AnimationUtils.loadAnimation(this, R.anim.sub_menu_fab3_hidden);
            view.startAnimation(hiddenAnimation);
            view.setClickable(false);
        }
    }

    /**
     * Show all floating action buttons.
     * After showing all the buttons, we update the value of the flag shouldShowSubFloatingButtons for the next showing or hiding
     */
    private void showAllSubFloatingActionButton() {
        this.showSubFloatingActionButton(this.subFabActionAdd);
        this.showSubFloatingActionButton(this.subFabActionSaveForNextPromotion);
        this.showSubFloatingActionButton(this.subFabForward);
        this.shouldShowSubFloatingButtons = true;
    }

    /**
     * Hide all floating action buttons.
     * After showing all the buttons, we update the value of the flag shouldShowSubFloatingButtons for the next showing or hiding
     */

    private void hideAllFloatingActionButton() {
        this.hideSubFloatingActionButton(this.subFabActionAdd);
        this.hideSubFloatingActionButton(this.subFabActionSaveForNextPromotion);
        this.hideSubFloatingActionButton(this.subFabForward);
        this.shouldShowSubFloatingButtons = false;
    }


}
