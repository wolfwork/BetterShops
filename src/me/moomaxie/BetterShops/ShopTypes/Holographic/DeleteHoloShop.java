package me.moomaxie.BetterShops.ShopTypes.Holographic;

import com.gmail.filoghost.holographicdisplays.disk.HologramDatabase;
import com.gmail.filoghost.holographicdisplays.object.NamedHologram;
import me.moomaxie.BetterShops.Configurations.GUIMessages.MainGUI;
import me.moomaxie.BetterShops.Shops.Shop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

/**
 * ***********************************************************************
 * Copyright Max Hubbard (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated documents with similar branding
 * are the sole property of Max. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 * ************************************************************************
 */
public class DeleteHoloShop {

    public static final BlockFace[] axis = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    public static void deleteHologramShop(ShopHologram holo){

        if (holo != null && holo.getHologram() != null) {
            holo.getHologram().delete();
            HologramDatabase.deleteHologram(((NamedHologram) holo.getHologram()).getName());
            HologramDatabase.trySaveToDisk();

            Shop shop = holo.getShop();

            shop.getLocation().getBlock().setType(Material.CHEST);

            Chest chest = (Chest) shop.getLocation().getBlock().getState();

            org.bukkit.material.Chest c = (org.bukkit.material.Chest) chest.getData();

            BlockFace face = c.getFacing();

            if (shop.getOwner().isOnline()) {
                face = yawToFace(shop.getOwner().getPlayer().getLocation().getYaw()).getOppositeFace();
            }

            c.setFacingDirection(face.getOppositeFace());

            chest.setData(c);

            chest.update();

            Block b = chest.getBlock().getRelative(face.getOppositeFace());

            if (b != null) {

                b.setType(Material.WALL_SIGN);


                if (b.getState() instanceof Sign) {
                    Sign s = (Sign) b.getState();

                    org.bukkit.material.Sign sign = (org.bukkit.material.Sign) s.getData();

                    sign.setFacingDirection(face.getOppositeFace());

                    s.setData(sign);

                    s.setLine(0, MainGUI.getString("SignLine1"));
                    s.setLine(1, MainGUI.getString("SignLine2"));
                    if (shop.isOpen()) {
                        s.setLine(2, MainGUI.getString("SignLine3Open"));
                    } else {
                        s.setLine(2, MainGUI.getString("SignLine3Closed"));
                    }
                    s.setLine(3, MainGUI.getString("SignLine4"));

                    s.update();
                }
            }

            HologramManager.removeHolographicShop(holo);
        }
    }

    public static BlockFace yawToFace(float yaw) {

        return axis[Math.round(yaw / 90f) & 0x3];

    }
}
