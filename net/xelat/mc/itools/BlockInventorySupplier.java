package net.xelat.mc.itools;

import buildcraft.BuildCraftBuilders;
import buildcraft.core.DefaultProps;
import buildcraft.core.proxy.CoreProxy;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockInventorySupplier extends BlockContainer {

	public BlockInventorySupplier() {
		super(4092, Material.iron);
		
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		setBlockName("inventorySupplier");
		
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {

		// Drop through if the player is sneaking
		if (entityplayer.isSneaking())
			return false;

		if (!CoreProxy.proxy.isRenderWorld(world))
			entityplayer.openGui(InventoryTools.instance, GuiIds.SUPPLIER, world, i, j, k);
		return true;

	}
	

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileInventorySupplier();
	}

	@Override
	public String getTextureFile() {
		return "/gfx/itools/itools.png";
	}
	
	@SuppressWarnings({ "all" })
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return 1;
	}
	
	@Override
	public int getBlockTextureFromSide(int i) {
		return 1;
	}
	
}
