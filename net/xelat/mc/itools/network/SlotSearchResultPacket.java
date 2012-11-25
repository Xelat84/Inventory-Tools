package net.xelat.mc.itools.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.xelat.mc.itools.LinkedItemStack;

import buildcraft.core.network.PacketCoordinates;

public class SlotSearchResultPacket extends PacketCoordinates {
	
	public List<LinkedItemStack> list;
	
	public SlotSearchResultPacket(int id, int x, int y, int z, List<LinkedItemStack> list) {
		super(id, x, y, z);
		this.list = list;
	}
	
	public SlotSearchResultPacket() {
		super();
	}
	
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(list.size());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).writeData(data);
		}
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		int size = data.read();
		for (int i = 0; i < size; i++) {
			LinkedItemStack item = new LinkedItemStack();
			item.readData(data);
			list.add(item);
		}
	}
}
