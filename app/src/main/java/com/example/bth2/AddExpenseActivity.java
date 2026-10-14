package com.example.bth2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText etName;
    private EditText etAmount;
    private Spinner spinnerCategory;
    private DatePicker datePicker;
    private EditText etNote;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        btnBack = findViewById(R.id.btnBack);
        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        datePicker = findViewById(R.id.datePicker);
        etNote = findViewById(R.id.etNote);
        btnSave = findViewById(R.id.btnSave);

        // Setup spinner
        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String amountStr = etAmount.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();
                String note = etNote.getText().toString().trim();

                if (name.isEmpty()) {
                    etName.setError("Vui lòng nhập tên khoản chi");
                    return;
                }
                if (amountStr.isEmpty()) {
                    etAmount.setError("Vui lòng nhập số tiền");
                    return;
                }

                long amount;
                try {
                    amount = Long.parseLong(amountStr);
                    if (amount <= 0) {
                        etAmount.setError("Số tiền phải lớn hơn 0");
                        return;
                    }
                } catch (NumberFormatException e) {
                    etAmount.setError("Số tiền không hợp lệ");
                    return;
                }

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is 0-based
                int year = datePicker.getYear();
                String date = day + "/" + month + "/" + year;

                Expense newExpense = new Expense(name, amount, category, date, note);
                ExpenseManager.getInstance().addExpense(newExpense);
                
                Toast.makeText(AddExpenseActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
