package com.futtosarisu.deustock.gateway;

import com.futtosarisu.deustock.data.SocialNetworkMessage;

import java.util.Date;
import java.util.List;

public interface SocialNetworkAPIGateway {

	public List<SocialNetworkMessage> getMessageList(String txt);
	public List<SocialNetworkMessage> getMessageList(String txt, int nMessage);
	public List<SocialNetworkMessage> getMessageList(String txt, int nMessage, Date from);


}
