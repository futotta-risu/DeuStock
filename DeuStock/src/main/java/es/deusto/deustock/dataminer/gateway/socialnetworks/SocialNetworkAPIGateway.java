package es.deusto.deustock.dataminer.gateway.socialnetworks;

import es.deusto.deustock.data.SocialNetworkMessage;

import java.util.List;

/**
 * Social Network gateway interface for basic functionality.
 */
public interface SocialNetworkAPIGateway {
	List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData);
}