package io.github.hotlava03.chatutils.mixin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigString;
import io.github.hotlava03.chatutils.config.ChatUtilsConfig;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.server.network.ServerPlayerEntity;
import io.github.hotlava03.chatutils.util.Counter;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.*;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;


@Mixin(ChatHud.class)
public class ReceiveMessageMixin {
    int pressTime;

     String word;
     Date delayDate = new Date();
     boolean isSent;
     boolean isInitialized = false;
    long delaySet = 1000;
    public void onInitialize() {
        isInitialized = true;
        ClientTickEvents.END_CLIENT_TICK.register(this::onPlayerTick);
    }

    public void onPlayerTick(MinecraftClient client) {
        //if (pe.tickCount % 20 != 0) {
        //    return;
        //}'
        Date now = new Date();
        long ms = (now.getTime()-delayDate.getTime());
        if (ms > delaySet + 500) {
            return;
        }
        if (ms > delaySet) {
            if(!isSent){
                MinecraftClient.getInstance().player.sendChatMessage(word);
                isSent = true;
                return;
            }
        }
    }
    private final Counter counter = new Counter();
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"))
    public void addMessage(Text text, int messageId, CallbackInfo info) throws FileNotFoundException {
        antiSpam:
        if (((ConfigBoolean) ChatUtilsConfig.OPTIONS.get(3)).getBooleanValue()) {
            double prejudice = 0;
            if (counter.lastMessage == null) {
                counter.lastMessage = text;
                counter.spamCounter = 1;
                break antiSpam;
            }
            String toUnscramble = ChatColor.stripColor(text.getString());
            //MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(toUnscramble));
            if(toUnscramble.contains(">") || toUnscramble.contains("<")){

            }else{
            if(toUnscramble.contains("The first person to unscramble ")){
                String[] words = toUnscramble.split("\\s+");
                String wordLocal = words[5];

                ArrayList<String> dictionary = new ArrayList<>(
                        Arrays.asList("hopper", "hero", "slime", "boots", "skyblock", "squid", "tripwire", "armor", "bed", "anaxrocks", "yellow", "lava", "mushroom", "carpet", "water", "dacon", "melon", "emerald", "easy", "milk", "winter", "leggings", "pinguinooo", "respiration", "strength", "villager", "plot", "plots", "sheep", "blaze", "clock", "bedrock", "enderchest", "helmet", "endportal", "dropper", "soup", "green", "compass", "jump", "zeus", "haste", "stonebrick", "enderpearl", "minecart", "hoe", "pickaxe", "leather", "mana", "painting", "lapis", "hard", "apollo", "dye", "concrete", "glass", "aether", "glowstone", "night", "furnace", "vines", "iron", "quartz", "lead", "god", "blue", "helper", "efficiency", "black", "notch", "apple", "summer", "sandstone", "creative", "grass", "master", "beetroot", "purple", "mango", "chicken", "red", "buildteam", "protection", "cow", "enchant", "sugarcane", "scrambler", "star", "crystals", "granite", "elytra", "gold", "insane", "bone", "prison", "beacon", "pie", "wool", "music", "shard", "feather", "vote", "hephaestus", "bookshelf", "asgard", "infinity", "poison", "clans", "repeater", "olympus", "forge", "noteblock", "sword", "cake", "islands", "enderman", "fortune", "andesite", "disc", "firework", "shears", "manacube", "wonderland", "experience", "jackpot", "coal", "staff", "chainmail", "diorite", "fly", "monster", "wheat", "banner", "skeleton", "ocelot", "king", "Minecraft", "saddle", "fragment", "banana", "cubit", "mod", "potato", "legendary", "sharpness", "torch", "boat", "sugar", "barrier", "pegasus", "potion", "dionysus", "lantern", "ghast", "sapling", "chestplate", "princess_esther", "steak", "medium", "artemis", "bread", "hermes", "inksac", "knockback", "haybale", "leaves", "pink", "bumblebee", "slimeball", "rabbit", "champion", "rank", "twiddler", "aldrins", "fish", "parkour", "kingdom", "steinbock", "haunted", "owner", "discord", "chest", "checkpoint", "obsidian", "kilton", "mojang", "cookie"));

                ArrayList<String> permutations = getPermutations(wordLocal);

                for (String permutation : permutations) {
                    if (dictionary.contains(permutation)) {
                        delayDate = new Date();
                        switch(permutation.length()){
                            case 3:
                                delaySet = 900;
                                break;
                            case 5:
                                delaySet = 1000;
                                break;
                            case 6:
                                delaySet = 1200;
                                break;
                            case 7:
                                delaySet = 1500;
                                break;
                            case 8:
                                delaySet = 1800;
                                break;
                            case 9:
                                delaySet = 2000;
                                break;
                            case 10:
                                delaySet = 2300;
                                break;
                            case 11:
                                delaySet = 2500;
                                break;
                            case 12:
                                delaySet = 2700;
                                break;
                            case 13:
                                delaySet = 2900;
                                break;
                            case 14:
                                delaySet = 3100;
                                break;
                        }
                        word = permutation;
                        isSent = false;
                        if (!isInitialized) {
                            onInitialize();
                        }
                        break;
                    }
                }
                //MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(Arrays.toString(tp.toArray())));

            }
            else if(toUnscramble.contains("The first person to type ")){
                String[] words = toUnscramble.split("\\s+");
                delayDate = new Date();
                switch(words[5].length()){
                    case 3:
                        delaySet = 900;
                        break;
                    case 5:
                        delaySet = 1000;
                        break;
                    case 6:
                        delaySet = 1200;
                        break;
                    case 7:
                        delaySet = 1500;
                        break;
                    case 8:
                        delaySet = 1800;
                        break;
                    case 9:
                        delaySet = 2000;
                        break;
                    case 10:
                        delaySet = 2300;
                        break;
                    case 11:
                        delaySet = 2500;
                        break;
                    case 12:
                        delaySet = 2700;
                        break;
                    case 13:
                        delaySet = 2900;
                        break;
                    case 14:
                        delaySet = 3100;
                        break;
                }
                word = words[5];
                isSent = false;
                if (!isInitialized) {
                    onInitialize();
                }
              }
            }
            if (getDifference(text.getString(), counter.lastMessage.getString()) <= prejudice) {
                counter.spamCounter++;
                ((MutableText) text).append(" \u00a78[\u00a7c" + counter.spamCounter + "x\u00a78]");
                try {
                    // FIELD field_2064 visibleMessages Ljava/util/List;
                    Field field = ChatHud.class.getDeclaredField("field_2064");
                    field.setAccessible(true);
                    List<?> lines = (List<?>) field.get(MinecraftClient.getInstance().inGameHud.getChatHud());
                    lines.remove(0);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                ++messageId;
            } else {
                counter.lastMessage = text;
                counter.spamCounter = 1;
            }
        }

        String tooltip;
        String toCopy = text.getString();
        if (!((ConfigBoolean) ChatUtilsConfig.OPTIONS.get(2)).getBooleanValue()) {
            toCopy = ChatColor.stripColor(toCopy);
        }


        if (((ConfigBoolean) ChatUtilsConfig.OPTIONS.get(1)).getBooleanValue()) {
            tooltip = ChatColor.translateAlternateColorCodes('&',
                    ((ConfigString) ChatUtilsConfig.OPTIONS.get(0)).getStringValue() + "\n\n&9Preview:\n&f" +
                            toCopy);
        } else {
            tooltip = ChatColor.translateAlternateColorCodes('&',
                    ((ConfigString) ChatUtilsConfig.OPTIONS.get(0)).getStringValue());
        }

        Style style = text.getStyle()
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chatmacros " + toCopy));
        if (((ConfigBoolean) ChatUtilsConfig.OPTIONS.get(4)).getBooleanValue()) {
            style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new LiteralText(tooltip)));
        }

