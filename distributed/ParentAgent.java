import jade.core.Agent;

public abstract class ParentAgent extends Agent {
    private int positionX;
    private int positionY;
    // public ParentAgent(int consumption, int production, EnergyType energyType, int x, int y) {
    //     this.positionX = x;
    //     this.positionY = y;
    // }
    // public int getConsumption() {
    //     return consumption;
    // }
    // public EnergyType getEnergyType() {
    //     return energyType;
    // }
    // public int getProduction() {
    //     return production;
    // }
    // public boolean isConsumer() {
    //     return consumption > 0 && production == 0;
    // }
    // public boolean isGreenConsumer() {
    //     return consumption > 0 && production == 0 && energyType == EnergyType.RENEWABLE;
    // }
    // public boolean isProducer() {
    //     return consumption == 0 && production > 0;
    // }
    // public boolean isGreenProducer() {
    //     return consumption == 0 && production > 0 && energyType == EnergyType.RENEWABLE;
    // }
    // public boolean isProsumer() {
    //     return consumption > 0 && production > 0;
    // }
    // public boolean isGreenProsumer() {
    //     return consumption > 0 && production > 0 && energyType == EnergyType.RENEWABLE;
    // }
    public int getPositionY() {
        return positionY;
    }
    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
