package net.xelat.mc.itools;


import java.util.ArrayList;
import java.util.List;

import buildcraft.core.network.BuildCraftPacket;
import buildcraft.core.network.PacketSlotChange;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.xelat.mc.itools.gui.BaseInventory;
import net.xelat.mc.itools.gui.MaskInventorySupplier;
import net.xelat.mc.itools.gui.SearchResultInventory;
import net.xelat.mc.itools.network.PacketIds;
import net.xelat.mc.itools.network.SlotSearchResultPacket;

public class TileInventorySupplier extends TileEntity implements IInventory {
	private BaseInventory internalStorage;
	private MaskInventorySupplier maskSupplier;
	private int _supplyTick = 0;
	public SearchResultInventory dummyInventory;
	
	public TileInventorySupplier() {
		internalStorage = new BaseInventory(18);
		maskSupplier = new MaskInventorySupplier(9);
	}
	
	public MaskInventorySupplier getMaskSupplier() {
		return maskSupplier;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		_supplyTick++;
		if (_supplyTick >= 20) {
			_supplyTick = 0;
			
			IInventory target = getTargetInventory();
			if (target != null) {
				//InventoryTools.logger.info("I see inventory!");
				maskSupplier.supplyInventory(this, target);
			}
			else {
				//InventoryTools.logger.info("Can't see inventory!");
			}
		}
	}
	
	public IInventory getTargetInventory() {
		//FIXME fix block access
		BlockInventorySupplier block = (BlockInventorySupplier)(Block.blocksList[BlockInventorySupplier.BLOCK_ID]);
//		if (!(blockType instanceof BlockInventorySupplier)) {
//			InventoryTools.logger.info("Wrong block type!");
//			return;
//		}
		
		return block.getFacingBlockInventory(worldObj, xCoord, yCoord, zCoord);
	}
	
	@Override
	public int getSizeInventory() {
		return internalStorage.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return internalStorage.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack item = internalStorage.getStackInSlot(i);
		if (item == null) {
			return null;
		}
		
		if (item.stackSize <= j) {
			internalStorage.setInventorySlotContents(i, null);
			return item;
		}
		
		ItemStack itemstack1 = item.splitStack(j);
		
		if (item.stackSize <= 0) {
			internalStorage.setInventorySlotContents(i, null);
		}
//		onInventoryChanged();
		return itemstack1;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack item = internalStorage.getStackInSlot(slot);
		internalStorage.setInventorySlotContents(slot, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		internalStorage.setInventorySlotContents(i, itemstack);
	}

	@Override
	public String getInvName() {
		return "Inventory Supplier";
	}

	@Override
	public int getInventoryStackLimit() {
		return internalStorage.getInventoryStackLimit();
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
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		NBTTagCompound inventoryTag = new NBTTagCompound();
		internalStorage.writeToNBT(inventoryTag);
		nbtTagCompound.setTag("inventory", inventoryTag);
		NBTTagCompound maskTag = new NBTTagCompound();
		maskSupplier.writeToNBT(maskTag);
		nbtTagCompound.setTag("mask", maskTag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		NBTTagCompound p = (NBTTagCompound) nbtTagCompound.getTag("inventory");
		internalStorage.readFromNBT(p);
		NBTTagCompound maskTag = (NBTTagCompound) nbtTagCompound.getTag("mask");
		maskSupplier.readFromNBT(maskTag);
	}

	public BuildCraftPacket getSearchResultPacket(World worldObj, ItemStack sampleItem) {
		List<LinkedItemStack> list = new ArrayList<LinkedItemStack>();
		IInventory targetInventory = getTargetInventory();
		if (targetInventory == null) {
			InventoryTools.logger.info("Target Inventory not found :(");
		}
		else {
			int l = targetInventory.getSizeInventory();
			InventoryTools.logger.info("Target inventory size=" + Integer.toString(l));
			InventoryTools.logger.info("Target inventory type=" + targetInventory.getClass().getName());
			for (int i = 0; i < l; i++) {
				ItemStack targetItem = targetInventory.getStackInSlot(i);
				if (targetItem == null) {
					InventoryTools.logger.info("Stack in slot " + Integer.toString(i) + " is null");
					continue;
				}
				InventoryTools.logger.info("Item[" + Integer.toString(i) + "] = " + targetItem.getItemName());
				if (targetItem.itemID != sampleItem.itemID || (sampleItem.getHasSubtypes() && sampleItem.getItemDamage() != targetItem.getItemDamage())) {
					InventoryTools.logger.info(Integer.toString(sampleItem.itemID) + " != " + Integer.toString(targetItem.itemID));
					continue;
				}
				//container.addFoundResult(sampleItem, i);
				list.add(new LinkedItemStack(sampleItem, i));
				InventoryTools.logger.info("Found item: " + targetItem.getItemName());
				//found = true;
			}
		}
		SlotSearchResultPacket packet = new SlotSearchResultPacket(PacketIds.RESPONSE_SCAN, xCoord, yCoord, zCoord, list);
		return packet;
	}

	public void setSearchResult(List<LinkedItemStack> list) {
		if (dummyInventory == null) return;
		dummyInventory.update(list);
	}
	
}
