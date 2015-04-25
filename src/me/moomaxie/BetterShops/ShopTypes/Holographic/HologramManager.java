package me.moomaxie.BetterShops.ShopTypes.Holographic;

import me.moomaxie.BetterShops.Shops.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ***********************************************************************
 * Copyright Max Hubbard (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated documents with similar branding
 * are the sole property of Max. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 * ************************************************************************
 */
public class HologramManager {

    private static List<ShopHologram> holos = new ArrayList<>();

    private static HashMap<Shop,ShopHologram> shopHolos = new HashMap<>();

    public static List<ShopHologram> getHolographicShops(){
        return holos;
    }

    public static HashMap<Shop,ShopHologram> getShopHolograms(){
        return shopHolos;
    }

    public static void addHolographicShop(ShopHologram holo){
        holos.add(holo);
        shopHolos.put(holo.getShop(),holo);
    }

    public static void removeHolographicShop(ShopHologram holo){
        holos.remove(holo);
        shopHolos.remove(holo.getShop());
    }
}
