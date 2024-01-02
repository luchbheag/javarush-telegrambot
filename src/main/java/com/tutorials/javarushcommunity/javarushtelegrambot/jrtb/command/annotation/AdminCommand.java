package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.annotation;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.Command;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark if {@link Command} can be viewed only by admins.
 */
@Retention(RUNTIME)
public @interface AdminCommand {
}
