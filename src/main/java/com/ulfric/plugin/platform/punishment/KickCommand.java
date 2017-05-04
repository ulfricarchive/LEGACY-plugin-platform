package com.ulfric.plugin.platform.punishment;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.spigot.command.Permission;
import com.ulfric.commons.spigot.punishment.Kick;

@Name("kick")
@Permission("kick-use")
@PunishmentType(Kick.class)
class KickCommand extends SkeletalPunishmentCommand {

}