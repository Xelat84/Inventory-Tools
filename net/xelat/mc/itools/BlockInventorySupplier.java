package net.xelat.mc.itools;

import buildcraft.BuildCraftBuilders;
import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import buildcraft.api.tools.IToolWrench;
import buildcraft.core.DefaultProps;
import buildcraft.core.proxy.CoreProxy;
import buildcraft.core.utils.Utils;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockInventorySupplier extends BlockContainer {
	public static final int BLOCK_ID = 4092;
	
	private static final int blockTextureFront = 1;
	private static final int blockTextureTop = 17;
	private static final int blockTextureSides = 0;

	public BlockInventorySupplier() {
		super(BLOCK_ID, Material.iron);
		
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		setBlockName("inventorySupplier");
		
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {

		// Drop through if the player is sneaking
		if (entityplayer.isSneaking()) {
			return false;
		}
		
		Item equipped = entityplayer.getCurrentEquippedItem() != null ? entityplayer.getCurrentEquippedItem().getItem() : null;
		if (equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(entityplayer, i, j, k)) {

			int meta = world.getBlockMetadata(i, j, k);

			switch (Orientations.values()[meta]) {
			case XNeg:
				world.setBlockMetadata(i, j, k, Orientations.ZPos.ordinal());
				break;
			case XPos:
				world.setBlockMetadata(i, j, k, Orientations.ZNeg.ordinal());
				break;
			case ZNeg:
				world.setBlockMetadata(i, j, k, Orientations.XNeg.ordinal());
				break;
			case ZPos:
			default:
				world.setBlockMetadata(i, j, k, Orientations.XPos.ordinal());
				break;
			}

			world.markBlockNeedsUpdate(i, j, k);
			((IToolWrench) equipped).wrenchUsed(entityplayer, i, j, k);
			return true;
		}
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
		int m = iblockaccess.getBlockMetadata(i, j, k);
		if (l == 1) {
			return blockTextureTop;
		}
		return getBlockTextureFromSideAndMetadata(l, m);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if (j == 0 && i == 3) {
			return blockTextureFront;
		}

		if (i == 1) {
			return blockTextureTop;
		}

		if (i == j) {
			return blockTextureFront;
		}

		return blockTextureSides;
	}
	
	@Override
	public int getBlockTextureFromSide(int i) {
		return 1;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
		super.onBlockPlacedBy(world, i, j, k, entityliving);

		Orientations orientation = Utils.get2dOrientation(new Position(entityliving.posX, entityliving.posY, entityliving.posZ),
				new Position(i, j, k));

		world.setBlockMetadataWithNotify(i, j, k, orientation.reverse().ordinal());
	}

	private class IntPos {
		public int x;
		public int y;
		public int z;
		public IntPos(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public IInventory getFacingBlockInventory(World worldObj, int xCoord, int yCoord, int zCoord) {
		IntPos targetPos = getFaceAdjacentPosition(worldObj, xCoord, yCoord, zCoord);
		if (!worldObj.blockExists(targetPos.x, targetPos.y, targetPos.z) || !worldObj.blockHasTileEntity(targetPos.x, targetPos.y, targetPos.z)) {
			return null;
		}
		
		TileEntity tile = worldObj.getBlockTileEntity(targetPos.x, targetPos.y, targetPos.z);
		if (tile instanceof IInventory) {
			return (IInventory) tile;
		}
		return null;
	}

	@SuppressWarnings("incomplete-switch")
	private IntPos getFaceAdjacentPosition(World worldObj, int xCoord, int yCoord, int zCoord) {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		IntPos pos = new IntPos(xCoord, yCoord, zCoord);
		switch (Orientations.values()[meta]) {
		case XNeg:
			pos.x--;
			break;
		case ZNeg:
			pos.z--;
			break;
		case XPos:
			pos.x++;
			break;
		case ZPos:
			pos.z++;
			break;
		}
		return pos;
	}
	
	
}
