package com.globalsion.helloworld.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.globalsion.helloworld.MainActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExportFunc {

    private DBHelper mydb ;

    public void exportCSV(Context ct) throws IOException {
        {

            mydb = new DBHelper(ct);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AssetTracker");

                boolean var = false;
                if (!folder.exists())
                    var = folder.mkdir();

                System.out.println("" + var);

            final String filename = folder + "/" + "AssetTracker_" + currentDateandTime + ".csv";

            // show waiting screen
            final ProgressDialog progDailog = ProgressDialog.show(
                    ct, "Asset Tracker", "Export Data in Excel......",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    UIHelper.ToastMessage(ct, "File was Exported in Asset Tracker Folder");

                }
            };

            new Thread() {
                public void run() {
                    try {

                        Cursor cursor = mydb.getAllAssetCursor();
                        if ( cursor != null && cursor.getCount() > 0) {

                            FileWriter fw = new FileWriter(filename);

                            fw.append("Asset Code");
                            fw.append(',');

                            fw.append("Department");
                            fw.append(',');

                            fw.append("Cost Centre");
                            fw.append(',');

                            fw.append("Purchase Date");
                            fw.append(',');

                            fw.append("Description");
                            fw.append(',');

                            fw.append("Location");
                            fw.append(',');

                            fw.append("Scan at");

                            fw.append('\n');

                            if (cursor.moveToFirst()) {
                                do {
                                    fw.append(cursor.getString(1));
                                    fw.append(',');

                                    fw.append(cursor.getString(2));
                                    fw.append(',');

                                    fw.append(cursor.getString(3));
                                    fw.append(',');

                                    fw.append(cursor.getString(4));
                                    fw.append(',');

                                    fw.append('"' + cursor.getString(5) + '"');
                                    fw.append(',');

                                    fw.append(cursor.getString(6));
                                    fw.append(',');

                                    fw.append(cursor.getString(7));
                                    fw.append('\n');

                                } while (cursor.moveToNext());
                            }
                            if (cursor != null && !cursor.isClosed()) {
                                cursor.close();
                            }

                            // fw.flush();
                            fw.close();

                            mydb.deleteAllAsset();
                        }
//                        else{
//                            UIHelper.ToastMessage(ct, "No Asset List Found to Export !");
//                        }

                    } catch (Exception e) {
                        UIHelper.ToastMessage(ct, e.getMessage());
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                }
            }.start();

        }

    }

}
