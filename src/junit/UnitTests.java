package junit;

import static org.junit.Assert.*;
import org.junit.*;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

import java.util.Map;
import main.* ;

public class UnitTests {

  private static String FILE_CONF = "confTest.json" ;
  private static int EXPECTED_NB_BROKER = 1 ;
  private static int EXPECTED_NB_CONSUMERS = 1 ;
  private static int EXPECTED_NB_PRODUCERS = 2 ;
  private static int EXPECTED_NB_PROSUMERS = 1 ;
  
  private Configuration conf ;
  
  @Before
  public void initialize() throws Exception {
    jade.core.Runtime runtime = jade.core.Runtime.instance();
    Profile config = new ProfileImpl("localhost", 8755, null);
    config.setParameter("gui", "false");
    AgentContainer mc = runtime.createMainContainer(config);
    conf = new Configuration(FILE_CONF, mc) ;
  }
  
  
  @Test
  public void checkNbAgents() {
      int expectedNbAgents = EXPECTED_NB_BROKER + EXPECTED_NB_CONSUMERS + EXPECTED_NB_PRODUCERS + EXPECTED_NB_PROSUMERS ;
      assertEquals(conf.getAgents().size() , expectedNbAgents) ;
    
      if(EXPECTED_NB_BROKER==0)
        assertNull(GraphicHelper.getBroker()) ;
      else
        assertNotNull(GraphicHelper.getBroker()) ;
    
      assertEquals(GraphicHelper.getConsumers().size() , EXPECTED_NB_CONSUMERS) ;
      assertEquals(GraphicHelper.getProducers().size() , EXPECTED_NB_PRODUCERS) ;
      assertEquals(GraphicHelper.getProsumers().size() , EXPECTED_NB_PROSUMERS) ;
  }
  
}
