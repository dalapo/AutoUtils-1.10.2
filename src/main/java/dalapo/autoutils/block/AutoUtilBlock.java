package dalapo.autoutils.block;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.autoutils.helper.TextureRegistryHelper;
import dalapo.autoutils.reference.NameList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoUtilBlock extends Block {

	protected final String name;
	
	public AutoUtilBlock(Material mtl, String name) {
		super(mtl);
		this.name = name;
		setUnlocalizedName(NameList.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.REDSTONE);
		setHardness(4F);
		setHarvestLevel("pickaxe", 1);
		setResistance(4F);
//		this.setBlockTextureName(name);
	}
	
	protected String getName()
	{
		return name;
	}
	
	public IBlockState defaultState()
	{
		return blockState.getBaseState();
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", NameList.MODID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}