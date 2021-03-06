package com.example.steven.safewalk;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.net.URI;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Steven on 12/12/2016.
 */

public class ContactsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_THUMBNAIL_URI

            };

    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;


    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the LOOKUP_KEY column
    private static final int LOOKUP_KEY_INDEX = 1;

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String mSearchString;
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };




    public ContactsFragment() {}

    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.contact_list_fragment,
                container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //EditText editText = (EditText) getActivity().findViewById(R.id.editText);
        //editText.setText("Stuff amd things");
        /*getLoaderManager().initLoader(0, null, this);
        // Gets the ListView from the View list of the parent activity
        //mContactsList =
        //        (ListView) getActivity().findViewById(R.id.contact_list_view);
        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_items,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        mCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                *//*if(view.getId() == R.id.contact_image) {
                    *//**//*Bitmap[] array = new Bitmap[2];
                        byte[] data = cursor.getBlob(columnIndex);
                        if (data != null) {
                            array[0] = BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                        }
                    if (array[0] != null) {
                        ((ImageView) view).setImageBitmap(array[0]);
                    }*//**//*
                    Bitmap myImg = BitmapFactory.decodeFile(cursor.getString(columnIndex));
                    if(cursor.getString(3) != null) {
                        ((ImageView) view).setImageBitmap(myImg);
                    }
                    return true;
                }*//*
                return false;
            }
        });
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);

        // Set the item click listener to be the current fragment.
        mContactsList.setOnItemClickListener(this);*/
    }

    public Uri getPhotoUri(Integer contactid) {
        Cursor photoCur = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'", null, ContactsContract.Contacts.DISPLAY_NAME+" COLLATE LOCALIZED ASC");
        photoCur.moveToPosition(contactid);
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, photoCur.getLong(photoCur.getColumnIndex(ContactsContract.Contacts._ID)));
        Uri photo = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        return photo;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        mSelectionArgs[0] = "%" + "" + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC"
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
    }

    //@Override
    public void onItemClick(AdapterView<?> parent, View item, int position, long rowID) {
           // Get the Cursor
            Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
            // Move to the selected contact
            cursor.moveToPosition(position);
            // Get the _ID value
            mContactId = cursor.getLong(CONTACT_ID_INDEX);
            // Get the selected LOOKUP KEY
            mContactKey = cursor.getString(LOOKUP_KEY_INDEX);
            // Create the contact's content Uri
            mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */
        //startContactDetailsActivity();
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 1);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case 1:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            SmsManager.getDefault().sendTextMessage("4806008313", null, "test message", null, null);
            // Set the value to the textviews
            //textView1.setText(name);
            //textView2.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startContactDetailsActivity() {
        Intent contactDetailsIntent = new Intent(getActivity(), ContactDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("contactKey", mContactKey);
        contactDetailsIntent.putExtras(bundle);
        startActivity(contactDetailsIntent, bundle);
    }
}