        if (text.getStyle().getClickEvent() == null && ((ConfigBoolean) ChatUtilsConfig.OPTIONS.get(5)).getBooleanValue()) {
            ((MutableText) text).setStyle(style);
        }
    }

    /*
     * This method does not belong to me.
     * Original source:
     * https://github.com/killjoy1221/TabbyChat-2/blob/master/src/main/java/mnm/mods/tabbychat/extra/ChatAddonAntiSpam.java
     */
    private static ArrayList<String> getPermutations(String word) {
        ArrayList<String> permutations = new ArrayList<>();
        if (word.length() == 0) {
            permutations.add("");
            return permutations;
        }

        char first = word.charAt(0);
        String remainder = word.substring(1);
        ArrayList<String> words = getPermutations(remainder);
        for (String word1 : words) {
            for (int j = 0; j <= word1.length(); j++) {
                String s = insertCharAt(word1, first, j);
                permutations.add(s);
            }
        }

        return permutations;
    }

    private static String insertCharAt(String word, char c, int i) {
        String start = word.substring(0, i);
        String end = word.substring(i);
        return start + c + end;
    }
    private double getDifference(String s1, String s2) {
        double avgLen = (s1.length() + s2.length()) / 2D;
        if (avgLen == 0) {
            return 0;
        }
        return StringUtils.getLevenshteinDistance(s1.toLowerCase(), s2.toLowerCase()) / avgLen;
    }
}
