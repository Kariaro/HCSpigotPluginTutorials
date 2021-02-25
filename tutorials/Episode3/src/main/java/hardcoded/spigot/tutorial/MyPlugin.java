package hardcoded.spigot.tutorial;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(command.getName().equalsIgnoreCase("delaysay")) {
			if(args.length < 2) return Arrays.asList("<delay in ticks>");
			return Collections.emptyList();
		} else if(command.getName().equalsIgnoreCase("myconfig")) {
			if(args.length < 2) {
				return Arrays.asList("set", "get", "help");
			} else {
				if(args[0].equals("set")) {
					if(args.length < 3) return Arrays.asList("<key>");
					if(args.length < 4) return Arrays.asList("<value>");
				} else if(args[0].equals("get")) {
					if(args.length < 3) return Arrays.asList("<key>");
				} else {
					return Collections.emptyList();
				}
			}
		}
		
		return super.onTabComplete(sender, command, alias, args);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(command.getName().equalsIgnoreCase("delaysay")) {
				if(args.length < 2) {
					player.sendMessage("Not enough arguments. Usage /delaysay <delay> \"<message>\"");
					return true;
				}
				
				int delay = 0;
				try {
					delay = Integer.valueOf(args[0]);
				} catch(NumberFormatException e) {
					player.sendMessage("Invalid delay. Usage /delaysay <delay> \"<message>\"");
					return true;
				}
				
				String message = "";
				for(int i = 1; i < args.length; i++) message += args[i] + (i < args.length - 1 ? " ":"");
				
				if(message.startsWith("\"")) {
					if(message.length() < 2) {
						message = "";
					} else {
						message = message.substring(0, message.length() - 1).substring(1);
					}
				}
				
				final String msg = message;
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
					player.getServer().broadcastMessage(msg);
				}, delay);
				
				return true;
			} else if(command.getName().equalsIgnoreCase("colorsay")) {
				if(args.length == 0) {
//					player.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
//					StringBuilder sb = new StringBuilder();
//					int j = 0;
//					for(ChatColor color : ChatColor.values()) {
//						if(color == ChatColor.RESET) continue;
//						sb.append("§r&").append(color.getChar()).append(" §").append(color.getChar()).append("text§r ");
//						if((++j > 0) && (j % 6 == 0)) sb.append('\n');
//					}
//					player.sendMessage(sb.toString().trim());
//					player.sendMessage("&r reset the current formatting");
//					player.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
					
					player.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
					String msg = "";
					int j = 0;
					for(ChatColor color : ChatColor.values()) {
						if(color == ChatColor.RESET) continue;
						msg += "§r&" + color.getChar() + " §" + color.getChar() + "text§r ";
						if((++j > 0) && (j % 6 == 0)) msg += '\n';
					}
					player.sendMessage(msg.trim());
					player.sendMessage("&r reset the current formatting");
					player.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
				} else {
					String msg = "";
					for(int i = 0; i < args.length; i++) msg += args[i] + (i < args.length - 1 ? " ":"");
					
					msg = msg.replace('&', '§');
					Bukkit.broadcastMessage("<" + player.getName() + "> " + msg);
				}
				
				return true;
			} else if(command.getName().equalsIgnoreCase("myconfig")) {
				String error = "";
				
				if(args.length > 0) {
					if(args[0].equals("get")) {
						if(args.length < 2) {
							error = "Missing <key> in command /myconfig get <key>";
						} else {
							Object value = this.getConfig().get(args[1]);
							player.sendMessage(ChatColor.GOLD + args[1] + ": " + ChatColor.RESET + value);
							return true;
						}
					} else if(args[0].equals("set")) {
						if(args.length < 2) {
							error = "Missing <key> in command /myconfig set <key> <value>";
						} else if(args.length < 3) {
							error = "Missing <value> in command /myconfig set <key> <value>";
						} else {
							getConfig().set(args[1], args[2]);
							saveConfig();
							return true;
						}
					}
				}
				
				if(!error.isEmpty()) {
					player.sendMessage(ChatColor.RED + error);
				} else {
					player.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
					player.sendMessage(ChatColor.GOLD + "/myconfig set <key> <value>: " + ChatColor.RESET + "Change a value in the config.");
					player.sendMessage(ChatColor.GOLD + "/myconfig get <key>: " + ChatColor.RESET + "Get a value from the config.");
					player.sendMessage(ChatColor.GOLD + "/myconfig help: " + ChatColor.RESET + "Shows you this help message.");
				}
				
				return true;
			}
		}
		
		return super.onCommand(sender, command, label, args);
	}
}
