package com.example.bth2;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private static ExpenseManager instance;
    private List<Expense> expenses;

    private ExpenseManager() {
        expenses = new ArrayList<>();
        // Add sample data
        expenses.add(new Expense("Ăn sáng", 30000, "Ăn uống", "12/10/2026", ""));
        expenses.add(new Expense("Đổ xăng", 50000, "Đi lại", "12/10/2026", ""));
        expenses.add(new Expense("Mua sách", 120000, "Học tập", "13/10/2026", "Sách Lập trình Android"));
    }

    public static ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(expense.getId())) {
                expenses.remove(i);
                break;
            }
        }
    }

    public long getTotalAmount() {
        long total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
}
