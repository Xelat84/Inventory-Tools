package net.xelat.mc.itools.gui;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;

public class InteractiveSlot extends CustomDummySlot implements IInteractiveSlot {

	public ISlotClickHandler clickHandler;
	
	public InteractiveSlot(IInventory inventory, int slotId, int xPos, int yPos) {
		super(inventory, slotId, xPos, yPos);
	}

	@Override
	public void processClick(int mouseButton, int isShift, EntityPlayer entityplayer) {
		if (clickHandler != null) {
			clickHandler.handleSlotClick(this);
		}
	}

}
