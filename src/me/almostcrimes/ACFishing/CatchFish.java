package me.almostcrimes.ACFishing;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class CatchFish implements Listener 
{
	private boolean caughtFish = false;

	public void setCaughtFish(boolean caughtFish) 
	{
		this.caughtFish = caughtFish;
	}

	/*
	 * The following method was written with help from Edward Hand.
	 */

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) 
	{
		if (event.getItem().getItemStack().getTypeId() == 349) 
		{
			caughtFish = true;
			new CatchReset(2); // resets Catch fish value.
		}
	}

	public boolean isCaughtFish() 
	{
		return caughtFish;
	}

	public class CatchReset 
	{
		Timer timer;

		public CatchReset(int seconds) 
		{
			timer = new Timer();
			timer.schedule(new RemindTask(), seconds * 1000);
		}

		// Declaration of the timer and its method.
		class RemindTask extends TimerTask 
		{
			public void run() 
			{
				setCaughtFish(false);
				timer.cancel();
			}
		}
	}
}