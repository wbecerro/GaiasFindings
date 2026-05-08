package wbe.gaiasFindings.config;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class Rune {

    private String id;

    private Material material;

    private String name;

    private Enchantment enchantment;

    public Rune(String id, Material material, String name, Enchantment enchantment) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.enchantment = enchantment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public void setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
    }
}
