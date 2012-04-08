package me.almostcrimes.ACFishing;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.bukkit.entity.*;
import org.bukkit.ChatColor;
import org.bukkit.World;
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
				

				if (catchFish.isCaughtFish() == true && dropFish.isDropedFish() == false && rodCastTime >= 5000L) 
				{
					player.getInventory().addItem(fish1);
					player.getInventory().addItem(fish1);
					player.getInventory().addItem(fish1);
					player.sendMessage(ChatColor.GOLD + "You got a 'BigCatch' AWESOME!");
				} 
				else if (catchFish.isCaughtFish() == false && dropFish.isDropedFish() == false && rodCastTime >= 10000L) 
				{
					int catchChance = (int) (Math.random() * 31 + 1);

					if (catchChance == 1 || catchChance == 2 || catchChance == 3 || catchChance == 4) 
					{
						player.sendMessage(ChatColor.DARK_AQUA + "Oh Joy, a fish...");
						player.getInventory().addItem(fish1);
					}
					if (catchChance == 5 || catchChance == 6) 
					{
						ItemStack lBoots = new ItemStack(301, 1, (short) 20);
						player.sendMessage(ChatColor.DARK_AQUA + "You Reel in Some old boots. A Fish is stuck in one!");
						player.getInventory().addItem(fish1);
						player.getInventory().addItem(lBoots);
					}
					if (catchChance == 7) 
					{
						ItemStack lHelmet = new ItemStack(298, 1, (short) 20);
						player.sendMessage(ChatColor.DARK_AQUA + "You Reel in a Hat... With a fish inside!!");
						player.getInventory().addItem(fish1);
						player.getInventory().addItem(lHelmet);
					}
					if (catchChance == 8 || catchChance == 30) 
					{
						ItemStack dirt = new ItemStack(3, 1);
						player.sendMessage(ChatColor.RED + "all you got was dirt... FAIL");
						player.getInventory().addItem(dirt);
					}
					if (catchChance == 9) 
					{
						ItemStack bone = new ItemStack(352, 1);
						player.sendMessage(ChatColor.DARK_AQUA + "You Caught a Fish. It then caughs up a bone... EWW");
						player.getInventory().addItem(fish1);
						player.getInventory().addItem(bone);
					}
					if (catchChance == 10) 
					{
						ItemStack bones = new ItemStack(352, 2);
						player.sendMessage(ChatColor.DARK_AQUA + "You Caught a Fish. It then coughs up 2 bones... EWWW");
						player.getInventory().addItem(fish1);
						player.getInventory().addItem(bones);
					}
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
							player.getInventory().addItem(fish1);
							player.sendMessage(ChatColor.GOLD + "You got a 'BigCatch' AWESOME!");
						}
					}
					if (catchChance == 12 || catchChance == 15 || catchChance == 29) 
					{
						ItemStack lBoots = new ItemStack(301, 1, (short) 20);
						player.sendMessage(ChatColor.DARK_AQUA + "You Reel in Some old boots, but no fish.");
						player.getInventory().addItem(lBoots);
					}
					if (catchChance == 13 || catchChance == 14 || catchChance == 28) 
					{
						ItemStack lHelmet = new ItemStack(298, 1, (short) 20);
						player.sendMessage(ChatColor.DARK_AQUA 	+ "You Reel in a Hat... Its empty.");
						player.getInventory().addItem(lHelmet);
					}
					if (catchChance >= 16 && catchChance <= 20 || catchChance == 27) 
					{
						player.sendMessage(ChatColor.RED + "Nothing special is on the hook...");
					}
/*
 * SQUID					
 */
					
