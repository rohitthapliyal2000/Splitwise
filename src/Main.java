import app.splitwise.models.User;
import app.splitwise.services.Account;
import app.splitwise.services.Expense;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, User> allUsers = getUsers();

        while (true) {
            String command = scanner.nextLine();
            String[] commands = command.split(" ");
            String commandType = commands[0];

            switch (commandType) {
                case "SHOW":
                    List<User> userAccountsToRead = new ArrayList<>();
                    if(commands.length > 1) {
                        for(int i = 1; i < commands.length; i++) {
                            userAccountsToRead.add(allUsers.get(commands[i]));
                        }
                    }
                    else {
                        for(var entry : allUsers.entrySet()) {
                            userAccountsToRead.add(entry.getValue());
                        }
                    }

                    Account account = new Account(userAccountsToRead);
                    account.showBalanceForUsers();
                    break;

                case "EXPENSE":
                    String paidBy = commands[1];
                    double amount = Double.parseDouble(commands[2]);
                    int noOfUsers = Integer.parseInt(commands[3]);
                    List<User> borrowers = new ArrayList<>();
                    List<Double> amountPerBorrower = new ArrayList<>();

                    for(int i = 0; i < noOfUsers; i++) {
                        borrowers.add(allUsers.get(commands[i + 4]));
                    }

                    String expenseType = commands[noOfUsers + 4];
                    switch(expenseType) {
                        case "EQUAL":
                            for(int i = 0; i < noOfUsers; i++) {
                                amountPerBorrower.add(amount/noOfUsers);
                            }
                            break;
                        case "EXACT":
                            for(int i = 0; i < noOfUsers; i++) {
                                amountPerBorrower.add(Double.parseDouble(commands[noOfUsers + 5 + i]));
                            }
                            break;
                        case "PERCENT":
                            for(int i = 0; i < noOfUsers; i++) {
                                amountPerBorrower.add((Double.parseDouble(commands[noOfUsers + 5 + i]) / 100.0) * (amount));
                            }
                            break;
                    }

                    User payer = allUsers.get(paidBy);
                    Expense expense = new Expense(payer, borrowers, amountPerBorrower);

                    System.out.println("user is " + payer);
                    System.out.println("user balances " + payer.getBalancePerUser());

                    expense.computeExpense();

                    System.out.println("after adding the expense user balances " + payer.getBalancePerUser());
                    break;
            }
        }
    }

    private static Map<String, User> getUsers() {
        User user1 = new User("Rohit", "u1", "r@gmail.com");
        User user2 = new User("Sarthak", "u2", "s@gmail.com");
        User user3 = new User("Gautam", "u3", "g@gmail.com");
        User user4 = new User("Chiranjeev", "u4", "c@gmail.com");

        user1.setFriend(user2);
        user1.setFriend(user3);
        user1.setFriend(user4);

        user2.setFriend(user3);
        user2.setFriend(user4);

        user3.setFriend(user4);

        Map<String, User> allUsers = new HashMap<>();
        allUsers.put(user1.id, user1);
        allUsers.put(user2.id, user2);
        allUsers.put(user3.id, user3);
        allUsers.put(user4.id, user4);
        return allUsers;
    }

}