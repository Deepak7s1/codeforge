package org.adinfinitum.osn.seed.util;


import java.util.Random;
import java.util.UUID;

/**
 * A class that generates random words and texts.
 */
public class RandomTextGenerator {

    /**
     * Generate a Conversation title.
     * @param prepend optional words that can be prepended to the title
     * @return the Conversation title
     */
    public static String genConversationTitle(String... prepend) {
        StringBuilder sb = new StringBuilder();
        if (prepend != null && prepend.length > 0) {
            for (String pp : prepend) {
                sb.append(pp).append(" ");
            }
        }
        sb.append(genRandomWords(4)).append(UUID.randomUUID().toString());
        return sb.toString();
    }


    /**
     * Generate a set of space separated random words.
     * @param numberOfWords the number of words to generate
     * @return the String of words
     */
    public static String genRandomWords(int numberOfWords) {
        Random rdm = new Random(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < numberOfWords; i++) {
            sb.append(wordDictionary[rdm.nextInt(wordDictionary.length)]).append(" ");
        }
        return sb.toString();
    }


    private static final String[] wordDictionary = {
            "Hello", "World", "Standard", "Respect", "Rendition", "Miscellaneous",
            "Restaurant", "BayArea", "Broomfield", "Singapore", "Montreal", "Seattle",
            "Poetic", "Perhaps", "Dictionary", "Rights", "Special", "Alpha", "Bravo",
            "Charlie", "Delta", "Echo", "Foxtrot", "Gamma", "Hotel", "Indigo", "Jaguar",
            "Kilo", "Lima", "Mamba", "Nitro", "Orange", "Pegasus", "Quintet", "Reindeer",
            "Super", "Trinidad", "Ubiquitous", "Velocity", "Xanadu", "Yellow", "Zebra",
            "Physical", "Hero", "Standpoint", "Browsing", "Employer", "Employee", "Business",
            "Bonus", "Mainstream", "Documentary", "Determine", "Equation", "Future", "University",
            "Abstract", "Grid", "Mental", "Mensa", "Cloud", "Redcloud", "Sunshine", "General",
            "Computing", "Science", "Math", "Literature", "History", "Geography", "Algebra",
            "Calculus", "Theory", "BigBang", "String", "Astronomy", "Engineering", "Professor"
    };
}
