package net.eduard.essentials.core;

import org.bukkit.entity.Player;

public class Fake {

    public Fake(Player player) {
        setPlayer(player);
        setOriginal(player.getName());
        setFake(player.getName());
    }


    private Player player;

    private String fake;

    private String original;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getFake() {
        return fake;
    }

    public void setFake(String fake) {
        this.fake = fake;
    }


}
