package net.xelat.mc.itools.gui;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class CustomDummySlot extends Slot {

	public int _type = 0;
	public boolean acceptItems = false;
	private int _index;
	public int data;
	
	public CustomDummySlot(IInventory inventory, int slotId, int xPos, int yPos) {
		super(inventory, slotId, xPos, yPos);
		_index = slotId;
	}

	public CustomDummySlot(int type, IInventory inventory, int slotId, int xPos, int yPos) {
		super(inventory, slotId, xPos, yPos);
		_index = slotId;
		_type = type;
	}
	
	public int getType() {
		return _type;
	}
	
	public int getIndex() {
		return _index;
	}
	
	@Override
	public void putStack(ItemStack itemStack) {
		if (acceptItems) {
			super.putStack(itemStack);
		}
	}
	
	public void setStack(ItemStack itemStack) {
		super.putStack(itemStack);
	}
	
	public void clearStack() {
		super.putStack(null);
	}
}
