package com.googlecode.sc2dm.server.sender;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:09 PM - 9/28/11
 */
public interface MessageSender {
    public boolean sendMessage(MessageData... messages);
}
