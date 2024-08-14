package com.github._1mag1n33dev.entityHaven.Utils;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class NBTUtils {


    public static net.minecraft.server.v1_8_R3.ItemStack getNMSItemStack(ItemStack item) {
        if (item == null) {
            System.out.println("ItemStack is null in getNMSItemStack");
            return null;
        }
        return CraftItemStack.asNMSCopy(item);
    }


    public static ItemStack setNBTTag(ItemStack item, String key, String value) {
        if (item == null) {
            System.out.println("ItemStack is null before NBT setting");
            return null;
        }
        if (item.getType() == Material.AIR) {
            System.out.println("ItemStack is AIR before NBT setting");
            return item;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem == null) {
            System.out.println("NMS ItemStack conversion failed");
            return item;
        }

        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        tag.setString(key, value);
        nmsItem.setTag(tag);

        ItemStack result = CraftItemStack.asBukkitCopy(nmsItem);
        // System.out.println("ItemStack after NBT: " + result.toString());
        return result;
    }

    public static boolean hasNBTTag(ItemStack item, String tag) {
        if (item == null) {
            System.out.println("ItemStack is null in hasNBTTag");
            return false;
        }
        if (item.getType() == Material.AIR) {
            System.out.println("ItemStack is AIR in hasNBTTag");
            return false;
        }
        if (tag == null) {
            System.out.println("Tag is null in hasNBTTag");
            return false;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = getNMSItemStack(item);
        if (nmsItem == null) {
            System.out.println("NMS ItemStack is null in hasNBTTag");
            return false;
        }

        NBTTagCompound tagCompound = nmsItem.getTag();
        if (tagCompound == null) {
            System.out.println("NBTTagCompound is null in hasNBTTag");
            return false;
        }

        return tagCompound.hasKey(tag);
    }

    public static String getNBTTag(ItemStack item, String key) {
        if (item == null) {
            System.out.println("ItemStack is null in getNBTTag");
            return null;
        }
        if (item.getType() == Material.AIR) {
            System.out.println("ItemStack is AIR in getNBTTag");
            return null;
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem == null) {
            System.out.println("NMS ItemStack conversion failed in getNBTTag");
            return null;
        }

        if (nmsItem.hasTag()) {
            NBTTagCompound tag = nmsItem.getTag();
            if (tag != null && tag.hasKey(key)) {
                return tag.getString(key);
            }
        }
        return null;
    }

    public static String getAllNBTTags(ItemStack item) {
        if (item == null) return "No item in hand.";
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.getTag();
        if (tag == null) {
            return "No NBT data found.";
        }
        return tag.toString();
    }

    public static String getAllNBTTags(Block block) {
        if (block == null) return "No block in sight.";
        CraftBlockState state = (CraftBlockState) block.getState();
        NBTTagCompound tag = new NBTTagCompound();
        state.getTileEntity().b(tag);
        if (tag.isEmpty()) {
            return "No NBT data found.";
        }
        return tag.toString();
    }

    public static String getAllNBTTags(Entity entity) {
        if (entity == null) return "No entity in sight.";
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.e(tag);
        if (tag.isEmpty()) {
            return "No NBT data found.";
        }
        return tag.toString();
    }

    public static void setNBTTag(Entity entity, String key, String value) {
        if (entity == null || key == null || value == null) {
            System.out.println("Entity or key or value is null in setNBTTag");
            return;
        }

        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);
        tag.set(key, new NBTTagString(value));
        nmsEntity.f(tag);
    }

    public static boolean hasNBTTag(Entity entity, String key) {
        if (entity == null || key == null) {
            System.out.println("Entity or key is null in hasNBTTag");
            return false;
        }

        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);

        return tag.hasKey(key);
    }

    public static String getNBTTag(Entity entity, String key) {
        if (entity == null || key == null) {
            System.out.println("Entity or key is null in getNBTTag");
            return null;
        }

        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);

        return tag.hasKey(key) ? tag.getString(key) : null;
    }
}
