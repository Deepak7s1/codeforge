package org.adinfinitum.osn.seed.config;


/**
 * A POJO used by Commons-Configuration to store settings
 * about how to seed OSN collections.
 */
@ConfigIdentifier(name="seed.collection")
public class Collection {
    private int numberOfCollections;
    private int numberOfConversationsPerCollection;

    public int getNumberOfCollections() {
        return numberOfCollections;
    }

    public void setNumberOfCollections(int numberOfCollections) {
        this.numberOfCollections = numberOfCollections;
    }

    public int getNumberOfConversationsPerCollection() {
        return numberOfConversationsPerCollection;
    }

    public void setNumberOfConversationsPerCollection(int numberOfConversationsPerCollection) {
        this.numberOfConversationsPerCollection = numberOfConversationsPerCollection;
    }

}
