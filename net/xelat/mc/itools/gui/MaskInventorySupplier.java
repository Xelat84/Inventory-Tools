package net.xelat.mc.itools.gui;

import java.lang.reflect.Array;

import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public class MaskInventorySupplier extends BaseInventory {
	
	int[] _targetSlotIds;
	
	public MaskInventorySupplier(int size) {
		super(size);
		_targetSlotIds = new int[size];
	}
	
	public boolean addMaskItem(ItemStack item, int targetSlotId) {
		
		item.stackSize = 1;
		
		int lastEmptySlot = -1;
		for (int i = getSizeInventory() - 1; i >= 0; i--) {
			// Replace existing slot
			if (getStackInSlot(i) != null) {
				if (_targetSlotIds[i] == targetSlotId) {
					setInventorySlotContents(i, item);
					return true;
				}
			}
			else {
				lastEmptySlot = i;
			}
		}
		if (lastEmptySlot == -1) {
			return false;
		}
		
		setInventorySlotContents(lastEmptySlot, item);
		_targetSlotIds[lastEmptySlot] = targetSlotId;
		
		
		return true;
	}
	
	public void clearMaskItem(int slotId) {
		setInventorySlotContents(slotId, null);
	}
	
	public int getTargetSlotId(int slotId) {
		return _targetSlotIds[slotId];
	}
	
	public void supplyInventory(IInventory storageInventory, IInventory targetInventory) {
		int l = getSizeInventory();
		for (int i = 0; i < l; i++) {
			ItemStack item = getStackInSlot(i);
			if (item == null) {
				continue;
			}
			int targetSlotId = _targetSlotIds[i];
			ItemStack targetItem = targetInventory.getStackInSlot(targetSlotId);
			int supplyCount = targetItem == null ? item.stackSize : item.stackSize - targetItem.stackSize;
			if (supplyCount > 0) {
				supplySlot(targetInventory, targetSlotId, storageInventory, item, supplyCount);
			}
		}
	}

	private void supplySlot(IInventory targetInventory, int targetSlotId, IInventory storageInventory, ItemStack item, int supplyCount) {
		int l = storageInventory.getSizeInventory();
		ItemStack targetItem = targetInventory.getStackInSlot(targetSlotId);
		
		for (int i = 0; i < l && supplyCount > 0; i++) {
			ItemStack storageItem = storageInventory.getStackInSlot(i);
			if (storageItem == null || storageItem.itemID != item.itemID || (item.getHasSubtypes() && storageItem.getItemDamage() != item.getItemDamage())) {
				continue;
			}
			
			if (targetItem == null) {
				targetItem = storageInventory.decrStackSize(i, supplyCount);
				supplyCount -= targetItem.stackSize;
			}
			else {
				if (storageItem.stackSize > supplyCount) {
					storageItem.stackSize -= supplyCount;
					targetItem.stackSize += supplyCount;
					break;
				}
				targetItem.stackSize += storageItem.stackSize;
				supplyCount -= storageItem.stackSize;
				storageInventory.setInventorySlotContents(i, null);
			}
		}
		
		targetInventory.setInventorySlotContents(targetSlotId, targetItem);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		for (int i = 0; i < _targetSlotIds.length; i++) {
			_targetSlotIds[i] = nbttagcompound.getInteger("slot" + Integer.toString(i));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		for (int i = 0; i < _targetSlotIds.length; i++) {
			nbttagcompound.setInteger("slot" + Integer.toString(i), _targetSlotIds[i]);
		}
	}

}
