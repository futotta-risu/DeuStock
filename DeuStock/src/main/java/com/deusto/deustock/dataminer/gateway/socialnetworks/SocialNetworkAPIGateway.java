package com.deusto.deustock.dataminer.gateway.socialnetworks;

import com.deusto.deustock.data.SocialNetworkMessage;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;

import java.util.List;

/**
 * Social Network gateway interface for basic functionality.
 */
public interface SocialNetworkAPIGateway {
	public List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData);
}
