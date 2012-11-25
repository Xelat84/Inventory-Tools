package net.xelat.mc.itools.gui;

import net.minecraft.src.EntityPlayer;

public interface IInteractiveSlot {
	void processClick(int mouseButton, int isShift, EntityPlayer entityplayer);
}
