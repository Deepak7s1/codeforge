package org.adinfinitum.osn.seed.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Reads and parses the seed configuration XML file.
 * Provides getters to the configuration information for use
 * by the {@link org.adinfinitum.osn.seed.SeedEngine}.
 */
public class SeedConfiguration {
    private static final Logger logger = Logger.getLogger(SeedConfiguration.class.getName());

    XMLConfiguration config = new XMLConfiguration();
    Connector connectorConfig;
    User userConfig;
    Collection collectionConfig;

    private static final SeedConfiguration _instance = new SeedConfiguration();

    /**
     * Get the singleton instance.
     * @return singleton reference
     */
    public static SeedConfiguration getInstance() {
        return _instance;
    }

    /**
     * Constructor.
     */
    private SeedConfiguration() {
        BufferedReader reader = null;
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("seed_properties.xml");
            reader = new BufferedReader(new InputStreamReader(is));
            config.load(reader);
            parseConfiguration();

        }
        catch (ConfigurationException ce) {
            logger.log(Level.SEVERE, "Configuration exception in seed_properties.xml", ce);
            System.exit(1);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to read seed_properties.xml", e);
            System.exit(1);
        }
        finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e) {
                // do nothing.
            }
        }
    }

    /**
     * Parse the XML configuration and generate configuration objects.
     */
    private void parseConfiguration() {
        BeanDeclaration decl = new XMLBeanDeclaration(config, getConfigIdentifier(Connector.class));
        connectorConfig = (Connector) BeanHelper.createBean(decl);

        decl = new XMLBeanDeclaration(config, getConfigIdentifier(User.class));
        userConfig = (User) BeanHelper.createBean(decl);

        decl = new XMLBeanDeclaration(config, getConfigIdentifier(Collection.class));
        collectionConfig = (Collection) BeanHelper.createBean(decl);
    }


    @SuppressWarnings("unchecked")
    private String getConfigIdentifier(Class c) {
        Annotation anno = c.getAnnotation(ConfigIdentifier.class);
        if (anno instanceof ConfigIdentifier) {
            return ((ConfigIdentifier)anno).name();
        }
        return null;
    }


    public Connector getConnectorConfig() {
        return connectorConfig;
    }

    public User getUserConfig() {
        return userConfig;
    }

    public Collection getCollectionConfig() {
        return collectionConfig;
    }

}
