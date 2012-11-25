package net.xelat.mc.itools.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.ItemStack;
import buildcraft.core.DefaultProps;
import buildcraft.core.network.PacketCoordinates;

public class ItemStackPacket extends PacketCoordinates {
	protected String channel = "XIT";
	
	public ItemStack item;
	
	public ItemStackPacket(int id, int x, int y, int z, ItemStack item) {
		super(id, x, y, z);
		this.item = item;
	}
	
	public ItemStackPacket() {
	}
	
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(item.itemID);
		data.writeInt(item.stackSize);
		data.writeInt(item.getItemDamage());
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		int id = data.readInt();
		item = new ItemStack(id, data.readInt(), data.readInt());
	}
	
}
