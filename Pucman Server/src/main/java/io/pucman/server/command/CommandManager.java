package io.pucman.server.command;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.pucman.common.reflect.ReflectUtil;
import io.pucman.common.reflect.accessors.FieldAccessor;
import io.pucman.server.manager.Manager;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * Manager for PumanCommands.
 * @param <P>
 */
public class CommandManager<P extends JavaPlugin> extends Manager<P>
{
    /**
     * Executor service used for execution various bodies of the PucmanCommand wrapper.
     */
    protected ListeningExecutorService service;

    /**
     * Command map used to register all commands.
     */
    private SimpleCommandMap commandMap;

    public CommandManager(P instance)
    {
        super(instance, Priority.HIGH);
        this.service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        if (this.commandMap == null) {
            SimplePluginManager simplePluginManager = (SimplePluginManager) this.instance.getServer().getPluginManager();
            FieldAccessor<SimpleCommandMap> accessor = ReflectUtil.get(simplePluginManager.getClass(), "commandMap", ReflectUtil.Type.DECLARED);
            accessor.get().setAccessible(true);
            this.commandMap = accessor.get(simplePluginManager);
        }
    }

    /**
     * Registers an array of commands.
     * @param commands - the PucmanCommands.
     */
    public void register(PucmanCommand... commands)
    {
        Arrays.stream(commands).forEachOrdered(cmd -> this.commandMap.register(instance.getName(), cmd.command));
    }
}