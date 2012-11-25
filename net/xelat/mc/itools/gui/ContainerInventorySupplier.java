package net.xelat.mc.itools.gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.xelat.mc.itools.InventoryTools;
import net.xelat.mc.itools.TileInventorySupplier;

public class ContainerInventorySupplier extends Container implements ISlotClickHandler {
	
	private TileInventorySupplier supplier;
	
	public BaseInventory sampleInventory;
	public SearchResultInventory resultInventory;
//	public int[] resultTargetSlotIds;
//	private int _nextResultSlot = 0;
	
	public ContainerInventorySupplier(IInventory playerInventory, TileInventorySupplier supplier) {
		this.supplier = supplier;
		
		sampleInventory = new BaseInventory(1);
		resultInventory = new SearchResultInventory(9);
		//TODO only on client side!!!
		supplier.dummyInventory = resultInventory;
		
//		resultTargetSlotIds = new int[9];
		
		
		// Internal Inventory
		
		final int xInternal = 8;
		final int yInternal = 101;
		
		for (int k = 0; k < 2; k++) {
			for (int j1 = 0; j1 < 9; j1++) {
				addSlotToContainer(new Slot(supplier, j1 + k * 9, xInternal + j1 * 18, yInternal + k * 18));
			}
		}
		
		// User Inventory
		
		final int xInventory = 8;
		final int yInventory = 140;
		
		for (int l = 0; l < 3; l++) {
			for (int k1 = 0; k1 < 9; k1++) {
				addSlotToContainer(new Slot(playerInventory, k1 + l * 9 + 9, xInventory + k1 * 18, yInventory + l * 18));
			}
		}
		
		// User HotBar
		
		final int xFastSlots = 8;
		final int yFastSlots = 198;
		
		for (int i1 = 0; i1 < 9; i1++) {
			addSlotToContainer(new Slot(playerInventory, i1, xFastSlots + i1 * 18, yFastSlots));
		}
		
		
		// 
		
		
		final int xMask = 8;
		final int yMask = 76;
		
		IInventory mask = supplier.getMaskSupplier();
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new CounterSlot(mask, i, xMask + i * 18, yMask));
		}
		
		final int xFound = 8;
		final int yFound = 34;
		
		for (int i = 0; i < 9; i++) {
			InteractiveSlot slot = new InteractiveSlot(resultInventory, i, xFound + i * 18, yFound);
			slot.clickHandler = this;
			addSlotToContainer(slot);
		}
		
		addSlotToContainer(new SampleSlot(sampleInventory, 0, 80, 10));
		
		
	}
	/*
	public void clearFoundResults() {
		for (int i = 0; i < _nextResultSlot; i++) {
			resultInventory.setInventorySlotContents(i, null);
		}
		_nextResultSlot = 0;
	}
	
	public void addFoundResult(ItemStack item, int targetSlotId) {
		if (_nextResultSlot == resultInventory.getSizeInventory()) {
			return;
		}
		
		resultTargetSlotIds[_nextResultSlot] = targetSlotId;
		resultInventory.setInventorySlotContents(_nextResultSlot, item);
		_nextResultSlot++;
	}
	*/
	@Override
	public ItemStack slotClick(int slotId, int mouseButton, int isShift, EntityPlayer entityplayer) {
		// Handling only dummy slots!
		if (slotId < 0 || !(inventorySlots.get(slotId) instanceof IInteractiveSlot)) {
			return super.slotClick(slotId, mouseButton, isShift, entityplayer);
		}
		
		IInteractiveSlot slot = (IInteractiveSlot)inventorySlots.get(slotId);
		slot.processClick(mouseButton, isShift, entityplayer);
		
		InventoryPlayer inventoryplayer = entityplayer.inventory;
		ItemStack currentlyEquippedStack = inventoryplayer.getItemStack();
		
		return currentlyEquippedStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return supplier.isUseableByPlayer(entityplayer);
	}
	
	@Override
	protected void retrySlotClick(int i, int j, boolean flag, EntityPlayer entityplayer) {
	}

	@Override
	public void handleSlotClick(Slot slot) {
		CustomDummySlot dummySlot = (CustomDummySlot)slot;
		ItemStack foundItem = dummySlot.getStack();
		if (foundItem != null) {
			int targetSlotId = dummySlot.getIndex();
			if (supplier.getMaskSupplier().addMaskItem(foundItem, targetSlotId)) {
				dummySlot.clearStack();
			}
		}
	}

	public void onGuiClosed() {
		supplier.dummyInventory = null;
	}

}
