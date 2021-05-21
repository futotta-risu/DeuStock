package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;
import es.deusto.deustock.data.SocialNetworkMessage;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;

import java.util.List;

import static org.junit.Assert.assertNotNull;

class TwitterGatewayPerformanceIT {

	@Rule
	public ContiPerfRule i = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(average = 999, max = 999, percentile95=999)
    public void testgetMessageListPerformance(){
        SocialNetworkQueryData queryData = new SocialNetworkQueryData("VIAC");

        List<SocialNetworkMessage> msg = TwitterGateway.getInstance().getMessageList(queryData);

        assertNotNull(msg);
    }
    
    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(average = 999, max = 999, percentile95=999)
    public void testGetMessageListPerformanceAlternative(){
        SocialNetworkQueryData queryData = new SocialNetworkQueryData("AMZN");

        TwitterGateway.getInstance().getMessageList(queryData);
    }
    
}
