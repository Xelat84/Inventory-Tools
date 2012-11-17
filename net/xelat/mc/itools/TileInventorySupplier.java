package net.xelat.mc.itools;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.xelat.mc.itools.gui.TempInventory;

public class TileInventorySupplier extends TileEntity implements IInventory {
	private ItemStack[] internalStorage;
	private int[] targetMask;
	private IInventory mask;
	
	public TileInventorySupplier() {
		internalStorage = new ItemStack[18];
		mask = new TempInventory(9);
		targetMask = new int[9];
	}
	
	public IInventory getMask() {
		return mask;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		//TODO check inventory and supply it if needed
	}
	
	@Override
	public int getSizeInventory() {
		return internalStorage.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return internalStorage[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (internalStorage[i] == null) {
			return null;
		}
		
		if (internalStorage[i].stackSize <= j) {
			ItemStack itemstack = internalStorage[i];
			internalStorage[i] = null;
//			onInventoryChanged();
			return itemstack;
		}
		
		ItemStack itemstack1 = internalStorage[i].splitStack(j);
		
		if (internalStorage[i].stackSize == 0) {
			internalStorage[i] = null;
		}
		
//		onInventoryChanged();
		return itemstack1;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (internalStorage[slot] == null) {
			return null;
		}
		ItemStack toReturn = internalStorage[slot];
		internalStorage[slot] = null;
		return toReturn;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		internalStorage[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "Inventory Supplier";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	
}
