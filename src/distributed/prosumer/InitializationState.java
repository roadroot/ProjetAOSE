package distributed.prosumer;

import jade.core.behaviours.OneShotBehaviour;

public class InitializationState extends OneShotBehaviour {
    public static final String NAME = "init";
    public static final int NONE = 0;
    public static final int GET_PRICE_TABLE = 1;
    private int decision = NONE;
    // private ConsumerAgent prosumer;
    @Override
    public void action() {
        // boolean satisfied = true;
        // for(String agent :prosumer.getProviders().values())
        //     if(agent == null) {
        //         satisfied = false;
        //         break;
        //     }
        // if(satisfied) {
        //     prosumer.doWait();
        //     System.out.println(prosumer.receive().getContent());
        // } else
        //     decision = GET_PRICE_TABLE;
    }

    public InitializationState(ProsumerAgent prosumer) {
        // this.prosumer = prosumer;
    }

    @Override
    public int onEnd() {
        return decision;
    }
}
