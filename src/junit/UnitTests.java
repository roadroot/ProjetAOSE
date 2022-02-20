package junit;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.Map;
import main.* ;

public class UnitTests {

  private static String FILE_CONF = "conf.json" ;
  private static int EXPECTED_NB_BROKER = 1 ;
  private static int EXPECTED_NB_CONSUMERS = 1 ;
  private static int EXPECTED_NB_PRODUCERS = 2 ;
  private static int EXPECTED_NB_PROSUMERS = 1 ;
  
  private Configuration conf ;
  
  @Before
  public void initialize() {
    // TODO add agent container
      // conf = new Configuration(FILE_CONF) ; 
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
