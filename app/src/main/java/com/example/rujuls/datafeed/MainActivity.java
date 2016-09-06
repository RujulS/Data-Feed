package com.example.rujuls.datafeed;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "com.example.rujuls.datafeed";
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;

    DBHandler d ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes();
            }
        });

        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        e4 = (EditText) findViewById(R.id.editText4);

        d = new DBHandler(this,null,null,2);

    }

    public void yes(){

        Log.i(TAG,"I was Clicked.");
        final String fileName = "/Rujul;/FieldList.xls";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        Uri u1  =   Uri.fromFile(file);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Details");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/richtext");
        startActivity(sendIntent);
    }

    public void addData(View v){

        String name = e1.getText().toString();
        long phone = Long.parseLong(e2.getText().toString());
        String mail =e3.getText().toString();
        String address =  e4.getText().toString();



            if (name.matches("")) {

                Toast.makeText(this, "You did not enter a Name", Toast.LENGTH_SHORT).show();
                return;

            } else if (phone == 0) {

                Toast.makeText(this, "You did not enter Phone No.", Toast.LENGTH_SHORT).show();
                return;
            } else if (mail.matches("")) {

                Toast.makeText(this, "You did not enter E-Mail", Toast.LENGTH_SHORT).show();
                return;
            } else if (address.matches("")) {

                Toast.makeText(this, "You did not enter Address", Toast.LENGTH_SHORT).show();
                return;
            } else {
                // DataFields f = new DataFields(name, phone, mail, address);
                DataFields f = new DataFields(name, phone, mail, address);
                d.addProduct(f);

                e1.setText("");
                e2.setText("0");
                e3.setText("");
                e4.setText("");
                //printDatabase();

                //
                Context context = getApplicationContext();
                CharSequence text = "Data Added";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Cursor cursor = d.fetch();
                exportToExcel(cursor);

            }



    }

    public void delete(View v){

        String input = e1.getText().toString();
        d.deleteProduct(input);
        Cursor cursor = d.fetch();
        exportToExcel(cursor);

    }


    public void printDatabase() {

        //TextView t = (TextView) findViewById(R.id.textView);
        //String dbString = d.databaseToString();
        //t.setText(dbString);
        ///t.setText("");
    }



    public void exportToExcel(Cursor cursor) {
        final String fileName = "FieldList.xls";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();

        File directory = new File(sdCard.getAbsolutePath() + "/Rujul;");
        Log.i(TAG, "Path Created");

        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(file, wbSettings);

            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("MyList", 0);

            try {
                sheet.addCell(new Label(0, 0, "ID")); // column and row
                sheet.addCell(new Label(1, 0, "NAME"));
                sheet.addCell(new Label(2, 0, "PHONE"));
                sheet.addCell(new Label(3, 0, "E-MAIL"));
                sheet.addCell(new Label(4, 0, "ADDRESS"));

                if (cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndex(d.COLUMN_ID));
                        String name = cursor.getString(cursor.getColumnIndex(d.COLUMN_NAME));
                        String phone = cursor.getString(cursor.getColumnIndex(d.COLUMN_PHONE));
                        String mail = cursor.getString(cursor.getColumnIndex(d.COLUMN_MAIL));
                        String address = cursor.getString(cursor.getColumnIndex(d.COLUMN_ADDRESS));

                        int i = cursor.getPosition() + 1;

                        sheet.addCell(new Label(0, i, id));
                        sheet.addCell(new Label(1, i, name));
                        sheet.addCell(new Label(2, i, phone));
                        sheet.addCell(new Label(3, i, mail));
                        sheet.addCell(new Label(4, i, address));

                    } while (cursor.moveToNext());
                }

                //closing cursor
                cursor.close();

            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }

            workbook.write();

            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            Toast.makeText(this, "CREATED BY: Rujul Shringarpure      CONTACT:9967935222", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
