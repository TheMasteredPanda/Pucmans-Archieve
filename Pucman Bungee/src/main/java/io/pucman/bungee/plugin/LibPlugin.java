package io.pucman.bungee.plugin;

import io.pucman.bungee.manager.ManagingPlugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;

/**
 * Inherited by main classes of plugins.
 *
 * @see ManagingPlugin
 */
public class LibPlugin extends ManagingPlugin
{

    /**
     * Reference to the proxy plugin manager..
     * @return
     */
    public PluginManager getPluginManager()
    {
        return this.getProxy().getPluginManager();
    }

    /**
     * Reference to the proxy scheduler.
     * @return
     */
    public TaskScheduler getScheduler()
    {
        return this.getProxy().getScheduler();
    }
}
