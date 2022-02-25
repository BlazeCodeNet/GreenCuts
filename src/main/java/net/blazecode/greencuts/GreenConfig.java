package net.blazecode.greencuts;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.nio.file.Path;

public class GreenConfig
{
    public static final int configVersion = 1;
    
    public boolean getEnabled()
    {
        return rootNode.node(ENABLED_TAG).getBoolean(true);
    }
    
    public int getAutoPlantTicks()
    {
        return rootNode.node(AUTO_PLANT_TICKS_TAG).getInt(40);
    }
    public int getAutoPlantChance()
    {
        return rootNode.node(AUTO_PLANT_CHANCE_TAG).getInt(80);
    }
    
    public void setupConfig() throws SerializationException
    {
        int checkedVersion = rootNode.node(VERSION_TAG).getInt(0);
        if(checkedVersion == configVersion)
        {
            return;
        }
        
        rootNode.node(ENABLED_TAG).comment(ENABLED_TAG_COMMENT).set(getEnabled());
        rootNode.node(AUTO_PLANT_TICKS_TAG).comment(AUTO_PLANT_TICKS_TAG_COMMENT).set(getAutoPlantTicks());
        rootNode.node(AUTO_PLANT_CHANCE_TAG).comment(AUTO_PLANT_CHANCE_TAG_COMMENT).set(getAutoPlantChance());
        
        rootNode.node(VERSION_TAG).comment(VERSION_TAG_COMMENT).set(configVersion);
        save();
    }
    
    // NODE LOCATION TAGS & COMMENTS
    private static final String VERSION_TAG = "_version_";
    private static final String VERSION_TAG_COMMENT = "!! DO NOT CHANGE THE VERSION !!";
    private static final String ENABLED_TAG = "enabled";
    private static final String ENABLED_TAG_COMMENT = "Toggles this entire mod on and off.";
    private static final String AUTO_PLANT_TICKS_TAG = "auto_plant_ticks";
    private static final String AUTO_PLANT_TICKS_TAG_COMMENT = "Auto-Plant timer in ticks; 20 ticks = 1 second.\n MUST be less than 5800 ticks!\n200 ticks(10 seconds) is recommended.";
    private static final String AUTO_PLANT_CHANCE_TAG = "auto_plant_chance";
    private static final String AUTO_PLANT_CHANCE_TAG_COMMENT = "The chance between 0-100 of a sapling to be auto-planted";
    
    
    public GreenConfig()
    {
        loader = HoconConfigurationLoader.builder()
                .path(Path.of("./config/" + GreenMod.MODID + ".conf"))
                .build();
        try
        {
            rootNode = loader.load();
            setupConfig();
        }
        catch (IOException ex)
        {
            GreenMod.debugLog("Error occured loading config:" + ex.getMessage());
            if(ex.getCause() != null)
            {
                ex.getCause().printStackTrace();
            }
            rootNode = null;
        }
    }
    
    private boolean save()
    {
        try
        {
            loader.save(rootNode);
            return true;
        }
        catch (final ConfigurateException ex)
        {
            GreenMod.debugLog("Unable to save config for '" + GreenMod.MODID + "'! Error: " + ex.getMessage());
        }
        return false;
    }
    
    private CommentedConfigurationNode getRootNode()
    {
        return rootNode;
    }
    
    private final HoconConfigurationLoader loader;
    private CommentedConfigurationNode rootNode;
}
