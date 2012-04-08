package me.almostcrimes.ACFishing;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.bukkit.entity.*;
import org.bukkit.ChatColor;
//import org.bukkit.World;
//import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

/**
 * ACFishing for Bukkit
 * 
 * @author almostcrimes
 * 
 * 
 * 			ORIGINAL COMMENTS spoonikle (original author)
 *         A Very special thanks to: Yurij - great code FullWall - 
 *         Good advice, and spotting big problems before they hurt my head
 *         darknesschaos - Words of encouragement Edward Hand - great code and
 *         to Samkio - For his Tutorials and helping me when my head hurt the
 *         most.
 * 
 */
public class ACFishing extends JavaPlugin 
{
	public final CatchFish catchFish = new CatchFish();
	public final DropFish dropFish = new DropFish();
	public final RodActivation rodActivation = new RodActivation(this);
	public final Logger logger = Logger.getLogger("Minecraft");

	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

	public void onDisable() 
	{
		PluginDescriptionFile _pdFile = this.getDescription();
		this.logger.info(_pdFile.getName() + " is now disabled.");
	}

	public void onEnable() 
	{
		PluginManager _pm = getServer().getPluginManager();
		PluginDescriptionFile _pdFile = this.getDescription();

		//	Register plugin events
		_pm.registerEvents(this.catchFish, this);
		_pm.registerEvents(this.dropFish, this);
		_pm.registerEvents(this.rodActivation, this);

		//	Display logger output
		this.logger.info(_pdFile.getName() + " version " + _pdFile.getVersion() + " is enabled.");
	}

	public boolean isDebugging(final Player player) 
	{
		if (debugees.containsKey(player)) 
		{
			return debugees.get(player);
		} 
		else 
		{
			return false;
		}
	}

	public void setDebugging(final Player player, final boolean value) 
	{
		debugees.put(player, value);
	}

	public class CatchDelay 
	{
		Timer timer;

		public CatchDelay(int seconds) 
		{
			timer = new Timer();
			timer.schedule(new RemindTask(), seconds * 1000);
		}

