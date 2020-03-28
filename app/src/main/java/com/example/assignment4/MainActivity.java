package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView balance, history;
    private Button inputAdd, inputSpend;
    private EditText inputDate, inputAmount, inputPurpose;
    private DatePickerDialog inputDateDialog;
    private AlertDialog.Builder inputAmountDialog, inputPurposeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SpendingManagerDB db = new SpendingManagerDB(getApplicationContext());

        history = findViewById(R.id.history);
        history.setText(db.getHistory());

        balance = findViewById(R.id.balance);
        balance.setText(String.format("Current Balance: $%.2f", db.getBalance()));

        inputDate = findViewById(R.id.inputDate);
        inputDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputDateDialog = new DatePickerDialog(v.getContext());
                inputDateDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker d, int year, int month, int dayOfMonth) {
                        inputDate.setText(String.format("%02d/%02d/%04d", month+1, dayOfMonth, year));
                    }
                });

                inputDateDialog.show();
            }
        });

        inputAmount = findViewById(R.id.inputAmount);
        inputAmount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputAmountDialog = new AlertDialog.Builder(v.getContext());
                final EditText input = new EditText(inputAmountDialog.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setHint("Amount");
                inputAmountDialog.setView(input);

                inputAmountDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        String input_str = input.getText().toString();
                        Double input_dbl;
                        if(input_str.length() == 0) {input_dbl = 0.00;}
                        else {input_dbl = Double.parseDouble(input_str);}
                        inputAmount.setText(String.format("$%.2f", input_dbl));
                    }
                });
                inputAmountDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        d.cancel();
                    }
                });

                inputAmountDialog.show();
            }
        });

        inputPurpose = findViewById(R.id.inputPurpose);
        inputPurpose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputPurposeDialog = new AlertDialog.Builder(v.getContext());
                final EditText input = new EditText(inputPurposeDialog.getContext());
                input.setHint("use for or get from");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                inputPurposeDialog.setView(input);

                inputPurposeDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        String input_str = input.getText().toString();
                        if(input_str.length() == 0) {input_str = "No Reason";}
                        inputPurpose.setText(input_str);
                    }
                });
                inputPurposeDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int i) {
                        d.cancel();
                    }
                });

                inputPurposeDialog.show();
            }
        });

        inputAdd = findViewById(R.id.inputAdd);
        inputAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = inputDate.getText().toString();
                String amount = inputAmount.getText().toString();
                String purpose = inputPurpose.getText().toString();

                if(date.length() == 0) {
                    Toast.makeText(v.getContext(), "Your date is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(amount.length() == 0) {
                    Toast.makeText(v.getContext(), "Your amount is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(purpose.length() == 0) {
                    Toast.makeText(v.getContext(), "Your purpose is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    db.addBalance(Float.parseFloat(amount.substring(1)));
                    db.addHistory(String.format("Added %s on %s from %s\n", amount, date, purpose));
                    history.setText(db.getHistory());
                    balance.setText(String.format("Current Balance: $%.2f", db.getBalance()));
                }
            }
        });

        inputSpend = findViewById(R.id.inputSpend);
        inputSpend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = inputDate.getText().toString();
                String amount = inputAmount.getText().toString();
                String purpose = inputPurpose.getText().toString();

                if(date.length() == 0) {
                    Toast.makeText(v.getContext(), "Your date is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(amount.length() == 0) {
                    Toast.makeText(v.getContext(), "Your amount is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(purpose.length() == 0) {
                    Toast.makeText(v.getContext(), "Your purpose is empty!", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    db.addBalance(-Float.parseFloat(amount.substring(1)));
                    db.addHistory(String.format("Spent %s on %s for %s\n", amount, date, purpose));
                    history.setText(db.getHistory());
                    balance.setText(String.format("Current Balance: $%.2f", db.getBalance()));
                }
            }
        });
    }
}