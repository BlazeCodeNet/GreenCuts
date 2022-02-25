package net.blazecode.greencuts;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.tinyremapper.extension.mixin.common.Logger;

public class GreenMod implements ModInitializer
{

	public static final String MODID = "greencuts";

	private static GreenConfig config;
	private static Logger logger;

	@Override
	public void onInitialize( )
	{
		logger = new Logger(Logger.Level.INFO);
		config = new GreenConfig();
	}
	
	public static GreenConfig getConfig()
	{
		return config;
	}
	
	public static void debugLog(String msg)
	{
		logger.info( "[" + MODID + "]" + msg);
	}
}
