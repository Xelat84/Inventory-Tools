package net.xelat.mc.itools.network;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class RequestScanPacket {
	public int xPos;
	public int yPos;
	public int zPos;
	ItemStack item;

	public RequestScanPacket() {
	}
	
	public void read(DataInputStream data) throws IOException {
		xPos = data.read();
		yPos = data.read();
		zPos = data.read();
		int itemID = data.read();
		item = new ItemStack(itemID, data.read(), data.read());
	}
	
	public TileEntity getTileEntity(World worldObj) {
		return worldObj.getBlockTileEntity(xPos, yPos, zPos);
	}
	
	
}
