/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.igates.listeners;

import com.ptibiscuit.framework.permission.PermissionHandler;
import com.ptibiscuit.igates.Plugin;
import com.ptibiscuit.igates.data.Portal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.isCancelled()) { return; }
		Player p = e.getPlayer();
		Plugin plug = Plugin.instance;
		if (!e.getFrom().getBlock().getLocation().equals(e.getTo().getBlock().getLocation()))
		{
			Portal portal = plug.getPortalByPosition(p.getLocation());
			if (portal != null)
			{
				// Il se trouve effectivement dans un portal !
				PermissionHandler perm = plug.getPermissionHandler();
				// Soit il possède la permission use, soit il possède la permissions spéciale. =)
				if (perm.has(e.getPlayer(), "portal.use", false) || perm.has(e.getPlayer(), "portal.use." + portal.getFillType().getName(), false))
				{
					e.setCancelled(!portal.teleportPlayer(p));
				} else {
					Plugin.instance.sendPreMessage(e.getPlayer(), "cant_do");
					return;
				}
				
			}
		}
	}
}
