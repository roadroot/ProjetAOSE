package main;
import java.io.Serializable;

public class Energy implements Serializable{
    public int amount;
    public int price;
    public EnergyType type;
    public Energy(int amount, EnergyType type, int price) {
        this.setAmount(amount);
        this.setPrice(price);
        this.setType(type);
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
    public Energy(int amount, EnergyType type) {
        this.setAmount(amount);
        this.setPrice(-1);
        this.setType(type);
    }
}
