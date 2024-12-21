package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import net.minecraft.world.explosion.Explosion;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.JTextComponent;
import java.awt.event.KeyEvent;

public class TemplateMod implements ModInitializer {
	public static final String MOD_ID = "template-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ServerEntityEvents.ENTITY_LOAD.register((entity, serverWorld) -> {
			entity.setNoGravity(false);
			//entity.setFireTicks(20*10);
			//newEntityJoinedWorldInfo();

			


			if (entity instanceof PigEntity){
				//entity.setFireTicks(20*5);

			}
			/*
			if (entity instanceof VillagerEntity){



				entity.onStruckByLightning(serverWorld, new LightningEntity());
			}

			 */



			/*
			if (entity instanceof VillagerEntity villager) {
				LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(serverWorld);
				lightning.refreshPositionAfterTeleport(villager.getX(), villager.getY(), villager.getZ());
				serverWorld.spawnEntity(lightning);

				villager.onStruckByLightning(serverWorld, lightning); // Effekt auslösen
				villager.extinguish();
			}

			 */






			ServerLivingEntityEvents.ALLOW_DAMAGE.register((livingEntity, damageSource, damageAmount) -> {
				// Überprüfen, ob der Schaden durch Sturz verursacht wurde und das Entity ein Spieler ist
				if (damageSource.isOf(DamageTypes.FALL) && livingEntity instanceof ServerPlayerEntity player) {
					// Spieler sofort in den Spectator-Modus versetzen
					if (!player.isSpectator()) { // Sicherstellen, dass dies nur einmal passiert
						player.changeGameMode(GameMode.SPECTATOR);
						//player.sendMessage(Text.of("Du wurdest durch Fallschaden in den Spectator-Modus versetzt!"), false);
						// Nachricht mit formatiertem Text
						Text message = Text.literal(player.getName().getString())
								.styled(style -> style.withColor(0x00FF00)) // Spielername in Grün
								.append(" wurde durch Fallschaden in den ")
								.append(Text.literal("Spectator-Modus").styled(style -> style.withColor(0xFF0000))) // Modus in Rot
								.append(" versetzt!");

						// Nachricht senden
						player.sendMessage(message, false);

						//fertig
					}

					// Den Fallschaden vollständig blockieren
					return false;
				}

				// Andere Schadensarten zulassen
				return true;
			});

		});

		LOGGER.info("Hello Fabric world!");

	}

	private static void newEntityJoinedWorldInfo(){
		System.out.println("A new entity has joined our world");
	}


}