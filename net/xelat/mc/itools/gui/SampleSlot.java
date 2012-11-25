package net.xelat.mc.itools.gui;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;

public class SampleSlot extends CustomDummySlot implements IInteractiveSlot {

	public SampleSlot(IInventory inventory, int slotId, int xPos, int yPos) {
		super(inventory, slotId, xPos, yPos);
		acceptItems = true;
	}

	@Override
	public void processClick(int mouseButton, int isShift, EntityPlayer entityplayer) {
		InventoryPlayer inventoryplayer = entityplayer.inventory;
		ItemStack currentlyEquippedStack = inventoryplayer.getItemStack();
		if (currentlyEquippedStack != null) {
			ItemStack sampleItem = currentlyEquippedStack.copy();
			sampleItem.stackSize = 1;
			putStack(sampleItem);
		}
	}

}
