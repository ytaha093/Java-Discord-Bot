import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Gamer implements Serializable, Comparable<Gamer> {

    String id;
    int money;
    int level;
    long lastTime;
    int cooldown;
    int salary;
    int newYear;

    public Gamer(String id, int money, int level, int cooldown, int salary, int newYear) {
        this.id = id;
        this.money = money;
        this.lastTime = 0;
        this.level = level;
        this.cooldown = cooldown;
        this.salary = salary;
        this.newYear = newYear;
    }

    public void chatted() {
        if ((System.nanoTime() - lastTime) / 1000000000 > cooldown) {
            this.lastTime = System.nanoTime();
            money += salary;
        }
    }
    
    public void levelUp() {
    	money -= levelCost();
    	level++;	
    	cooldown--;
    	salary += 2;
    }
    
    public void freeLevelUp() {
    	level++;	
    	cooldown--;
    	salary += 2;
    }
    
    public boolean hasUsedNewYear() {
    	if (newYear == 0) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public void newYear() {
    	newYear = 1;
    }
    
    public int levelCost() {
    	return 300 + (level * 400);
    }
    
    public int getLevel() {
    	return level;
    }
    
    public int getsalary() {
    	return salary;
    }
    
    public double getCooldown() {
    	return cooldown;
    }

    public void add(int bet) {
        money += bet;
    }

    public void remove(int bet) {
        money -= bet;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setMoney(int money) {
        this.money = money;
    }


    public String getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public String toString() {
        return id + " " + money + " " + level + " " + cooldown + " " + salary + " " + newYear;
    }


    public int compareTo(Gamer g) {
        if (this.money < g.getMoney()) {
            return 1;
        } else if (this.money > g.getMoney()) {
            return -1;
        } else {
            return 0;
        }
    }

}
