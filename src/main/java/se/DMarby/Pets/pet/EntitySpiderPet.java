package se.DMarby.Pets.pet;

import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntitySpider;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSpider;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import se.DMarby.Pets.PetEntity;
import se.DMarby.Pets.Util;

public class EntitySpiderPet extends EntitySpider { // old AI
    private final Player owner;

    public EntitySpiderPet(World world, Player owner) {
        super(world);
        this.owner = owner;
    }

    public EntitySpiderPet(World world) {
        this(world, null);
    }

    private int distToOwner() {
        EntityHuman handle = ((CraftPlayer) owner).getHandle();
        return (int) (Math.pow(locX - handle.locX, 2) + Math.pow(locY - handle.locY, 2) + Math.pow(locZ
                - handle.locZ, 2));
    }

    @Override
    protected void bq() {
        if (owner == null) {
            super.bq();
            return;
        }
        this.W = 10F;
        if (distToOwner() > 3) {
            this.getNavigation().a(owner.getLocation().getX(), owner.getLocation().getY(), owner.getLocation().getZ(), 1.2F);
            this.getNavigation().a(false);
            getEntitySenses().a();
            getNavigation().f();
            getControllerMove().c();
            getControllerLook().a();
            getControllerJump().b();
        }
        if (distToOwner() > Util.MAX_DISTANCE)
            this.getBukkitEntity().teleport(owner);
    }

    @Override
    public CraftEntity getBukkitEntity() {
        if (owner != null && bukkitEntity == null)
            bukkitEntity = new BukkitSpiderPet(this);
        return super.getBukkitEntity();
    }

    public static class BukkitSpiderPet extends CraftSpider implements PetEntity {
        private final Player owner;

        public BukkitSpiderPet(EntitySpiderPet entity) {
            super((CraftServer) Bukkit.getServer(), entity);
            this.owner = entity.owner;
        }

        @Override
        public Spider getBukkitEntity() {
            return this;
        }

        @Override
        public Player getOwner() {
            return owner;
        }

        @Override
        public void upgrade() {
        }

        @Override
        public void setLevel(int level) {
            // setSize(level);
        }
    }
}
