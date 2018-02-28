package com.sunilrana.acontact.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunilrana.acontact.BuildConfig;
import com.sunilrana.acontact.R;
import com.sunilrana.acontact.model.ContactData;
import com.sunilrana.acontact.permission.Action;
import com.sunilrana.acontact.permission.PermissionAction;
import com.sunilrana.acontact.permission.PermissionActionFactory;
import com.sunilrana.acontact.permission.PermissionPresenter;
import com.sunilrana.acontact.permission.PermissionPresenter.PermissionCallbacks;
import com.sunilrana.acontact.utils.ContactHelper;

import static com.sunilrana.acontact.permission.PermissionActionFactory.SUPPORT_IMPL;

/**
 * Created by sunilrana on 28/02/18.
 */

public class ContactSelectionActivity extends Activity implements PermissionCallbacks {

    private static final String TAG = ContactSelectionActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;


    private PermissionPresenter permissionPresenter;



    TextView mNameTv, mPhoneTv, mEmailTv;
    Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_selection);


        PermissionActionFactory permissionActionFactory = new PermissionActionFactory(this);
        PermissionAction permissionAction = permissionActionFactory.getPermissionAction(SUPPORT_IMPL);

        permissionPresenter = new PermissionPresenter(permissionAction, this);


        mNameTv = (TextView)findViewById(R.id.name);
        mEmailTv = (TextView)findViewById(R.id.email);
        mPhoneTv = (TextView)findViewById(R.id.phone);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionPresenter.requestReadContactsPermission();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {

            uriContact = data.getData();

            ContactData contactData = new ContactHelper(this).getPhoneBookContacts(uriContact);

            if(contactData != null) {
                Log.d(TAG, "Contact name: " + contactData.getName());
                Log.d(TAG, "Contact Email: " + contactData.getEmail());
                Log.d(TAG, "Contact Mobile: " + contactData.getPhone());
                Log.d(TAG, "Contact Id: " + contactData.getId());

                mNameTv.setText(contactData.getName());
                mEmailTv.setText(contactData.getEmail());
                mPhoneTv.setText(contactData.getPhone());
            }else{
                Toast.makeText(this, "Mobile & email is not available.", Toast.LENGTH_LONG).show();
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Action.ACTION_CODE_READ_CONTACTS:
                permissionPresenter.checkGrantedPermission(grantResults, requestCode);
        }
    }


    @Override
    public void permissionAccepted(int action) {
        switch (action) {
            case Action.ACTION_CODE_READ_CONTACTS:
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
                break;

        }
    }

    @Override
    public void permissionDenied(int action) {
        switch (action) {
            case Action.ACTION_CODE_READ_CONTACTS:
                showSnackBarPermissionMessage(R.string.snackbar_read_contacts);
                PhoneNumberUtils.getStrippedReversed("");
                break;
        }
    }



    protected void showSnackBarPermissionMessage(int message) {
        final RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.rootView);
        Snackbar snackbar = Snackbar.make(rootLayout, getString(message), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snackbar_settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings
                                .ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(intent);
                    }
                });
        snackbar.show();
    }


}