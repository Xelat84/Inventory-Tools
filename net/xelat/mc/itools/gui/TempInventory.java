package net.xelat.mc.itools.gui;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class TempInventory implements IInventory {
	
	private ItemStack[] content;
	
	public TempInventory(int size) {
		content = new ItemStack[size];
	}
	
	@Override
	public int getSizeInventory() {
		return content.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return content[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (content[i] == null) return null;
		if (content[i].stackSize > j) content[i].stackSize -= j;
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		content[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	
}
