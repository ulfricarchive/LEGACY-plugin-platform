package com.ulfric.plugin.platform.punishment;

import java.util.Objects;

import com.ulfric.commons.spigot.command.Command;
import com.ulfric.commons.spigot.command.Context;
import com.ulfric.commons.spigot.command.argument.Argument;
import com.ulfric.commons.spigot.punishment.Punisher;
import com.ulfric.commons.spigot.punishment.Punishment;
import com.ulfric.commons.spigot.punishment.PunishmentHolder;
import com.ulfric.commons.spigot.punishment.PunishmentService;
import com.ulfric.commons.spigot.punishment.Punishments;
import com.ulfric.commons.spigot.service.ServiceUtils;

public abstract class SkeletalPunishmentCommand implements Command {

	@Argument
	private PunishmentHolder target;

	@Argument(optional = true)
	private String reason;

	@Override
	public void run(Context context)
	{
		PunishmentService service = this.getPunishmentService();
		Punishment punishment = this.createPunishment(context, service);
		service.apply(this.target, punishment);
	}

	public final Punishment createPunishment(Context context, PunishmentService service)
	{
		Punisher punisher = Punishments.getService().getPunisher(context.getSender());
		return Punishment.builder()
				.setPunisher(punisher)
				.setReason(this.reason == null ? service.getDefaultReason() : this.reason)
				.build();
	}

	public PunishmentService getPunishmentService()
	{
		PunishmentType type = this.getClass().getAnnotation(PunishmentType.class);
		Objects.requireNonNull(type, "type");
		return ServiceUtils.getService(type.value());
	}

}