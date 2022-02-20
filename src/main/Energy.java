package main;
import java.io.Serializable;

public class Energy implements Serializable{
    public int amount;
    public int price;
    public int duration;
    public EnergyType type;
    public Energy(int amount, EnergyType type, int price, int duration) {
        this.setAmount(amount);
        this.setPrice(price);
        this.setType(type);
        this.duration = duration;
    }
    public Energy(EnergyType type, int price) {
        this.setAmount(-1);
        this.setPrice(price);
        this.setType(type);
        duration = -1;
    }
    public EnergyType getType() {
        return type;
    }
    public void setType(EnergyType type) {
        this.type = type;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Energy(int amount, EnergyType type, int duration) {
        this.setAmount(amount);
        this.setPrice(-1);
        this.setType(type);
        this.duration = duration;
    }
    @Override
    public String toString() {
        return "Energy [amount=" + amount + ", duration=" + duration + ", price=" + price + ", type=" + type + "]";
    }
}
