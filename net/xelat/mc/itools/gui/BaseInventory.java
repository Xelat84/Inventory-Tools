package net.xelat.mc.itools.gui;

import buildcraft.core.utils.INBTTagable;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class BaseInventory implements IInventory, INBTTagable {
	
	protected ItemStack[] content;
	
	public BaseInventory(int size) {
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
		if (content[i] != null && content[i].stackSize > j) {
			content[i].stackSize -= j;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		content[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
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

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		NBTTagList nbttaglist = nbttagcompound.getTagList("items");

		for (int j = 0; j < nbttaglist.tagCount(); ++j) {
			NBTTagCompound itemTag = (NBTTagCompound) nbttaglist.tagAt(j);
			int index = itemTag.getInteger("idx");
			content[index] = ItemStack.loadItemStackFromNBT(itemTag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		NBTTagList nbttaglist = new NBTTagList();
		for (int j = 0; j < content.length; ++j) {
			if (content[j] != null && content[j].stackSize > 0) {
				NBTTagCompound itemTag = new NBTTagCompound();
				nbttaglist.appendTag(itemTag);
				itemTag.setInteger("idx", j);
				content[j].writeToNBT(itemTag);
			}
		}
		nbttagcompound.setTag("items", nbttaglist);
	}
	
	
	
}
