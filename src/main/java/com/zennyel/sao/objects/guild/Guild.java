package com.zennyel.sao.objects.guild;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Guild implements Role{

    private String name;
    private Player owner;
    private UUID guildId;
    private Location home;
    private List<Guild> enemies;
    private List<Guild> allies;
    private List<Player> members;
    private RoleType roleType;


    public Guild(String name, Player owner) {
        this.name = name;
        this.owner = owner;
        generateGuildId();
    }

    public Guild(String name, Player owner, Location home, List<Guild> enemies, List<Guild> allies, List<Player> members) {
        this.name = name;
        this.owner = owner;
        this.home = home;
        this.enemies = enemies;
        this.allies = allies;
        this.members = members;
        generateGuildId();
    }

    public void generateGuildId(){
        this.guildId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public UUID getGuildId() {
        return guildId;
    }

    public void setGuildId(UUID guildId) {
        this.guildId = guildId;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public List<Guild> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Guild> enemies) {
        this.enemies = enemies;
    }

    public List<Guild> getAllies() {
        return allies;
    }

    public void setAllies(List<Guild> allies) {
        this.allies = allies;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    @Override
    public void promote() {
        switch (this.roleType) {
            case OWNER:
                break;
            case CAPTAIN:
                this.roleType = RoleType.OWNER;
                break;
            case MEMBER:
                this.roleType = RoleType.CAPTAIN;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.roleType);
        }
    }

        @Override
        public void demote () {
            switch (this.roleType) {
                case OWNER:
                    this.roleType = RoleType.CAPTAIN;
                    break;
                case CAPTAIN:
                    this.roleType = RoleType.MEMBER;
                    break;
                case MEMBER:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.roleType);
            }
        }
}
