package com.zennyel.sao.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Item extends ItemStack {

    public Item(Material material,String displayName, String lore){
        setType(material);
        setItemMeta(displayName, lore);
    }

    public void setItemMeta(String displayName, String lore){
        ItemMeta im = getItemMeta();
        im.setDisplayName(displayName);
        im.setLore(Arrays.asList(lore));
        setItemMeta(im);
    }

}
