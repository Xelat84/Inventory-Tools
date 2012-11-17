package net.xelat.mc.itools.gui;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class CustomDummySlot extends Slot {

	public int _type = 0;
	public boolean acceptItems = false;
	
	public CustomDummySlot(IInventory par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	public CustomDummySlot(int type, IInventory par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
		_type = type;
	}
	
	public int getType() {
		return _type;
	}
	
	@Override
	public void putStack(ItemStack par1ItemStack) {
		if (acceptItems) {
			super.putStack(par1ItemStack);
		}
	}
}