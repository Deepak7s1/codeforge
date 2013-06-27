package org.adinfinitum.osn.seed.util;

import java.util.Random;
import java.util.UUID;


/**
 * A class that generates random words and texts.
 */
public class RandomTextGenerator {

    /**
     * Generate an 8-character unique identifier.
     * @return the unique identifier
     */
    public static String genUniqueId() {
        return UUID.randomUUID().toString().substring(0,8);
    }


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
        sb.append(genRandomWords(4));
        return sb.toString();
    }


    /**
     * Generate some message text
     * @param prepend optional words that can be prepended to the message
     * @return the message text
     */
    public static String genMessageText(String... prepend) {
        StringBuilder sb = new StringBuilder();
        if (prepend != null && prepend.length > 0) {
            for (String pp : prepend) {
                sb.append(pp).append(" ");
            }
        }
        sb.append(genRandomSentences(1));
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
            sb.append(WORD_DICTIONARY[rdm.nextInt(WORD_DICTIONARY.length)]).append(" ");
        }
        return sb.toString();
    }


    /**
     * Generate a set of space separated random words.
     * @param numSentences the number of words to generate
     * @return the String of words
     */
    public static String genRandomSentences(int numSentences) {
        Random rdm = new Random(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < numSentences; i++) {
            sb.append(SENTENCES[rdm.nextInt(SENTENCES.length)]).append(" ");
        }
        return sb.toString();
    }


    private static final String[] WORD_DICTIONARY = {
            "Hello", "World", "Standard", "Respect", "Rendition", "Miscellaneous",
            "Restaurant", "BayArea", "Broomfield", "Singapore", "Montreal", "Seattle",
            "Poetic", "Perhaps", "Dictionary", "Rights", "Special", "Alpha", "Bravo",
            "Charlie", "Delta", "Echo", "Foxtrot", "Gamma", "Justice", "Indigo", "Jaguar",
            "Kilo", "Lima", "Mamba", "Nitro", "Closure", "Pegasus", "Quintet", "Reindeer",
            "Super", "Trinidad", "Ubiquitous", "Velocity", "Xanadu", "Yellow", "Zebra",
            "Physical", "Hero", "Standpoint", "Browsing", "Employer", "Employee", "Business",
            "Bonus", "Mainstream", "Documentary", "Determine", "Equation", "Future", "University",
            "Abstract", "Grid", "Mental", "Mensa", "Cloud", "Redcloud", "Sunshine", "General",
            "Computing", "Science", "Math", "Literature", "History", "Geography", "Algebra",
            "Calculus", "Theory", "Levitate", "String", "Astronomy", "Engineering", "Professor",
            "Continent", "Multi-threaded", "Omega", "Daemon", "Framework", "Variance", "Routine",
            "Perfunctory", "Verisimilitude","Mordacious", "Cognizance", "Abstergent", "Mignon",
            "Rudimentary", "Music", "Cantabile", "Fortissimo", "Pianissimo", "Crescendo",
            "\u5317\u7F8E", "\u4E2D\u6587", "\u3059\u3066\u304D\u306A", "\u53EF\u611B\u3089\u3057\u3044"
    };


    private static final String[] SENTENCES = {
            "The quick, brown fox jumps over a lazy dog.",
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.",
            "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.",
            "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.",
            "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.",
            "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.",
            "Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.",
            "A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.",
            "I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine.",
            "abc def ghi jkl mno pqrs tuv wxyz ABC DEF GHI JKL MNO PQRS TUV WXYZ !§ $%& /() =?* '<> #|; ²³~ @`´ ©«» ¤¼× {} ",
            "Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus."
    };
}
