package net.xelat.mc.itools;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.xelat.mc.itools.gui.ContainerInventorySupplier;
import net.xelat.mc.itools.gui.GuiInventorySupplier;
import net.xelat.mc.itools.gui.BaseInventory;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z)) {
			return null;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		switch (ID) {
		case GuiIds.SUPPLIER:
			if (!(tile instanceof TileInventorySupplier)) {
				return null;
			}
			return new ContainerInventorySupplier(player.inventory,	(TileInventorySupplier) tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z)) {
			return null;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		switch (ID) {
		case GuiIds.SUPPLIER:
			if (!(tile instanceof TileInventorySupplier)) {
				return null;
			}
			return new GuiInventorySupplier(player.inventory, (TileInventorySupplier)tile);
		}
		
		return null;
	}

}
