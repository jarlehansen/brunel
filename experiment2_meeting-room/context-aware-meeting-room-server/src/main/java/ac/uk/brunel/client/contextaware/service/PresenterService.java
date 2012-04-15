package ac.uk.brunel.client.contextaware.service;

import ac.uk.brunel.contextaware.network.generated.PresenterActionProtobuf;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 18, 2010
 * Time: 7:12:16 PM
 */
public interface PresenterService {
    public void performPresenterAction(final PresenterActionProtobuf.PresenterAction presenterAction);
}
