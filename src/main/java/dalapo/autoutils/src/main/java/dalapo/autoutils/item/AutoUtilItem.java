package dalapo.autoutils.item;

import dalapo.autoutils.helper.TextureRegistryHelper;
import dalapo.autoutils.reference.NameList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AutoUtilItem extends Item {
	private final String name;
	
	public AutoUtilItem(String name)
	{
		super();
		this.name = name;
		this.setUnlocalizedName(NameList.MODID + "." + name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
	}
	
	public String getName()
	{
		return name;
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}