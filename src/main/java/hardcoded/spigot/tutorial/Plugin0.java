package hardcoded.spigot.tutorial;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin0 extends JavaPlugin {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("explode")) {
			Player player = (Player)sender;
			
			TNTPrimed tnt = (TNTPrimed)player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
			tnt.setFuseTicks(0);
			return true;
		}
		
		return super.onCommand(sender, command, label, args);
	}
}
