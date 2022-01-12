public class Agent {
    private int consumption;
    private int production;
    private EnergyType energyType;
    public Agent(int consumption, int production, EnergyType energyType) {
        this.consumption = consumption;
        this.production = production;
        this.energyType = energyType;
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
