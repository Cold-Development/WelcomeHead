package org.padrewin.welcomehead.settings;

import dev.padrewin.colddev.config.CommentedConfigurationSection;
import dev.padrewin.colddev.config.ColdSetting;
import dev.padrewin.colddev.config.ColdSettingSerializer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.padrewin.welcomehead.WelcomeHead;
import static dev.padrewin.colddev.config.ColdSettingSerializers.*;

public class SettingKey {

    private static final List<ColdSetting<?>> KEYS = new ArrayList<>();

    // Redirect base command: Which command should be used when '/welcomehead' is executed without subcommands.
    public static final ColdSetting<String> BASE_COMMAND_REDIRECT = create(
            "base-command-redirect",
            STRING,
            "",
            "Which command should we redirect to when using '/welcomehead' with no subcommand specified?",
            "You can use a value here such as 'version' to show the output of '/welcomehead version'",
            "If you have any aliases defined, do not use them here",
            "If left blank, the default behavior of showing '/welcomehead version' with bypassed permissions will be used"
    );

    // Clean the chat before giving the text (for player only)
    public static final ColdSetting<Integer> SPACES_TOP = create(
            "Spaces-Top",
            INTEGER,
            0,
            "Spaces = clean the chat before giving the text (for player only)"
    );
    // Clean the chat after giving the text (for player only)
    public static final ColdSetting<Integer> SPACES_BOT = create(
            "Spaces-Bot",
            INTEGER,
            0,
            "Spaces = clean the chat after giving the text (for player only)"
    );

    // Time when you want the message to appear (in seconds)
    public static final ColdSetting<Integer> TIMER = create(
            "Timer",
            INTEGER,
            5,
            "Time when you want the message to appear (in seconds)"
    );

    // ----------------- First time join -----------------------------
    // Face the new players! Change the texts made available to you.
    public static final ColdSetting<Boolean> PLAYERS_FIRSTJOIN_ENABLE = create(
            "Players-FirstJoin.enable",
            BOOLEAN,
            true,
            "Face the new players! Enable the head message for first join."
    );
    // Head-text for first join (if the head is enabled)
    // If you need to have the head of the player put "enable: true"
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_1 = create(
            "Players-FirstJoin.head-text.1",
            STRING,
            "",
            "Line 1 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_2 = create(
            "Players-FirstJoin.head-text.2",
            STRING,
            "",
            "Line 2 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_3 = create(
            "Players-FirstJoin.head-text.3",
            STRING,
            "",
            "Line 3 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_4 = create(
            "Players-FirstJoin.head-text.4",
            STRING,
            "        &#6E00A5W&#7B17B4e&#882EC3l&#9545D2c&#A15CE1o&#AE73F0m&#BB8AFFe &#A53CFF%player_name%&f!",
            "Line 4 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_5 = create(
            "Players-FirstJoin.head-text.5",
            STRING,
            "        You can read the rules on our website!",
            "Line 5 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_6 = create(
            "Players-FirstJoin.head-text.6",
            STRING,
            "",
            "Line 6 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_7 = create(
            "Players-FirstJoin.head-text.7",
            STRING,
            "",
            "Line 7 of the head message for first join (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_8 = create(
            "Players-FirstJoin.head-text.8",
            STRING,
            "",
            "Line 8 of the head message for first join (if head is enabled)"
    );
    // If you don't want the head, set "enable" to false; this option enables centered text for first join message.
    public static final ColdSetting<Boolean> PLAYERS_FIRSTJOIN_CENTER = create(
            "Players-FirstJoin.center",
            BOOLEAN,
            true,
            "If you don't want the head, set 'enable' to false. Enable centered text for first join message."
    );
    // Fallback message for first join if head is disabled. Set as many lines as you need!
    public static final ColdSetting<List<String>> PLAYERS_FIRSTJOIN_NO_HEAD_TEXT = create(
            "Players-FirstJoin.no-head-text",
            STRING_LIST,
            Arrays.asList("Line1", "Line2", "Line3", "Line4", "Line5"),
            "Fallback message for first join if head is disabled. Set as many lines as you need!"
    );

    // Commands executed on first join. Put your commands without '/'.
    // Default is a single dash indicating no command.
    public static final ColdSetting<List<String>> COMMANDS_FIRST = create(
            "Commands-First",
            STRING_LIST,
            Arrays.asList("-"),
            "Commands executed on first join. Put your commands without '/'"
    );

    // Need a sound when the player joins for the first time?
    // Sounds: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html
    public static final ColdSetting<Boolean> SOUND_A_ENABLE = create(
            "SoundA.enable",
            BOOLEAN,
            true,
            "Need a sound when the player joins for the first time? Enable this to play sound."
    );
    public static final ColdSetting<String> SOUND_A_SOUND = create(
            "SoundA.sound",
            STRING,
            "ENTITY_GOAT_SCREAMING_DEATH",
            "Sound played on first join. See: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html"
    );
    // Values: 0.0 -> 1.0
    public static final ColdSetting<Double> SOUND_A_VOLUME = create(
            "SoundA.volume",
            DOUBLE,
            1.0,
            "Volume for first join sound (Values: 0.0 -> 1.0)"
    );
    // Values: 0.0 -> 1.0
    public static final ColdSetting<Double> SOUND_A_PITCH = create(
            "SoundA.pitch",
            DOUBLE,
            1.0,
            "Pitch for first join sound (Values: 0.0 -> 1.0)"
    );
    // Send fireworks when they spawn for the first time? Set to 0 to disable this.
    public static final ColdSetting<Integer> FIREWORK_AMOUNT = create(
            "Firework.amount",
            INTEGER,
            5,
            "Send fireworks when they spawn for the first time? Set to 0 to disable this."
    );

