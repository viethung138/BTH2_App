package com.example.bth2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ExpenseDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvName;
    private TextView tvAmount;
    private TextView tvCategory;
    private TextView tvDate;
    private TextView tvNote;
    private Button btnGoBack;
    private Button btnDelete;

    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        btnBack = findViewById(R.id.btnBack);
        tvName = findViewById(R.id.tvName);
        tvAmount = findViewById(R.id.tvAmount);
        tvCategory = findViewById(R.id.tvCategory);
        tvDate = findViewById(R.id.tvDate);
        tvNote = findViewById(R.id.tvNote);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnDelete = findViewById(R.id.btnDelete);

        expense = (Expense) getIntent().getSerializableExtra("expense");

        if (expense != null) {
            tvName.setText(expense.getName());
            
            DecimalFormat df = new DecimalFormat("#,###");
            tvAmount.setText(df.format(expense.getAmount()) + " đ");
            
            tvCategory.setText(expense.getCategory());
            tvDate.setText(expense.getDate());
            tvNote.setText(expense.getNote());
        }

        View.OnClickListener goBackListener = v -> finish();
        
        btnBack.setOnClickListener(goBackListener);
        btnGoBack.setOnClickListener(goBackListener);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expense != null) {
                    ExpenseManager.getInstance().removeExpense(expense);
                    Toast.makeText(ExpenseDetailActivity.this, "Đã xóa khoản chi", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
