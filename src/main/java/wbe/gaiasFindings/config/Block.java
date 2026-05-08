package wbe.gaiasFindings.config;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Block {

    private Material id;

    private HashMap<Rune, Integer> rewards = new HashMap<>();

    private int totalWeight;

    public Block(Material id, HashMap<Rune, Integer> rewards, int totalWeight) {
        this.id = id;
        this.rewards = rewards;
        this.totalWeight = totalWeight;
    }

    public Material getId() {
        return id;
    }

    public void setId(Material id) {
        this.id = id;
    }

    public HashMap<Rune, Integer> getRewards() {
        return rewards;
    }

    public void setRewards(HashMap<Rune, Integer> rewards) {
        this.rewards = rewards;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Rune getRandomReward() {
        Random random = new Random();
        int randomNumber = random.nextInt(totalWeight);
        int weight = 0;
        Map.Entry<Rune, Integer> last = null;

        for(Map.Entry<Rune, Integer> reward : rewards.entrySet()) {
            weight += reward.getValue();
            if(randomNumber < weight) {
                return reward.getKey();
            }
            last = reward;
        }

        return last.getKey();
    }
}
