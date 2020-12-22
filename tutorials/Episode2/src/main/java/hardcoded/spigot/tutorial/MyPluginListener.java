package hardcoded.spigot.tutorial;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MyPluginListener implements Listener {
	private MyPlugin plugin;
	private Random random = new Random();
	
	public MyPluginListener(MyPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage("Welcome to my server " + ChatColor.RED + player.getName() + ChatColor.RESET + "!");
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		ItemStack item = player.getInventory().getItemInMainHand();
		if((item != null) && (item.getType() == Material.IRON_AXE)) {
			if(block.getType() == Material.SANDSTONE) {
				event.setDropItems(false);
				
				int amount = 2 + random.nextInt(3);
				player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.SAND, amount));
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		ItemStack item = event.getItem();
		if((item != null) && (item.getType() == Material.STICK)) {
			if(event.getAction() == Action.LEFT_CLICK_AIR) {
				Location eye = player.getEyeLocation();
				Vector eyeDir = eye.getDirection();
				
				TNTPrimed primed = (TNTPrimed)player.getWorld().spawnEntity(eye, EntityType.PRIMED_TNT);
				primed.setFuseTicks(20);
				primed.setVelocity(eyeDir);
			}
		}
	}
}
