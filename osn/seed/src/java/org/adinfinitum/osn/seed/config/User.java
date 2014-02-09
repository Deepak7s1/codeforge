package org.adinfinitum.osn.seed.config;

/**
 * A POJO used by Commons-Configuration to store settings
 * about how to seed OSN users.
 */
@ConfigIdentifier(name="seed.user")
public class User {
    private int numberOfUsers;
    private int numberOfOutsiders;

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public int getNumberOfOutsiders() {
        return numberOfOutsiders;
    }

    public void setNumberOfOutsiders(int numberOfOutsideUsers) {
        this.numberOfOutsiders = numberOfOutsideUsers;
    }
}
