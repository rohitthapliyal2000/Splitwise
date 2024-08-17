package app.splitwise.models;

import java.util.*;

public class User {
    private final String name;

    public final String id;

    private final String email;

    public List<User> getFriends() {
        return friends;
    }

    public void setFriend(User friend) {
        for(User previousFriend: this.friends) {
            if(previousFriend == friend) {
                return;
            }
        }
        this.friends.add(friend);
        this.balancePerUser.putIfAbsent(friend, 0.0);
        friend.setFriend(this);
    }

    private List<User> friends;

    private Map<User, Double> balancePerUser;

    public Map<User, Double> getBalancePerUser() {
        return balancePerUser;
    }

    public User(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.friends = new ArrayList<>();
        this.balancePerUser = new HashMap<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                '}';
    }
}
