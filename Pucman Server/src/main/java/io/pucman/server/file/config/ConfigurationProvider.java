package io.pucman.server.file.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Also copied from BungeeCord, with minor edits made.
 */
public abstract class ConfigurationProvider
{
    /**
     * List of configuration providers.
     */
    private static final Map<Class<? extends ConfigurationProvider>, ConfigurationProvider> providers = new HashMap<>();

    static
    {
        providers.put(YamlProvider.class, new YamlProvider());
    }

    /**
     * Gets a provider instance.
     * @param provider - provider class.
     * @return provider instance.
     */
    public static ConfigurationProvider getProvider(Class<? extends ConfigurationProvider> provider)
    {
        return providers.get(provider);
    }

    /**
     * List of loading methods.
     */
    public abstract void save(Configuration config, File file) throws IOException;

    public abstract void save(Configuration config, Writer writer);

    public abstract Configuration load(File file) throws IOException;

    public abstract Configuration load(File file, Configuration defaults) throws IOException;

    public abstract Configuration load(Reader reader);

    public abstract Configuration load(Reader reader, Configuration defaults);

    public abstract Configuration load(InputStream is);

    public abstract Configuration load(InputStream is, Configuration defaults);

    public abstract Configuration load(String string);

    public abstract Configuration load(String string, Configuration defaults);
}