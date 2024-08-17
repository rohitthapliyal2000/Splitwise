package app.splitwise.services;

import app.splitwise.models.User;

import java.util.*;

public class Expense {
    private User payer;

    private List<User> borrowers;

    private List<Double> amountPerBorrower;

    public Expense(User payer, List<User> borrowers, List<Double> amountPerBorrower) {
        this.payer = payer;
        this.borrowers = borrowers;
        this.amountPerBorrower = amountPerBorrower;
    }

    public void computeExpense() {
        Map<User, Double> payerBalances = payer.getBalancePerUser();

        for(int i = 0; i < borrowers.size(); i++) {
            User borrower = borrowers.get(i);

            // Updating balancePerUser map (Payer side)
            if(payerBalances.containsKey(borrower)) {
                Double currentBalance = payerBalances.get(borrower);
                Double newBalance = currentBalance + amountPerBorrower.get(i);
                payerBalances.put(borrower, newBalance);
            }

            // Updating balancePerUser map (Borrower side)
            Map<User, Double> borrowerBalances = borrower.getBalancePerUser();
            if(borrowerBalances.containsKey(payer)) {
                Double currentBalance = borrowerBalances.get(payer);
                Double newBalance = currentBalance - amountPerBorrower.get(i);
                borrowerBalances.put(payer, newBalance);
            }
        }
    }
}
