package com.example.truongngoc.scratchphonecard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.ActionBar;


/**
 * A login screen that offers login via email/password.
 */
public class RechargeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RechargeTask mAuthTask = null;

    // UI references.
    //  private AutoCompleteTextView mEmailView;
    private EditText cardCodeView;
    private View mProgressView;
    private View mLoginFormView;
    private Spinner listCardPriceSpinner;
    // about radio buttons
    private RadioButton viettelRadioButton;
    private RadioButton mobifoneRadioButton;
    private RadioButton vietnamobileRadioButton;
    private RadioButton vinaphoneRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // request WINDOW_FEATTURE
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.recharge_activity);


        // arimo fonts
        final Typeface TYPEFACE_ARIMO_BOLD = Typeface.createFromAsset(this.getAssets(), "fonts/Arimo-Bold.ttf");
        final Typeface TYPEFACE_ARIMO_REGULAR = Typeface.createFromAsset(this.getAssets(), "fonts/Arimo-Regular.ttf");
        final Typeface TYPEFACE_ARIMO_LIGHT = Typeface.createFromAsset(this.getAssets(), "fonts/Arimo-Italic.ttf");
        final Typeface TYPEFACE_ARIMO_BOLD_ITALIC = Typeface.createFromAsset(this.getAssets(), "fonts/Arimo-BoldItalic.ttf");

        // Set up the login form.
        // mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();
        //get support action bar
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            //You do not need to catch the up action in the activity's onOptionsItemSelected() method. Instead, that method should call its superclass, as shown in Respond to Actions.
            // The superclass method responds to the Up selection by navigating to the parent activity, as specified in the app manifest.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.cardCodeView = (EditText) findViewById(R.id.card_code);
        this.cardCodeView.setTypeface(TYPEFACE_ARIMO_BOLD);
        this.cardCodeView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRecharge();
                    return true;
                }
                return false;
            }
        });

//        Button rechargeButton = (Button) findViewById(R.id.email_sign_in_button);
//        rechargeButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptRecharge();
//            }
//        });


        final TextView chooseProviderTextView = (TextView) this.findViewById(R.id.provider_name_header);
        final TextView choosePriceTextView = (TextView) this.findViewById(R.id.price_header_text_view);
        final TextView cardCodeTextView = (TextView) this.findViewById(R.id.card_code_header);

        chooseProviderTextView.setTypeface(TYPEFACE_ARIMO_REGULAR);
        choosePriceTextView.setTypeface(TYPEFACE_ARIMO_REGULAR);
        cardCodeTextView.setTypeface(TYPEFACE_ARIMO_REGULAR);


        // Radio buttons
        this.viettelRadioButton = (RadioButton) this.findViewById(R.id.viettel__radio_button);
        this.viettelRadioButton.setTypeface(TYPEFACE_ARIMO_REGULAR);
        this.vietnamobileRadioButton = (RadioButton) this.findViewById(R.id.vietnamobile__radio_button);
        this.vietnamobileRadioButton.setTypeface(TYPEFACE_ARIMO_REGULAR);
        this.mobifoneRadioButton = (RadioButton) this.findViewById(R.id.mobifone__radio_button);
        this.mobifoneRadioButton.setTypeface(TYPEFACE_ARIMO_REGULAR);
        this.vinaphoneRadioButton = (RadioButton) this.findViewById(R.id.vinaphone__radio_button);
        this.vinaphoneRadioButton.setTypeface(TYPEFACE_ARIMO_REGULAR);


        this.listCardPriceSpinner = (Spinner) this.findViewById(R.id.spinner_price_list);
        if (this.listCardPriceSpinner != null) {
            this.listCardPriceSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, this.getResources().getStringArray(R.array.card_price_list)));
            // register item click listener
            //this.listCardPriceSpinner.setOnItemClickListener(this);
            this.listCardPriceSpinner.setOnItemSelectedListener(this);
        }


        mLoginFormView = findViewById(R.id.recharge_form);
        mProgressView = findViewById(R.id.login_progress);
    }

//    private void populateAutoComplete() {
//        getLoaderManager().initLoader(0, null, this);
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRecharge() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        // mEmailView.setError(null);
        cardCodeView.setError(null);

        // Store values at the time of the login attempt.
        // String email = mEmailView.getText().toString();
        final String cardCode = cardCodeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(cardCode)) {
            cardCodeView.setError(getString(R.string.error_field_required));
            focusView = cardCodeView;
            cancel = true;
        }

        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);
            // make a call
            final String telPrefix = "tel:*100*";
            final String telSuffix = cardCode + "#";
            Intent rechargeIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(telPrefix + Uri.encode(telSuffix)));
            startActivity(rechargeIntent);
            // mAuthTask = new RechargeTask(email, password); // need to modify code here
            // mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.recharge_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            this.attemptRecharge();

//            // get card code from the edit text
//            final String cardCode = this.cardCodeView.getText().toString();
//
//            final String telPrefix = "tel:*100*";
//            final String telSuffix = cardCode + "#";
//            Intent rechargeIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(telPrefix + Uri.encode(telSuffix)));
//            startActivity(rechargeIntent);
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }

//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(RechargeActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous recharging task used to recharge
     */
    public class RechargeTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        RechargeTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                finish();
            } else {
                cardCodeView.setError(getString(R.string.error_incorrect_password));
                cardCodeView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