		class RemindTask extends TimerTask 
		{
			@SuppressWarnings("static-access")
			public void run() 
			{
				Player player = rodActivation.fishingPlayer;
				long rodCastTime = rodActivation.fishingTime.get(player);
				ItemStack fish1 = new ItemStack(349, 1);
				int _lightLevel = player.getTargetBlock(null, 20).getLightLevel();
				
				player.sendMessage(ChatColor.BLUE + "Your current light level is:" + _lightLevel);

				if (_lightLevel >= 0 && _lightLevel <= 5)	//	Light Level 0-5
				{
					if (catchFish.isCaughtFish() == true && dropFish.isDropedFish() == false && rodCastTime >= 5000L)
					{
						player.getInventory().addItem(fish1);
						player.getInventory().addItem(fish1);
						player.getInventory().addItem(fish1);
						player.sendMessage(ChatColor.GOLD + "You caught a lot of fish!");
					}
					else if (catchFish.isCaughtFish() == false && dropFish.isDropedFish() == false && rodCastTime >= 10000L)	//	Didn't catch any fish after waiting awhile
					{
						//	Spawning entities will go here
						//	-Squid
						//	-Skeleton
						player.sendMessage(ChatColor.RED + "MOB SPAWN");
					}
				}
				else if (_lightLevel >= 6 && _lightLevel <= 10)
				{
					if (catchFish.isCaughtFish() == false && dropFish.isDropedFish() == false && rodCastTime >= 10000L) 
					{
						int catchChance = (int) (Math.random() * 31 + 1);	//	Generate a random number 1 - 31

						/*
						 * 	FISH
						 */
						if (catchChance == 1 || catchChance == 2 || catchChance == 3 || catchChance == 4) 
						{
							player.sendMessage(ChatColor.DARK_AQUA + "Oh Joy, a fish...");
							player.getInventory().addItem(fish1);
						}
						/*
						 * 	LEATHER BOOTS WITH FISH
						 */
						if (catchChance == 5 || catchChance == 6) 
						{
							ItemStack lBoots = new ItemStack(301, 1, (short) 20);
							player.sendMessage(ChatColor.DARK_AQUA + "You Reel in Some old boots. A Fish is stuck in one!");
							player.getInventory().addItem(fish1);
							player.getInventory().addItem(lBoots);
						}
						/*
						 * 	LEATHER CAP WITH FISH
						 */
						if (catchChance == 7) 
						{
							ItemStack lHelmet = new ItemStack(298, 1, (short) 20);
							player.sendMessage(ChatColor.DARK_AQUA + "You Reel in a Hat... With a fish inside!!");
							player.getInventory().addItem(fish1);
							player.getInventory().addItem(lHelmet);
						}
						/*
						 * 	DIRT
						 */
						if (catchChance == 8 || catchChance == 30) 
						{
							ItemStack dirt = new ItemStack(3, 1);
							player.sendMessage(ChatColor.RED + "all you got was dirt... FAIL");
							player.getInventory().addItem(dirt);
						}
						/*
						 * 	FISH WITH BONES
						 */
						if (catchChance == 9) 
						{
							ItemStack bone = new ItemStack(352, 1);
							player.sendMessage(ChatColor.DARK_AQUA + "You Caught a Fish. It then caughs up a bone... EWW");
							player.getInventory().addItem(fish1);
							player.getInventory().addItem(bone);
						}
						/*
						 * 	FISH WITH 2 BONES
						 */
						if (catchChance == 10) 
						{
							ItemStack bones = new ItemStack(352, 2);
							player.sendMessage(ChatColor.DARK_AQUA + "You Caught a Fish. It then coughs up 2 bones... EWWW");
							player.getInventory().addItem(fish1);
							player.getInventory().addItem(bones);
						}
						/*
						 *	FISH AND EXTRA FISH
						 */
						if (catchChance == 11) 
						{
							int bigChance = (int) (Math.random() * 3 + 1);
							if (bigChance == 1 || bigChance == 2) 
							{
								player.sendMessage(ChatColor.DARK_AQUA + "Oh Joy, a fish...");
								player.getInventory().addItem(fish1);
							} 
							else 
							{
								player.getInventory().addItem(fish1);
								player.getInventory().addItem(fish1);
								player.sendMessage(ChatColor.GOLD + "You nice fishing!");
							}
						}
						/*
						 * 	BOOTS
						 */
						if (catchChance == 12 || catchChance == 15 || catchChance == 29) 
						{
							ItemStack lBoots = new ItemStack(301, 1, (short) 20);
							player.sendMessage(ChatColor.DARK_AQUA + "You Reel in Some old boots, but no fish.");
							player.getInventory().addItem(lBoots);
						}
						/*
						 * 	LEATHER CAP
						 */
						if (catchChance == 13 || catchChance == 14 || catchChance == 28) 
						{
							ItemStack lHelmet = new ItemStack(298, 1, (short) 20);
							player.sendMessage(ChatColor.DARK_AQUA 	+ "You Reel in a Hat... Its empty.");
							player.getInventory().addItem(lHelmet);
						}
						/*
						 * 	NOTHING
						 */
						if (catchChance >= 16 && catchChance <= 20 || catchChance == 27) 
						{
							player.sendMessage(ChatColor.RED + "Nothing special is on the hook...");
						}
						/*
						 *	STRING
						 */
						if (catchChance >= 22 && catchChance <= 24) 
						{
							ItemStack sString = new ItemStack(287, 1);
							player.getInventory().addItem(sString);
							player.sendMessage(ChatColor.AQUA + "Some string got caught in your fishing line...");
						}
						/*
						 * 	WOODEN BOWLS
						 */
						if (catchChance == 31)
						{
							/*
							 * 1 in 10 chance of getting a wooden bowl with gold
							 */
							int bigChance = (int) (Math.random() * 10 + 1);
							if (bigChance >= 1 && bigChance <= 8) 
							{
								player.sendMessage(ChatColor.GREEN + "You found a wooden bowl... weird");
								ItemStack sBowl = new ItemStack(281, 1);
								player.getInventory().addItem(sBowl);
							} 
							else 
							{
								player.sendMessage(ChatColor.GREEN + "You found a wooden bowl... Some gold was inside!!");
								ItemStack sBowl = new ItemStack(281, 1);
								ItemStack sGold = new ItemStack(14, 1);
								player.getInventory().addItem(sBowl);
								player.getInventory().addItem(sGold);
							}
						}
					}
				}
				else if (_lightLevel >= 11 && _lightLevel <= 15)
				{
					/*
					 * 1 in 8 chance catching a fish
					 */
					int bigChance = (int) (Math.random() * 8 + 1);
					if (bigChance >= 1 && bigChance <= 6) 
					{
						player.sendMessage(ChatColor.GREEN + "It seems to bright to fish here.");
					} 
					else 
					{
						player.sendMessage(ChatColor.GREEN + "You caught a fish in the bright light! That is quite rare!");
						player.getInventory().addItem(fish1);
					}
				}

				timer.cancel(); // Terminate the timer thread
			}

		}

	}

}
