package me.moomaxie.BetterShops.Listeners.ManagerOptions;

import BetterShops.Dev.API.Events.ServerShopCreateEvent;
import me.moomaxie.BetterShops.Configurations.AnvilGUI;
import me.moomaxie.BetterShops.Configurations.Config;
import me.moomaxie.BetterShops.Configurations.GUIMessages.MainGUI;
import me.moomaxie.BetterShops.Configurations.Messages;
import me.moomaxie.BetterShops.Configurations.Permissions.Permissions;
import me.moomaxie.BetterShops.Configurations.ShopLimits;
import me.moomaxie.BetterShops.Core;
import me.moomaxie.BetterShops.Listeners.BuyerOptions.OpenShop;
import me.moomaxie.BetterShops.Listeners.Misc.ChatMessages;
import me.moomaxie.BetterShops.Listeners.OpenShopOptions;
import me.moomaxie.BetterShops.NPC.DeleteNPC;
import me.moomaxie.BetterShops.NPC.NPCMenu;
import me.moomaxie.BetterShops.NPC.NPCs;
import me.moomaxie.BetterShops.NPC.ShopsNPC;
import me.moomaxie.BetterShops.Shops.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * ***********************************************************************
 * Copyright Max Hubbard (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated documents with similar branding
 * are the sole property of Max. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 * ************************************************************************
 */
public class ShopSettings implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onSettings(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName().contains("§7[Shop]")) {
            e.setCancelled(true);

            if (e.getInventory().getType() == InventoryType.CHEST) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {

                    String name = e.getInventory().getName();
                    name = name.substring(11);

                    Shop shop = ShopLimits.fromString(p, name);

                    if (shop.getOwner().getUniqueId().equals(p.getUniqueId())) {

                        if (e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(MainGUI.getString("ShopInfoDisplayNameOpen")) || e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(MainGUI.getString("ShopInfoDisplayNameClosed")) || e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§l" + shop.getName()) && e.getCurrentItem().getItemMeta().getLore() != null && e.getCurrentItem().getItemMeta().getLore().contains(MainGUI.getString("OpenShopSettings"))) {
                            if (e.getAction() == InventoryAction.PICKUP_ALL) {
                                if (shop.isOpen()) {
                                    shop.setOpen(false);
                                    ((Player) e.getWhoClicked()).sendMessage(Messages.getString("Prefix") + Messages.getString("ShopOption").replaceAll("<Value>",MainGUI.getString("ShopInfoDisplayNameClosed")));
                                    if (!shop.isNPCShop()) {


                                        Chest chest = (Chest) shop.getLocation().getWorld().getBlockAt(shop.getLocation()).getState();

                                        Block block = chest.getBlock();

                                        Sign sign = null;
                                        if (block.getRelative(1, 0, 0).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(1, 0, 0).getState();
                                        } else if (block.getRelative(-1, 0, 0).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(-1, 0, 0).getState();
                                        } else if (block.getRelative(0, 0, 1).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(0, 0, 1).getState();
                                        } else if (block.getRelative(0, 0, -1).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(0, 0, -1).getState();
                                        }

                                        if (sign != null) {
                                            if (sign.getLine(0).contains(MainGUI.getString("SignLine1"))) {
                                                if (sign.getLine(3).contains(MainGUI.getString("SignLine4"))) {
                                                    if (sign.getLine(1).contains(MainGUI.getString("SignLine2"))) {
                                                        sign.setLine(2, MainGUI.getString("SignLine3Closed"));
                                                        sign.update();
                                                    }
                                                }
                                            }
                                        }
                                    }


                                } else {
                                    shop.setOpen(true);
                                    ((Player) e.getWhoClicked()).sendMessage(Messages.getString("Prefix") + Messages.getString("ShopOption").replaceAll("<Value>",MainGUI.getString("ShopInfoDisplayNameOpen")));

                                    if (!shop.isNPCShop()) {

                                        Chest chest = (Chest) shop.getLocation().getWorld().getBlockAt(shop.getLocation()).getState();

                                        Block block = chest.getBlock();

                                        Sign sign = null;
                                        if (block.getRelative(1, 0, 0).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(1, 0, 0).getState();
                                        } else if (block.getRelative(-1, 0, 0).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(-1, 0, 0).getState();
                                        } else if (block.getRelative(0, 0, 1).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(0, 0, 1).getState();
                                        } else if (block.getRelative(0, 0, -1).getType() == Material.WALL_SIGN) {
                                            sign = (Sign) block.getRelative(0, 0, -1).getState();
                                        }

                                        if (sign != null) {
                                            if (sign.getLine(0).contains(MainGUI.getString("SignLine1"))) {
                                                if (sign.getLine(3).contains(MainGUI.getString("SignLine4"))) {
                                                    if (sign.getLine(1).contains(MainGUI.getString("SignLine2"))) {
                                                        sign.setLine(2, MainGUI.getString("SignLine3Open"));
                                                        sign.update();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                OpenShopOptions.openShopOwnerOptionsInventory(e.getInventory(), p, shop, 1);
                            } else if (e.getAction() == InventoryAction.PICKUP_HALF) {
                                openShopManager(e.getInventory(), p, shop);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void openShopManager(Inventory inv, Player p, Shop shop) {

        boolean same = true;

        if (inv == null) {
            same = false;
            inv = Bukkit.createInventory(p, 54, "§7[Shop] §a" + shop.getName());
        } else {
            inv.clear();
        }

        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(" ");
        item.setItemMeta(m);
        for (int i = 0; i < 18; i++) {
            inv.setItem(i, item);
        }


        ItemStack nam = new ItemStack(Material.CHEST);
        ItemMeta namMeta = nam.getItemMeta();
        if (shop.isNotify()) {
            namMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NotificationsOn"));
        } else {
            namMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NotificationsOff"));
        }
        namMeta.setLore(Arrays.asList(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NotificationsLore")));
        nam.setItemMeta(namMeta);

        ItemStack desc = new ItemStack(Material.CHEST);
        ItemMeta descMeta = desc.getItemMeta();
        descMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ChangeDescription"));
        descMeta.setLore(Arrays.asList(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ChangeDescriptionLore")));
        desc.setItemMeta(descMeta);

        ItemStack ownuse = new ItemStack(Material.CHEST);
        ItemMeta ownuseMeta = ownuse.getItemMeta();
        if (shop.isServerShop()) {
            ownuseMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ServerShopOn"));
        } else {
            ownuseMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ServerShopOff"));
        }
        ownuseMeta.setLore(Arrays.asList(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ServerShopLore")));
        ownuse.setItemMeta(ownuseMeta);

        ItemStack npc = new ItemStack(Material.CHEST);
        ItemMeta npcMeta = npc.getItemMeta();
        if (shop.isNPCShop()) {
            npcMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NPCShopOn"));
        } else {
            npcMeta.setDisplayName(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NPCShopOff"));
        }
        npcMeta.setLore(Arrays.asList(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("Warning"),
                me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("DeletedChest"),
                " ",
                me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NPCLore")));
        npc.setItemMeta(npcMeta);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(MainGUI.getString("BackArrow"));
        back.setItemMeta(backMeta);

        inv.setItem(0, back);

        inv.setItem(inv.firstEmpty(), nam);
        inv.setItem(inv.firstEmpty(), desc);

        if (Permissions.hasUsePerm(p)) {
            inv.setItem(inv.firstEmpty(), ownuse);
        }
        if (Config.useNPCs() && Permissions.hasNPCPerm(p)) {
            inv.setItem(inv.firstEmpty(), npc);
        }


        if (!same)
            p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onSettingsClick(final InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName().contains("§7[Shop]")) {
            e.setCancelled(true);


            if (e.getInventory().getType() == InventoryType.CHEST) {
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                    String name = e.getInventory().getName();
                    name = name.substring(11);

                    final Shop shop = ShopLimits.fromString(p, name);

                    if (e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(MainGUI.getString("BackArrow"))) {
                        if (shop.getOwner().getUniqueId().equals(p.getUniqueId())) {
                            if (!shop.isServerShop()) {
                                OpenShopOptions.openShopOwnerOptionsInventory(e.getInventory(), p, shop, 1);
                            } else {
                                OpenShop.openShopItems(e.getInventory(), p, shop, 1);
                            }
                        } else {
                            OpenShop.openShopItems(e.getInventory(), p, shop, 1);
                        }
                    }

                    if (e.getCurrentItem().getItemMeta().getLore() != null && e.getCurrentItem().getItemMeta().getLore().contains(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NotificationsLore"))) {
                        if (shop.isNotify()) {
                            shop.setNotification(false);
                            openShopManager(e.getInventory(), p, shop);
                        } else {
                            shop.setNotification(true);
                            openShopManager(e.getInventory(), p, shop);
                        }
                    } else if (e.getCurrentItem().getItemMeta().getDisplayName() != null && e.getCurrentItem().getItemMeta().getDisplayName().equals(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ChangeDescription"))) {

                        if (Config.useAnvil()) {
                            AnvilGUI gui = Core.getAnvilGUI();
                            gui.doGUIThing(p, new AnvilGUI.AnvilClickEventHandler() {
                                @Override
                                public void onAnvilClick(AnvilGUI.AnvilClickEvent ev) {
                                    if (ev.getSlot() == 2) {
                                        ev.setWillClose(true);
                                        ev.setWillDestroy(true);


                                        if (ev.getCurrentItem().getType() == Material.PAPER) {
                                            if (ev.getCurrentItem().hasItemMeta()) {
                                                if (ev.getCurrentItem().getItemMeta().getDisplayName() != null) {
                                                    String name = ev.getCurrentItem().getItemMeta().getDisplayName();

                                                    if (isAlphaNumeric(name)) {
                                                        if (name.length() <= 26) {

                                                            shop.setDescription(name);

                                                            ((Player) e.getWhoClicked()).sendMessage(Messages.getString("Prefix") + Messages.getString("ChangeDescription"));
                                                            e.getWhoClicked().closeInventory();
                                                            openShopManager(e.getInventory(), (Player) e.getWhoClicked(), shop);
                                                        } else {
                                                            ((Player) e.getWhoClicked()).sendMessage(Messages.getString("Prefix") + Messages.getString("LongDescription"));
                                                            e.getWhoClicked().closeInventory();
                                                            openShopManager(e.getInventory(), (Player) e.getWhoClicked(), shop);
                                                        }
                                                    } else {
                                                        ((Player) e.getWhoClicked()).sendMessage(Messages.getString("Prefix") + Messages.getString("ImproperDescription"));

                                                        if (Core.isAboveEight() && Config.useTitles()) {

                                                            e.getWhoClicked().closeInventory();
                                                            Core.getTitleManager().setTimes(((Player) e.getWhoClicked()), 20, 40, 20);
                                                            Core.getTitleManager().sendSubTitle(((Player) e.getWhoClicked()), Messages.getString("ImproperDescription"));


                                                        }
                                                        e.setCancelled(true);
                                                    }
                                                }
                                            }
                                        }

                                    } else {
                                        ev.setWillClose(false);
                                        ev.setWillDestroy(false);
                                    }
                                }
                            });

                            ItemStack it = new ItemStack(Material.PAPER);
                            ItemMeta meta = it.getItemMeta();
                            meta.setDisplayName("Type Description");
                            it.setItemMeta(meta);

                            gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, it);

                            gui.open();
                        } else {
                            p.closeInventory();
                            ChatMessages.description.put(p, shop);
                            p.sendMessage(Messages.getString("Prefix") + Messages.getString("ChatMessage"));
                        }
                    } else if (e.getCurrentItem().getItemMeta().getLore() != null && e.getCurrentItem().getItemMeta().getLore().contains(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("ServerShopLore"))) {
                        boolean can = true;
                        if (Config.usePerms()) {
                            if (!Permissions.hasUsePerm(p)) {
                                can = false;
                            }
                        }
                        if (can) {
                            if (shop.isServerShop()) {
                                shop.setServerShop(false);
                                p.sendMessage(Messages.getString("Prefix") + Messages.getString("ServerShop").replaceAll("<Value>", "§cOff"));
                                openShopManager(e.getInventory(), p, shop);
                            } else {
                                shop.setServerShop(true);
                                p.sendMessage(Messages.getString("Prefix") + Messages.getString("ServerShop").replaceAll("<Value>", "§aOn"));
                                openShopManager(e.getInventory(), p, shop);

                                ServerShopCreateEvent ev = new ServerShopCreateEvent(shop);

                                Bukkit.getPluginManager().callEvent(ev);
                            }
                        } else {
                            p.sendMessage(Messages.getString("Prefix") + Messages.getString("NoPermission"));
                        }
                    } else if (e.getCurrentItem().getItemMeta().getLore() != null && e.getCurrentItem().getItemMeta().getLore().contains(me.moomaxie.BetterShops.Configurations.GUIMessages.ShopSettings.getString("NPCLore"))) {
                        boolean can = true;
                        if (Config.usePerms()) {
                            if (!Permissions.hasNPCPerm(p)) {
                                can = false;
                            }
                        }
                        if (can) {
                            if (shop.isNPCShop()) {
                                shop.setNPCShop(false);
                                p.sendMessage(Messages.getString("Prefix") + Messages.getString("NPCShop").replaceAll("<Value>", "§cOff"));
                                try {
                                    for (ShopsNPC npc : NPCs.getNPCs()) {
                                        if (npc.getShop().getName().equals(shop.getName())) {
                                            DeleteNPC.deleteNPC(npc);
                                            npc.getEntity().remove();
                                        }
                                    }
                                } catch (Exception ignored) {

                                }
                                openShopManager(e.getInventory(), p, shop);
                            } else {
                                NPCMenu.openNPCMenu(shop, p);
                            }
                        } else {
                            p.sendMessage(Messages.getString("Prefix") + Messages.getString("NoPermission"));
                        }
                    }
                }
            }
        }
    }

    public boolean isAlphaNumeric(String str) {
        if (str.trim().length() < 1) {
            return false;
        }
        String acceptable = "abcdefghijklmnopqrstuvwxyz0123456789 ";
        for (int i = 0; i < str.length(); i++) {
            if (!acceptable.contains(str.substring(i, i + 1).toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
