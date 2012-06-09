/* Copyright 2011, Oracle and/or its affiliates.  All rights reserved. */
package oracle.social.johtaja.share;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.javatools.resourcebundle.BundleFactory;


/**
 * Utility class containing NLS related convenience methods.
 */
public class NlsUtil {
    private static final Logger logger = Logger.getLogger(NlsUtil.class.getName());
    private static final Level DEBUG = Level.FINEST;
    

    /**
     * This is the recommended method for retrieving the current user's Locale.
     * Only supported Locales are returned.
     * @return the preferred Locale
     */
    public static Locale getPreferredLocale() {
        return Locale.ENGLISH;
    }
    

    /**
     * Lookup localized messages in resource bundles, providing optional arguments
     * for message patterns.
     * 
     * @param baseName to be used
     * @param key message key
     * @param args optional arguments for message patterns.
     * @return localized message
     */
     public static String getMessage(String baseName, String key, Object... args) {
        ResourceBundle rb = BundleFactory.getBundle(baseName, getPreferredLocale());
        String pattern = null;
        try {
            pattern = rb.getString(key);
        }
        catch (MissingResourceException e) {
            logger.warning("Resource for " + key + " was not found in " + baseName);
            return "NOT_FOUND_FOR_" + key;
        }

        String result = MessageFormat.format(pattern, args);
        return result;
    }
}