    // ----------------- Basic joining (every time user joins the server) -----------------------------
    // Face the returning players! Change the texts made available to you.
    public static final ColdSetting<Boolean> PLAYERS_BACK_ENABLE = create(
            "Players-Back.enable",
            BOOLEAN,
            true,
            "Face the returning players! Enable the head message for returning players."
    );

    // Head-text for returning players (if the head is enabled)
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_1 = create(
            "Players-Back.head-text.1",
            STRING,
            "",
            "Line 1 of the head message for returning players (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_2 = create(
            "Players-Back.head-text.2",
            STRING,
            "        &#6E00A5W&#750DADe&#7C19B5l&#8326BEc&#8A32C6o&#913FCEm&#984BD6e &#A664E6b&#AD71EFa&#B47DF7c&#BB8AFFk &#FF0000%player_name%&#984BD6!",
            "Line 2 of the head message for returning players (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_3 = create(
            "Players-Back.head-text.3",
            STRING,
            "",
            "Line 3 of the head message for returning players (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_4 = create(
            "Players-Back.head-text.4",
            STRING,
            "&#FF0000https://github.com/Cold-Development",
            "Line 4 of the head message for returning players (if head is enabled). Default (commented): '        &#6E00A5W&#7B17B4e&#882EC3l&#9545D2c&#A15CE1o&#AE73F0m&#BB8AFFe &#A53CFF%player_name%&#FFFFFF!'"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_5 = create(
            "Players-Back.head-text.5",
            STRING,
            "    &#FF0000&lhttps://store.mc-1st.net",
            "Line 5 of the head message for returning players (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_6 = create(
            "Players-Back.head-text.6",
            STRING,
            "",
            "Line 6 of the head message for returning players (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_7 = create(
            "Players-Back.head-text.7",
            STRING,
            "",
            "Line 7 of the head message for returning players (if head is enabled)"
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_8 = create(
            "Players-Back.head-text.8",
            STRING,
            "        &8(( &bCold Development &8))",
            "Line 8 of the head message for returning players (if head is enabled)"
    );

    // If you don't want the head, set "enable" to false; this option enables centered text for returning players' message.
    public static final ColdSetting<Boolean> PLAYERS_BACK_CENTER = create(
            "Players-Back.center",
            BOOLEAN,
            true,
            "If you don't want the head, set 'enable' to false. Enable centered text for returning players' message."
    );
    // Fallback message for returning players if head is disabled. Set as many lines as you need!
    public static final ColdSetting<List<String>> PLAYERS_BACK_NO_HEAD_TEXT = create(
            "Players-Back.no-head-text",
            STRING_LIST,
            Arrays.asList(
                    "----------------------------------",
                    " ",
                    "&fWelcome &#A53CFF%player_name%&f!",
                    " ",
                    "----------------------------------"
            ),
            "Fallback message for returning players if head is disabled. Set as many lines as you need!"
    );

    // Commands executed on returning join. Put your commands without '/'.
    // Default is a single dash indicating no command.
    public static final ColdSetting<List<String>> COMMANDS_BACK = create(
            "Commands-Back",
            STRING_LIST,
            Arrays.asList("-"),
            "Commands executed on returning join. Put your commands without '/'"
    );
    // Need a sound when the player joins again?
    // Sounds: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html
    public static final ColdSetting<Boolean> SOUND_B_ENABLE = create(
            "SoundB.enable",
            BOOLEAN,
            true,
            "Need a sound when the player joins again? Enable this to play sound."
    );
    public static final ColdSetting<String> SOUND_B_SOUND = create(
            "SoundB.sound",
            STRING,
            "ENTITY_VILLAGER_CELEBRATE",
            "Sound played on returning join. See: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html"
    );
    // Values: 0.0 -> 1.0
    public static final ColdSetting<Double> SOUND_B_VOLUME = create(
            "SoundB.volume",
            DOUBLE,
            1.0,
            "Volume for returning join sound (Values: 0.0 -> 1.0)"
    );
    // Values: 0.0 -> 1.0
    public static final ColdSetting<Double> SOUND_B_PITCH = create(
            "SoundB.pitch",
            DOUBLE,
            1.0,
            "Pitch for returning join sound (Values: 0.0 -> 1.0)"
    );

    private static <T> ColdSetting<T> create(String key, ColdSettingSerializer<T> serializer, T defaultValue, String... comments) {
        ColdSetting<T> setting = ColdSetting.backed(WelcomeHead.getInstance(), key, serializer, defaultValue, comments);
        KEYS.add(setting);
        return setting;
    }

    // Support for commented sections, if needed
    private static ColdSetting<CommentedConfigurationSection> create(String key, String... comments) {
        ColdSetting<CommentedConfigurationSection> setting = ColdSetting.backedSection(WelcomeHead.getInstance(), key, comments);
        KEYS.add(setting);
        return setting;
    }

    public static List<ColdSetting<?>> getKeys() {
        return Collections.unmodifiableList(KEYS);
    }

    private SettingKey() {}
}