//					if (catchChance >= 1 || catchChance <= 31)
//					//if (catchChance == 21) 
//					{
//						int bigChance = (int) (Math.random() * 3 + 1);
//						if (bigChance == 1 || bigChance == 2) 
//						{
//							//Entity _entity = null;
//							Player _newPlayer = (Player) player;
//							World _currentWorld = (World) player.getWorld();
//							Squid _squid = (Squid) _currentWorld;
//							
//							_squid.teleport(_newPlayer.getLocation());
//
////							CraftPlayer craftPlayer = (CraftPlayer) player;
////							CraftWorld craftWorld = (CraftWorld) craftPlayer.getWorld();
////							World world = craftWorld.getHandle();
////							e = new EntitySquid(world);
////							e.getBukkitEntity().teleport(player.getLocation());
////							world.a(e);
//							player.sendMessage(ChatColor.GOLD + "You snagged a Squid!! HOLY CRAP!!!");
//						} 
//						else 
//						{
//							//Entity _entity = null;
//							Player _newPlayer = (Player) player;
//							World _currentWorld = (World) player.getWorld();
//							Squid _squid = (Squid) _currentWorld;
//							
//							_squid.teleport(_newPlayer.getLocation());
//							
//							player.sendMessage(ChatColor.GOLD + "You snagged a Squid!! HOLY CRAP!!!");
//							player.getInventory().addItem(fish1);
//							player.sendMessage(ChatColor.AQUA + "The squid had a fish in its tentacles, SCORE!!");
//						}
//					}
					
/*
 * STRING CAUGHT
 */
					if (catchChance >= 22 && catchChance <= 24) 
					{
						ItemStack sString = new ItemStack(287, 1);
						player.getInventory().addItem(sString);
						player.sendMessage(ChatColor.AQUA + "Some string got caught in your fishing line...");
					}
					
/*
 * SKELETONS
*/
//					if (catchChance == 25) 
//					{
//						int bigChance = (int) (Math.random() * 10 + 1);
//						if (bigChance == 1) 
//						{
//							EntityLiving e = null;
//							CraftPlayer craftPlayer = (CraftPlayer) player;
//							CraftWorld craftWorld = (CraftWorld) craftPlayer.getWorld();
//							World world = craftWorld.getHandle();
//							e = new EntitySkeleton(world);
//							e.getBukkitEntity().teleport(player.getLocation());
//							world.a(e);
//							player.sendMessage(ChatColor.RED + "HOLY CRAP!! A SKELETON!! RUN!! RUN!! RUN!!!!");
//						} 
//						else 
//						{
//							player.sendMessage(ChatColor.RED + "A skeleton got caught on the line! One of its bones breaks and it sinks into the water... *few");
//							ItemStack sBone = new ItemStack(352, 2);
//							ItemStack sBoneMeal = new ItemStack(351, 4, (short) 15);
//							player.getInventory().addItem(sBone);
//							player.getInventory().addItem(sBoneMeal);
//						}
//					}
					
/*
 * 	CATCHING PIGS
 */

//					if (catchChance == 26) 
//					{
//						int bigChance = (int) (Math.random() * 10 + 1);
//						if (bigChance == 1 || bigChance == 2) 
//						{
//							EntityLiving e = null;
//							CraftPlayer craftPlayer = (CraftPlayer) player;
//							CraftWorld craftWorld = (CraftWorld) craftPlayer.getWorld();
//							World world = craftWorld.getHandle();
//							e = new EntityPig(world);
//							e.getBukkitEntity().teleport(player.getLocation());
//							world.a(e);
//							player.sendMessage(ChatColor.GREEN + "Ummm, you saved a pig from drowning...");
//
//						} 
//						else 
//						{
//							player.sendMessage(ChatColor.GREEN + "Oh-no! a poor pig is caught on the line... Unfortunatly it falls back into the water...");
//							ItemStack sPork = new ItemStack(319, 1);
//							player.getInventory().addItem(sPork);
//							player.sendMessage(ChatColor.RED + "Well, atleast you got some pork...");
//						}
//					}
					
/*
* 	CATCHING BOWLS
*/
					if (catchChance == 31)
					{
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

				timer.cancel(); // Terminate the timer thread
			}

		}

	}

}
