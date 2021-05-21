package es.deusto.deustock.dataminer.gateway.socialnetworks;

import es.deusto.deustock.data.SocialNetworkMessage;

import java.util.List;

/**
 * Social Network gateway interface for basic functionality.
 *
 * @author Erik B. Terres
 */
public interface SocialNetworkAPIGateway {

	/**
	 * Retrieves a list of messages from a Social Network
	 * @param queryData  {@link SocialNetworkQueryData} for the message retrieval.
	 * @return  List of {@link SocialNetworkMessage}
	 */
	List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData);
}
