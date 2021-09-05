package net.blazecode.greencuts;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment( EnvType.SERVER )
public class GreenMod implements DedicatedServerModInitializer
{

	public static final String MODID = "greencuts";

	@Override
	public void onInitializeServer( )
	{
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
	}

	public static ModConfig getConfig()
	{
		if (config == null)
		{
			config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		}
		return config;
	}

	private static ModConfig config;

	@Config(name = MODID)
	public static class ModConfig implements ConfigData
	{
		@Comment("Toggles this entire mod on or off")
		boolean enabled = true;

		@Comment("Auto-Plant timer in ticks; 20 ticks = 1 second.\n MUST be less than 5800 ticks!\n200 ticks(10 seconds) is recommended.")
		int autoPlantTicks = 200;

		@Comment("The chance between 0-100 of a sapling to be auto-planted")
		int autoPlantChance = 100;

		public boolean getEnabled()
		{
			return enabled;
		}

		public int getPlantTicks()
		{
			if(autoPlantTicks > 5800)
			{
				return 5800;
			}
			return autoPlantTicks;
		}
		public int getPlantChance()
		{
			if(autoPlantChance < 0 || autoPlantChance > 100)
			{
				return 100;
			}
			return autoPlantChance;
		}
	}
}
