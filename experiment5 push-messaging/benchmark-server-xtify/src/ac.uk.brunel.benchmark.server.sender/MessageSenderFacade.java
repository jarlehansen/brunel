package ac.uk.brunel.benchmark.server.sender;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 5:37 PM - 11/22/11
 */
public interface MessageSenderFacade {
    public void sendMessage(PushMessage pushMessage);
}
