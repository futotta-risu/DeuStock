package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;

class TwitterGatewayPerformanceIT {

	@Rule
	public ContiPerfRule i = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testgetMessageListPerformance(){
        SocialNetworkQueryData queryData = new SocialNetworkQueryData("VIAC");

        TwitterGateway.getInstance().getMessageList(queryData);
    }
    
    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testgetMessageListPerformanceAlternative(){
        SocialNetworkQueryData queryData = new SocialNetworkQueryData("AMZN");

        TwitterGateway.getInstance().getMessageList(queryData);
    }
    
}
