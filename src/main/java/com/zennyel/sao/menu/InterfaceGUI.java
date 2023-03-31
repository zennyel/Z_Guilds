package com.zennyel.sao.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InterfaceGUI {

    Inventory inventory = null;
    Player player = null;

    void addItems();


    void setFirstLineItem(ItemStack itemStack, int position);
    void setSecondLineItem(ItemStack itemStack, int position);
    void setThirdLineItem(ItemStack itemStack, int position);

    Inventory getInventory();

    Player getPlayer();




}
