package io.pucman.server.conversation.action.chat;

import io.pucman.server.conversation.ConversationContext;
import io.pucman.server.conversation.action.Action;
import io.pucman.server.conversation.conversable.ConversablePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Base class for actions that work in chat only.
 * @param <P> - Plugin type this action belongs to.
 * @param <V> - Input type of this action.
 *
 * @see Action
 */
public abstract class ChatAction<P extends JavaPlugin, V> extends Action<V> implements Listener
{
    private ConversationContext<P, ConversablePlayer> conversationContext = this.getContext();
    private ConversablePlayer player = conversationContext.getForWhom();

    @EventHandler
    public void on(AsyncPlayerChatEvent e)
    {
        if (!player.get().getUniqueId().equals(e.getPlayer().getUniqueId())) {
            return;
        }

        if (!this.hasStarted() || !this.isAwaitingInput()) {
            return;
        }


        if (!e.getMessage().equals("")) {
            player.getActiveConversation().validateInput(e.getMessage());
        }

        HandlerList.unregisterAll(this);
    }

}
