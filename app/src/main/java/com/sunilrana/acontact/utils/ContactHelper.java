package com.sunilrana.acontact.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.sunilrana.acontact.model.ContactData;

import java.util.ArrayList;


public class ContactHelper {

    Context mContext;

    public ContactHelper(Context mContext) {
        this.mContext = mContext;
    }


    public ContactData getPhoneBookContacts(Uri contactUrl) {

        ArrayList<String> emailList = new ArrayList<>();
        ArrayList<String> mobileList = new ArrayList<>();

        ContactData contactData = null;
        Cursor contactsCursor = getContactsCursor(contactUrl);
        if (isCursorValid(contactsCursor)) {
            do {
                String contactId = contactsCursor.getString(contactsCursor.getColumnIndex(
                        ContactsContract.Contacts._ID));
                Cursor emails = getEmailsCursor(contactId);
                Cursor phones = getPHoneCursor(contactId);

                if (isCursorValid(emails) && isCursorValid(phones)) {
                    String name = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));


                    if (phones.moveToFirst()) {
                        do {
                            String phoneN = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            mobileList.add(phoneN);
                        } while (phones.moveToNext());
                        phones.close();
                    }


                    if (emails.moveToFirst()) {
                        do {
                            String emAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            emailList.add(emAddress);
                        } while (emails.moveToNext());
                        emails.close();
                    }

                    if (mobileList.size() > 0) {
                        contactData = new ContactData(contactId, name, mobileList, emailList);
                    }
                }
                closeCursor(emails);
                closeCursor(phones);
            } while (contactsCursor.moveToNext());
        }
        closeCursor(contactsCursor);

        return contactData;
    }

    private Cursor getContactsCursor(Uri contactUri) {
        String sortOrder = ContactsContract.Contacts.SORT_KEY_PRIMARY;
        String[] projection = {ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                sortOrder};
        String selection = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + "<>''" + " AND " + ContactsContract
                .Contacts.IN_VISIBLE_GROUP + "=1";

        return mContext.getContentResolver().query(contactUri, projection, selection, null, sortOrder);
    }


    private Cursor getPHoneCursor(String contactId) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId;

        return mContext.getContentResolver().query(uri, projection, selection, null, null);
    }


    private Cursor getEmailsCursor(String contactId) {
        String selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId;
        return mContext.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, selection, null, null);
    }


    private boolean isCursorValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed() && cursor.getCount() > 0 && cursor.moveToFirst();
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
