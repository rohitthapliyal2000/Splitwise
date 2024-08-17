package app.splitwise.services;

import app.splitwise.models.User;
import java.util.*;

public class Account {
    private List<User> users;

    public Account(List<User> users) {
        this.users = users;
    }

    public void showBalanceForUsers() {
        Set<String> processedUserIds = new HashSet<>();

        for(User user : users) {
            Map<User, Double> balancePerUser = user.getBalancePerUser();

            for(var entry : balancePerUser.entrySet()) {
                // Skipping already processed user due to 2-way relationship
                if(processedUserIds.contains(entry.getKey().id)) {
                    continue;
                }

                if(entry.getValue() < 0.0) {
                    System.out.println(user.id + " owes " + entry.getKey().id + " : " + entry.getValue() * -1.0);
                }
                else if(entry.getValue() > 0.0) {
                    System.out.println(entry.getKey().id + " owes " + user.id + " : " + entry.getValue());
                }
            }

            processedUserIds.add(user.id);
        }
    }
}
