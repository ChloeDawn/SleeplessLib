package net.sleeplessdev.lib.base;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.Locale;

public enum OrdinalFacing implements IStringSerializable {

    SOUTH(EnumFacing.SOUTH, null),
    SOUTH_WEST(EnumFacing.WEST, EnumFacing.SOUTH),
    WEST(EnumFacing.WEST, null),
    NORTH_WEST(EnumFacing.NORTH, EnumFacing.WEST),
    NORTH(EnumFacing.NORTH, null),
    NORTH_EAST(EnumFacing.EAST, EnumFacing.NORTH),
    EAST(EnumFacing.EAST, null),
    SOUTH_EAST(EnumFacing.SOUTH, EnumFacing.EAST);

    public static final OrdinalFacing[] VALUES = values();

    public static final OrdinalFacing[] CARDINALS = new OrdinalFacing[] {
            SOUTH, WEST, NORTH, EAST
    };

    public static final OrdinalFacing[] ORDINALS = new OrdinalFacing[] {
            SOUTH_WEST, NORTH_WEST, NORTH_EAST, SOUTH_EAST
    };

    private final EnumFacing primary;
    @Nullable
    private final EnumFacing secondary;

    OrdinalFacing(EnumFacing primary, @Nullable EnumFacing secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public static OrdinalFacing getFacingFromEntity(Entity entity) {
        float rot = entity.getRotationYawHead();
        float angle = rot < 0 ? rot + 360 : rot;
        return getFacingFromAngle(angle);
    }

    public static OrdinalFacing getFacingFromAngle(float angle) {
        int c = Math.floorMod(Math.round(angle / 45), 8);
        return OrdinalFacing.VALUES[c >= 0 && c < 8 ? c : 0];
    }

    public int getAngle() {
        return ordinal() * 45;
    }

    public EnumFacing getCardinal() {
        return getPrimary();
    }

    public EnumFacing getPrimary() {
        return primary;
    }

    @Nullable
    public EnumFacing getSecondary() {
        return secondary;
    }

    public boolean isCardinal() {
        return this == SOUTH || this == WEST || this == NORTH || this == EAST;
    }

    public OrdinalFacing rotate90() {
        switch (this) {
            case NORTH:      return EAST;
            case SOUTH:      return WEST;
            case WEST:       return NORTH;
            case EAST:       return SOUTH;
            case NORTH_WEST: return NORTH_EAST;
            case NORTH_EAST: return SOUTH_EAST;
            case SOUTH_WEST: return NORTH_WEST;
            case SOUTH_EAST: return SOUTH_WEST;
        }
        return this;
    }

    public OrdinalFacing rotate180() {
        switch (this) {
            case NORTH:      return SOUTH;
            case SOUTH:      return NORTH;
            case WEST:       return EAST;
            case EAST:       return WEST;
            case NORTH_WEST: return SOUTH_EAST;
            case NORTH_EAST: return SOUTH_WEST;
            case SOUTH_WEST: return NORTH_EAST;
            case SOUTH_EAST: return NORTH_WEST;
        }
        return this;
    }

    public OrdinalFacing rotate270() {
        switch (this) {
            case NORTH:      return WEST;
            case SOUTH:      return EAST;
            case WEST:       return SOUTH;
            case EAST:       return NORTH;
            case NORTH_WEST: return SOUTH_WEST;
            case NORTH_EAST: return NORTH_WEST;
            case SOUTH_WEST: return SOUTH_EAST;
            case SOUTH_EAST: return NORTH_EAST;
        }
        return this;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
