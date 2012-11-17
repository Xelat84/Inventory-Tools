package net.xelat.mc.itools.gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.xelat.mc.itools.InventoryTools;
import net.xelat.mc.itools.TileInventorySupplier;

public class ContainerInventorySupplier extends Container {
	private static final int SLOT_MASK = 1;
	private static final int SLOT_FOUND = 2;
	private static final int SLOT_SAMPLE = 3;		
	
	IInventory playerInventory;
	TileInventorySupplier supplier;
	IInventory dummyInventory;
	public int[] foundSlotIds;
	
	public ContainerInventorySupplier(IInventory playerInventory, TileInventorySupplier supplier, IInventory dummyInventory) {
		this.playerInventory = playerInventory;
		this.supplier = supplier;
		this.dummyInventory = dummyInventory;
		foundSlotIds = new int[9];
		
		final int xInternal = 8;
		final int yInternal = 101;
		
		for (int k = 0; k < 2; k++) {
			for (int j1 = 0; j1 < 9; j1++) {
				addSlotToContainer(new Slot(supplier, j1 + k * 9, xInternal + j1 * 18, yInternal + k * 18));
			}
		}
		
		final int xInventory = 8;
		final int yInventory = 140;
		
		for (int l = 0; l < 3; l++) {
			for (int k1 = 0; k1 < 9; k1++) {
				addSlotToContainer(new Slot(playerInventory, k1 + l * 9 + 9, xInventory + k1 * 18, yInventory + l * 18));
			}
		}
		
		final int xFastSlots = 8;
		final int yFastSlots = 198;
		
		for (int i1 = 0; i1 < 9; i1++) {
			addSlotToContainer(new Slot(playerInventory, i1, xFastSlots + i1 * 18, yFastSlots));
		}
		
		final int xMask = 8;
		final int yMask = 76;
		
		IInventory mask = supplier.getMask();
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new CustomDummySlot(SLOT_MASK, mask, i, xMask + i * 18, yMask));
		}
		
		final int xFound = 8;
		final int yFound = 34;
		
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new CustomDummySlot(SLOT_FOUND, dummyInventory, i + 1, xFound + i * 18, yFound));
		}
		
		CustomDummySlot sampleSlot = new CustomDummySlot(SLOT_SAMPLE, dummyInventory, 0, 80, 10);
		sampleSlot.acceptItems = true;
		addSlotToContainer(sampleSlot);
		
		
	}
	
	@Override
	public ItemStack slotClick(int slotId, int mouseButton, int isShift, EntityPlayer entityplayer) {
		if (slotId < 0) {
			return super.slotClick(slotId, mouseButton, isShift, entityplayer);
		}
		
		if (!(inventorySlots.get(slotId) instanceof CustomDummySlot)) {
			return super.slotClick(slotId, mouseButton, isShift, entityplayer);
		}
		CustomDummySlot slot = (CustomDummySlot)inventorySlots.get(slotId);
		
		InventoryPlayer inventoryplayer = entityplayer.inventory;
		ItemStack currentlyEquippedStack = inventoryplayer.getItemStack();
		
		
		if (currentlyEquippedStack != null && slot.getType() == SLOT_SAMPLE) {
			ItemStack sampleItem = currentlyEquippedStack.copy();
			sampleItem.stackSize = 1;
			slot.putStack(sampleItem);
		}
		else if (currentlyEquippedStack == null) {
			switch (slot.getType()) {
			case SLOT_SAMPLE:
				slot.clearStack();
				break;
			case SLOT_FOUND:
				int i = slot.getIndex() - 1;
				ItemStack foundItem = slot.getStack();
				if (foundItem != null) {
					supplier.addMask(foundItem, foundSlotIds[i]);
					slot.clearStack();
				}
				break;
			case SLOT_MASK:
				slot.clearStack();
				break;
			}
		}
		return currentlyEquippedStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return supplier.isUseableByPlayer(entityplayer);
	}
	
	@Override
	protected void retrySlotClick(int i, int j, boolean flag, EntityPlayer entityplayer) {
	}

}
