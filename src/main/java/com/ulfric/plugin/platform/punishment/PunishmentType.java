package com.ulfric.plugin.platform.punishment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ulfric.commons.spigot.punishment.PunishmentService;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface PunishmentType {

	Class<? extends PunishmentService> value();

}