package com.zennyel.sao.menu;

import com.zennyel.sao.Z_Guilds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class GUI implements InterfaceGUI{

    private Inventory inventory;
    private Player player;

    public GUI(Inventory inventory, Player player) {
        this.inventory = inventory;
        this.player = player;
        addItems();
    }

    @Override
    public void addItems() {
    }

    @Override
    public void setFirstLineItem(ItemStack itemStack, int position) {
        Bukkit.getScheduler().runTaskAsynchronously(Z_Guilds.getPlugin(Z_Guilds.class), ()->{
            inventory.setItem(position, itemStack);
        });
    }

    @Override
    public void setSecondLineItem(ItemStack itemStack, int position) {
        Bukkit.getScheduler().runTaskAsynchronously(Z_Guilds.getPlugin(Z_Guilds.class), ()->{
            inventory.setItem(position + 9, itemStack);
        });
    }


    @Override
    public void setThirdLineItem(ItemStack itemStack, int position) {
        Bukkit.getScheduler().runTaskAsynchronously(Z_Guilds.getPlugin(Z_Guilds.class), ()->{
            inventory.setItem(position + 18, itemStack);
        });
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Player getPlayer() {
        return null;
    }
}
