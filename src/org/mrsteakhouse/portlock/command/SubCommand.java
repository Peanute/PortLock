package org.mrsteakhouse.portlock.command;

import org.bukkit.entity.Player;

public abstract interface SubCommand
{
  public abstract boolean onCommand(Player player, String[] args);

  public abstract String help(Player player);
  
  public abstract String noPerm();
}