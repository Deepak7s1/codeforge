package org.adinfinitum.osn.seed.config;

/**
 * A POJO used by Commons-Configuration to store settings
 * about how to seed OSN users.
 */
@ConfigIdentifier(name="seed.user")
public class User {
    private int numberOfUsers;

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
}
