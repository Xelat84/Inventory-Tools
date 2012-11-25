package net.xelat.mc.itools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.ItemStack;

public class LinkedItemStack {
	ItemStack item;
	int slotId;
	
	public LinkedItemStack(ItemStack item, int slotId) {
		this.item = item;
		this.slotId = slotId;
	}
	
	public LinkedItemStack() {
	}

	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(item.itemID);
		data.writeInt(item.stackSize);
		data.writeInt(item.getItemDamage());
		data.writeInt(slotId);
	}

	public void readData(DataInputStream data) throws IOException {
		int itemID = data.read();
		item = new ItemStack(itemID, data.read(), data.read());
		slotId = data.read();
	}
}
