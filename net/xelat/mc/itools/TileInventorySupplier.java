package net.xelat.mc.itools;


import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.xelat.mc.itools.gui.BaseInventory;
import net.xelat.mc.itools.gui.MaskInventorySupplier;

public class TileInventorySupplier extends TileEntity implements IInventory {
	private BaseInventory internalStorage;
	private MaskInventorySupplier maskSupplier;
	private int _supplyTick = 0;
	
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
	
}
