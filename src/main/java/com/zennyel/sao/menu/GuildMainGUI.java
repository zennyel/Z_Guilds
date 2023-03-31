package com.zennyel.sao.menu;

import com.zennyel.sao.utils.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuildMainGUI extends GUI{

    public GuildMainGUI(Inventory inventory, String name, Player player) {
        super(inventory, player);
        getPlayer().openInventory(inventory);
    }

    @Override
    public void addItems() {
        super.addItems();
        for(int i = 0; i < getInventory().getSize(); i++){
            setFirstLineItem(new Item(Material.STAINED_GLASS_PANE, " ", ""), i);
        }
        setSecondLineItem(new Item(Material.SILVER_SHULKER_BOX, "§4Configurações", ""), 3);
        setSecondLineItem(new Item(Material.SKULL, "§6Você", ""), 5);
        setSecondLineItem(new Item(Material.GRAY_SHULKER_BOX, "§4Membros", "§7Clique para ver os membros"), 7);
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
