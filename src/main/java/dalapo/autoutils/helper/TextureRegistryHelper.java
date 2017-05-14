package dalapo.autoutils.helper;

import dalapo.autoutils.reference.NameList;

public class TextureRegistryHelper {
	public static String formatTexName(String filename)
	{
		return NameList.MODID + ":" + filename;
	}
	
	public static String formatGuiName(String filename)
	{
		String formatted = "autoutils:textures/gui/" + filename + ".png";
		return formatted;
	}
}