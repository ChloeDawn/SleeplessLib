package net.sleepless.lib.block.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.sleepless.lib.util.capability.CapabilityStorage;
import net.sleepless.lib.util.serializable.SerializerStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class SimpleBlockEntity extends TileEntity {

    private final CapabilityStorage capabilities;
    private final SerializerStorage serializers;

    {
        CapabilityStorage.Builder capabilityBuilder = CapabilityStorage.create();
        SerializerStorage.Builder serializerBuilder = SerializerStorage.create();
        collectCapabilities(capabilityBuilder);
        collectSerializers(serializerBuilder);
        this.capabilities = capabilityBuilder.build();
        this.serializers = serializerBuilder.build();
    }

    protected void collectCapabilities(CapabilityStorage.Builder builder) {}

    protected void collectSerializers(SerializerStorage.Builder builder) {}

    @Override
    public final void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        serializers.deserializeAll(nbt);
    }

    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        serializers.serializeAll(nbt);
        return nbt;
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = super.getUpdateTag();
        serializers.serializeAll(nbt, true);
        return nbt;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        String name = getBlockType().getUnlocalizedName();
        return new TextComponentTranslation(name + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound nbt = pkt.getNbtCompound();
        serializers.deserializeAll(nbt, true);
    }

    @Override
    public final boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
        return capabilities.test(capability, side);
    }

    @Nullable
    @Override
    public final <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
        return capabilities.get(capability, side);
    }

}
