package com.globalsion.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView txtAccountNo;
    TextView txtBalance;
    Button btnLogOut;
    Button btnCancel;
    Button btnRM20;
    Button btnRM50;
    Button btnRM100;
    Button btnRetrieve;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String defaultSerialNo = "123456789";
        double defaultBalance = 0.00;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String formattedBalance = decimalFormat.format(defaultBalance);

        txtAccountNo = (TextView)findViewById(R.id.txtAccountNo);
        txtBalance = (TextView)findViewById(R.id.txtBalance);
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnRetrieve = (Button)findViewById(R.id.btnRetrieve);

        txtAccountNo = (TextView) findViewById(R.id.txtAccountNo);
        txtBalance = (TextView) findViewById(R.id.txtBalance);

        btnRM20 = (Button)findViewById(R.id.btnRM20);
        btnRM50 = (Button)findViewById(R.id.btnRM50);
        btnRM100 = (Button)findViewById(R.id.btnRM100);

//        txtAccountNo.setText(defaultSerialNo);
        txtBalance.setText(String.valueOf(formattedBalance));

        btnLogOut.setOnClickListener(new BtnLogOutClickListener());
        btnCancel.setOnClickListener(new BtnCancelClickListener(txtAccountNo, txtBalance));

        btnRetrieve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Connection connection = SQLConnection();
                try {
                    if (connection != null) {
                        String query = "SELECT SerialNo, Balance FROM [Account]";
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        while (rs.next()) {
                            txtAccountNo.setText(rs.getString(1));
                            txtBalance.setText(rs.getString(2));
                        }
                    }
                }catch(Exception ex){
                    Log.e("Error ", ex.getMessage());
                }
            }
        });

        btnRM20.setOnClickListener(new BtnTopUp20ClickListener(txtBalance));
        btnRM50.setOnClickListener(new BtnTopUp50ClickListener(txtBalance));
        btnRM100.setOnClickListener(new BtnTopUp100ClickListener(txtBalance));
    }

    Connection connection = null;
    @SuppressLint("NewApi")
    public Connection SQLConnection() {
        Connection conn;
        String serverName = "DESKTOP-R4FGAIN";
        String port = "1433";
        String databaseName = "TopUp";
        String userName = "admin";
        String password = "adminP@55w0rd";
        String instanceName = "MSSQL2014"; // Replace with your actual instance name

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String connectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + serverName + ":" + port + "/" + databaseName + ";instance=" + instanceName;
            conn = DriverManager.getConnection(connectionURL, userName, password);
        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
            conn = null;
        }

        return conn;
    }

    public class BtnLogOutClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    public class BtnCancelClickListener implements View.OnClickListener{

        private TextView txtAccountNo;
        private TextView txtBalance;

        // Constructor to initialize the TextViews
        public BtnCancelClickListener(TextView txtAccountNo, TextView txtBalance) {
            this.txtAccountNo = txtAccountNo;
            this.txtBalance = txtBalance;
        }

        @Override
        public void onClick(View v){
            // Clear the text of txtAccountNo
            if (txtAccountNo != null) {
                txtAccountNo.setText("");
            }
            // Reset the balance to default balance
            if (txtBalance != null) {
                txtBalance.setText("0.00");
            }
        }
    }

    public class BtnTopUp20ClickListener implements View.OnClickListener{

        private TextView txtBalance;
        public BtnTopUp20ClickListener(TextView txtBalance){
            this.txtBalance = txtBalance;
        }

        @Override
        public void onClick(View v){
            String currentBalanceString = txtBalance.getText().toString();

            // Convert the String to a double
            double currentBalance = Double.parseDouble(currentBalanceString);

            // Add 20 to the balance
            double newBalance = currentBalance + 20.00;

            // Format the new balance with two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedNewBalance = decimalFormat.format(newBalance);

            // Set the updated balance back to the TextView
            txtBalance.setText(String.valueOf(formattedNewBalance));
        }
    }

    public class BtnTopUp50ClickListener implements View.OnClickListener{

        private TextView txtBalance;
        public BtnTopUp50ClickListener(TextView txtBalance){
            this.txtBalance = txtBalance;
        }

        @Override
        public void onClick(View v){
            String currentBalanceString = txtBalance.getText().toString();

            // Convert the String to a double
            double currentBalance = Double.parseDouble(currentBalanceString);

            // Add 20 to the balance
            double newBalance = currentBalance + 50.00;

            // Format the new balance with two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedNewBalance = decimalFormat.format(newBalance);

            // Set the updated balance back to the TextView
            txtBalance.setText(String.valueOf(formattedNewBalance));
        }
    }

    public class BtnTopUp100ClickListener implements View.OnClickListener{

        private TextView txtBalance;
        public BtnTopUp100ClickListener(TextView txtBalance){
            this.txtBalance = txtBalance;
        }

        @Override
        public void onClick(View v){
            String currentBalanceString = txtBalance.getText().toString();

            // Convert the String to a double
            double currentBalance = Double.parseDouble(currentBalanceString);

            // Add 20 to the balance
            double newBalance = currentBalance + 100.00;

            // Format the new balance with two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedNewBalance = decimalFormat.format(newBalance);

            // Set the updated balance back to the TextView
            txtBalance.setText(String.valueOf(formattedNewBalance));
        }
    }
}