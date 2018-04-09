package net.sleeplessdev.lib.base;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;
import java.util.Optional;

public enum OrdinalFacing implements IStringSerializable {

    SOUTH(EnumFacing.SOUTH),
    SOUTH_WEST(EnumFacing.WEST),
    WEST(EnumFacing.WEST),
    NORTH_WEST(EnumFacing.NORTH),
    NORTH(EnumFacing.NORTH),
    NORTH_EAST(EnumFacing.EAST),
    EAST(EnumFacing.EAST),
    SOUTH_EAST(EnumFacing.SOUTH);

    public static final OrdinalFacing[] VALUES = values();

    public static final OrdinalFacing[] CARDINALS = new OrdinalFacing[] {
            SOUTH, WEST, NORTH, EAST
    };

    public static final OrdinalFacing[] ORDINALS = new OrdinalFacing[] {
            SOUTH_WEST, NORTH_WEST, NORTH_EAST, SOUTH_EAST
    };

    private final EnumFacing cardinalEquivalent;

    OrdinalFacing(EnumFacing cardinalEquivalent) {
        this.cardinalEquivalent = cardinalEquivalent;
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

    public int getIndex() {
        return ordinal();
    }

    public int getCardinalIndex() {
        if (isCardinal()) {
            switch (this) {
                case SOUTH: return 0;
                case WEST:  return 1;
                case NORTH: return 2;
                case EAST:  return 3;
            }
        }
        throw new IllegalStateException(name() + " has no cardinal index!");
    }

    public int getOrdinalIndex() {
        if (!isCardinal()) {
            switch (this) {
                case SOUTH_WEST: return 0;
                case NORTH_WEST: return 1;
                case NORTH_EAST: return 2;
                case SOUTH_EAST: return 3;
            }
        }
        throw new IllegalStateException(name() + " has no ordinal index!");
    }

    public int getAngle() {
        return ordinal() * 45;
    }

    public EnumFacing getCardinal() {
        return cardinalEquivalent;
    }

    public OrdinalFacing getPrimary() {
        switch (this) {
            case SOUTH_WEST: return SOUTH;
            case NORTH_WEST: return NORTH;
            case NORTH_EAST: return NORTH;
            case SOUTH_EAST: return SOUTH;
        }
        return this;
    }

    public Optional<OrdinalFacing> getSecondary() {
        switch (this) {
            case SOUTH_WEST: return Optional.of(WEST);
            case NORTH_WEST: return Optional.of(WEST);
            case NORTH_EAST: return Optional.of(EAST);
            case SOUTH_EAST: return Optional.of(EAST);
        }
        return Optional.empty();
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
