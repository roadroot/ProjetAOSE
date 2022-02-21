package junit;

import static org.junit.Assert.*;
import org.junit.*;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

import main.* ;

public class UnitTests {

  private static String FILE_CONF = "confTest.json" ;
  private static int EXPECTED_NB_BROKER = 1 ;
  private static int EXPECTED_NB_CONSUMERS = 1 ;
  private static int EXPECTED_NB_PRODUCERS = 2 ;
  private static int EXPECTED_NB_PROSUMERS = 1 ;
  private static int EXPECTED_ENERGY_AMOUNT = 100 ;

  // EnergyType instead of int
  private static EnergyType EXPECTED_ENERGY_TYPE = EnergyType.RENEWABLE ;
  private static int EXPECTED_ENERGY_DURATION = 2 ;
  
  private Configuration conf ;

  //changed naming to energy to suppress warnings
  private Energy energy ;
  
  @Before
  public void initialize() throws Exception {
    jade.core.Runtime runtime = jade.core.Runtime.instance();
    Profile config = new ProfileImpl("localhost", 8755, null);
    config.setParameter("gui", "false");
    AgentContainer mc = runtime.createMainContainer(config);
    conf = new Configuration(FILE_CONF, mc) ;

    energy = new Energy(EXPECTED_ENERGY_AMOUNT, EXPECTED_ENERGY_TYPE , EXPECTED_ENERGY_DURATION);
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
  
  
  @Test
  public void checkEnergy() {
      Message m1 = GraphicHelper.messages.get(0);
      assertEquals(m1.getSender() , "producer") ;
      assertEquals(m1.getReceiver() , "Consumer") ;
      // not Energy by energy
      assertEquals(m1.getContent() , energy.toString()) ;
  }
  
}
