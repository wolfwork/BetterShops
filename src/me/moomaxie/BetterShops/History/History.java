package me.moomaxie.BetterShops.History;

import me.moomaxie.BetterShops.Core;
import me.moomaxie.BetterShops.Shops.Shop;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

/**
 * ***********************************************************************
 * Copyright Max Hubbard (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated documents with similar branding
 * are the sole property of Max. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 * ************************************************************************
 */
public class History {

    private LinkedList<Transaction> Buytransactions = new LinkedList<>();
    private LinkedList<Transaction> transactions = new LinkedList<>();
    private LinkedList<Transaction> Selltransactions = new LinkedList<>();

    private Shop shop;

    public History(Shop shop){
        this.shop = shop;
    }

    public void addTransaction(OfflinePlayer p, Date date, ItemStack item, double price, int amount, boolean sell, boolean save){

        Transaction trans = new Transaction(p, date, item,price, amount,sell);

        if (sell) {
            Selltransactions.add(trans);
        } else {
            Buytransactions.add(trans);
        }

        transactions.add(trans);

        if (save) {
            shop.saveTransaction(trans, true);
        }
        saveTransactionToFile(trans);
    }

    public LinkedList<Transaction> getBuyingTransactions(){
        return Buytransactions;
    }
    public LinkedList<Transaction> getSellingTransactions(){
        return Selltransactions;
    }
    public LinkedList<Transaction> getAllTransactions(){
        return transactions;
    }

    public Shop getShop(){
        return shop;
    }

    public void clearAllTransactions(){
        transactions.clear();
        Buytransactions.clear();
        Selltransactions.clear();
    }

    public void clearHistory(){ //Everyday
        transactions.clear();
        Buytransactions.clear();
        Selltransactions.clear();

        shop.clearTransactions();
    }

    public void saveTransactionToFile(Transaction t){
        File file = new File(Core.getCore().getDataFolder(),"Transactions/" + shop.getName() + ".yml");

        if (!file.exists()){
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        int next = config.getKeys(false).size() + 1;

        config.createSection("" + next);

        config.getConfigurationSection("" + next).set("Shop Owner UUID", shop.getOwner().getUniqueId().toString());
        config.getConfigurationSection("" + next).set("Shop Owner Name", shop.getOwner().getName());
        config.getConfigurationSection("" + next).set("Date", t.getDate().toLocaleString());
        config.getConfigurationSection("" + next).set("Buyer UUID", t.getPlayer().getUniqueId().toString());
        config.getConfigurationSection("" + next).set("Buyer Name", t.getPlayer().getName());
        config.getConfigurationSection("" + next).set("Item", t.getItem());
        config.getConfigurationSection("" + next).set("Price", t.getPrice());
        config.getConfigurationSection("" + next).set("Amount", t.getAmount());
        config.getConfigurationSection("" + next).set("Selling Shop", t.isSell());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
