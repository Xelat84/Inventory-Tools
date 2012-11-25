package net.xelat.mc.itools;


import java.util.logging.Logger;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import buildcraft.core.utils.Localization;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(name="InventoryTools", version="0.0.1", useMetadata = false, modid = "InventoryTools", dependencies="required-after:Forge@[5.0,)")
@NetworkMod(channels = {"XIT"}, packetHandler = PacketHandler.class, clientSideRequired = true, serverSideRequired = true)
//@NetworkMod(channels = {}, clientSideRequired = true, serverSideRequired = true)
public class InventoryTools {
	
	public static Logger logger = Logger.getLogger("InventoryTools");
	
	public static BlockInventorySupplier blockInventorySupplier;
	
	@Instance("InventoryTools")
	public static InventoryTools instance;
	
	
	@Init
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
	}
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
		logger.setParent(FMLLog.getLogger());
		logger.info("Starting Inventory Tools");
		logger.info("Copyright (c) Xelat, 2012");
		
		blockInventorySupplier = new BlockInventorySupplier();
		GameRegistry.registerBlock(blockInventorySupplier);
		LanguageRegistry.addName(blockInventorySupplier, "Inventory Supplier");
		
		GameRegistry.registerTileEntity(TileInventorySupplier.class, blockInventorySupplier.getBlockName());
		
		GameRegistry.addShapelessRecipe(new ItemStack(blockInventorySupplier), new Object[] {new ItemStack(Block.dirt)});
		
		Localization.addLocalization("/lang/itools/", "en_US");
		
	}
}
