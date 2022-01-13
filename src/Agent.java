public class Agent {
    private int consumption;
    private int production;
    private int positionX;
    private int positionY;
    private EnergyType energyType;
    public Agent(int consumption, int production, EnergyType energyType, int x, int y) {
        this.consumption = consumption;
        this.production = production;
        this.energyType = energyType;
        this.positionX = x;
        this.positionY = y;
    }
    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public int getConsumption() {
        return consumption;
    }
    public EnergyType getEnergyType() {
        return energyType;
    }
    public int getProduction() {
        return production;
    }
    public boolean isConsumer() {
        return consumption > 0 && production == 0;
    }
    public boolean isGreenConsumer() {
        return consumption > 0 && production == 0 && energyType == EnergyType.RENEWABLE;
    }
    public boolean isProducer() {
        return consumption == 0 && production > 0;
    }
    public boolean isGreenProducer() {
        return consumption == 0 && production > 0 && energyType == EnergyType.RENEWABLE;
    }
    public boolean isProsumer() {
        return consumption > 0 && production > 0;
    }
    public boolean isGreenProsumer() {
        return consumption > 0 && production > 0 && energyType == EnergyType.RENEWABLE;
    }
}
