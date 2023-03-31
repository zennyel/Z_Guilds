package com.zennyel.sao.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuildEnemiesGUI extends GUI{

    public GuildEnemiesGUI(Inventory inventory, String name, Player player) {
        super(inventory, player);
        getPlayer().openInventory(inventory);
    }

    @Override
    public void addItems() {
        super.addItems();
    }

    @Override
    public void setFirstLineItem(ItemStack itemStack, int position) {
        super.setFirstLineItem(itemStack, position);
    }

    @Override
    public void setSecondLineItem(ItemStack itemStack, int position) {
        super.setSecondLineItem(itemStack, position);
    }

    @Override
    public void setThirdLineItem(ItemStack itemStack, int position) {
        super.setThirdLineItem(itemStack, position);
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }

    @Override
    public Player getPlayer() {
        return super.getPlayer();
    }
}
