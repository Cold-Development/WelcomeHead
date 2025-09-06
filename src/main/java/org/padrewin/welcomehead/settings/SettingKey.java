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

    // --------------------------------------------------------------
    //  locale
    // --------------------------------------------------------------
    public static final ColdSetting<String> LOCALE = create(
            "locale",
            STRING,
            "en_US",
            "The locale to use in the /locale folder"
    );

    // --------------------------------------------------------------
    //  base-command-redirect
    // --------------------------------------------------------------
    public static final ColdSetting<String> BASE_COMMAND_REDIRECT = create(
            "base-command-redirect",
            STRING,
            "",
            "Which command should we redirect to when using '/welcomehead' with no subcommand specified?",
            "You can use a value here such as 'version' to show the output of '/welcomehead version'",
            "If you have any aliases defined, do not use them here",
            "If left blank, the default behavior of showing '/welcomehead version' with bypassed permissions will be used"
    );

    // --------------------------------------------------------------
    //  Spaces (clean chat) & Timer
    // --------------------------------------------------------------
    public static final ColdSetting<Integer> SPACES_TOP = create(
            "Spaces-Top",
            INTEGER,
            1,
            "Spaces = clean the chat before giving the text (for player only)"
    );
    public static final ColdSetting<Integer> SPACES_BOT = create(
            "Spaces-Bot",
            INTEGER,
            1,
            "Spaces = clean the chat after giving the text (for player only)"
    );
    public static final ColdSetting<Integer> TIMER = create(
            "Timer",
            INTEGER,
            5,
            "Time when you want the message to appear (in seconds)"
    );

    // --------------------------------------------------------------
    //  Players-FirstJoin
    // --------------------------------------------------------------
    public static final ColdSetting<CommentedConfigurationSection> PLAYERS_FIRSTJOIN_SECTION = create(
            "Players-FirstJoin",
            "First Join Player Settings",
            "", // Blank line before section
            ""  // Double blank line for more spacing
    );

    public static final ColdSetting<Boolean> PLAYERS_FIRSTJOIN_ENABLE = create(
            "Players-FirstJoin.enable",
            BOOLEAN,
            true,
            "Face the new players! Enable the head message for first join."
    );

    // showHead: whether to display the player's head for first join
    public static final ColdSetting<Boolean> PLAYERS_FIRSTJOIN_SHOWHEAD = create(
            "Players-FirstJoin.showHead",
            BOOLEAN,
            true, // Adjust if you want true by default
            "If you need the player's head, set this to true."
    );

    // Lines for the player's head-text
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_1 = create(
            "Players-FirstJoin.head-text.1",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_2 = create(
            "Players-FirstJoin.head-text.2",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_3 = create(
            "Players-FirstJoin.head-text.3",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_4 = create(
            "Players-FirstJoin.head-text.4",
            STRING,
            "&#6E00A5W&#7B17B4e&#882EC3l&#9545D2c&#A15CE1o&#AE73F0m&#BB8AFFe &#A53CFF%player_name%&f!",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_5 = create(
            "Players-FirstJoin.head-text.5",
            STRING,
            "&cYou can read the rules on our website!",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_6 = create(
            "Players-FirstJoin.head-text.6",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_7 = create(
            "Players-FirstJoin.head-text.7",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_FIRSTJOIN_HEAD_TEXT_8 = create(
            "Players-FirstJoin.head-text.8",
            STRING,
            "",
            ""
    );

    public static final ColdSetting<Boolean> PLAYERS_FIRSTJOIN_CENTER = create(
            "Players-FirstJoin.center",
            BOOLEAN,
            true,
            "This will center the text based on the chat size."
    );

    // --------------------------------------------------------------
    //  SoundA (first join sound)
    // --------------------------------------------------------------
    public static final ColdSetting<CommentedConfigurationSection> SOUNDA_SECTION = create(
            "SoundA",
            "First Join Sound Settings",
            "", // Blank line before section
            ""  // Double blank line for more spacing
    );

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
    public static final ColdSetting<Double> SOUND_A_VOLUME = create(
            "SoundA.volume",
            DOUBLE,
            1.0,
            "Volume for first join sound (Values: 0.0 -> 1.0)"
    );
    public static final ColdSetting<Double> SOUND_A_PITCH = create(
            "SoundA.pitch",
            DOUBLE,
            1.0,
            "Pitch for first join sound (Values: 0.0 -> 1.0)"
    );

    // --------------------------------------------------------------
    //  Firework (first join fireworks)
    // --------------------------------------------------------------
    public static final ColdSetting<CommentedConfigurationSection> FIREWORK_SECTION = create(
            "Firework",
            "Firework Settings",
            "", // Blank line before section
            ""  // Double blank line for more spacing
    );

    public static final ColdSetting<Integer> FIREWORK_AMOUNT = create(
            "Firework.amount",
            INTEGER,
            5,
            "Send fireworks when they spawn for the first time? Set to 0 to disable this."
    );

    // --------------------------------------------------------------
    //  Players-Back (returning players)
    // --------------------------------------------------------------
    public static final ColdSetting<CommentedConfigurationSection> PLAYERS_BACK_SECTION = create(
            "Players-Back",
            "Returning Player Settings",
            "", // Blank line before section
            ""  // Double blank line for more spacing
    );

    public static final ColdSetting<Boolean> PLAYERS_BACK_ENABLE = create(
            "Players-Back.enable",
            BOOLEAN,
            true,
            "A warm welcome for the old players! Enable the head message for returning players."
    );

    // showHead: whether to display the player's head for returning players
    public static final ColdSetting<Boolean> PLAYERS_BACK_SHOWHEAD = create(
            "Players-Back.showHead",
            BOOLEAN,
            true,
            "If you need the player's head, set this to true."
    );

    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_1 = create(
            "Players-Back.head-text.1",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_2 = create(
            "Players-Back.head-text.2",
            STRING,
            "&#6E00A5W&#750DADe&#7C19B5l&#8326BEc&#8A32C6o&#913FCEm&#984BD6e &#A664E6b&#AD71EFa&#B47DF7c&#BB8AFFk &#FF0000%player_name%&#984BD6!",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_3 = create(
            "Players-Back.head-text.3",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_4 = create(
            "Players-Back.head-text.4",
            STRING,
            "&#FF0000https://github.com/Cold-Development",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_5 = create(
            "Players-Back.head-text.5",
            STRING,
            "&#FF0000&lhttps://store.mc-1st.net",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_6 = create(
            "Players-Back.head-text.6",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_7 = create(
            "Players-Back.head-text.7",
            STRING,
            "",
            ""
    );
    public static final ColdSetting<String> PLAYERS_BACK_HEAD_TEXT_8 = create(
            "Players-Back.head-text.8",
            STRING,
            "&8(( &bCold Development &8))",
            ""
    );
    public static final ColdSetting<Boolean> PLAYERS_BACK_CENTER = create(
            "Players-Back.center",
            BOOLEAN,
            true,
            "This will center the text based on the chat size."
    );

    // --------------------------------------------------------------
    //  SoundB (returning join sound)
    // --------------------------------------------------------------
    public static final ColdSetting<CommentedConfigurationSection> SOUNDB_SECTION = create(
            "SoundB",
            "Returning Player Sound Settings",
            "", // Blank line before section
            ""  // Double blank line for more spacing
    );

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
    public static final ColdSetting<Double> SOUND_B_VOLUME = create(
            "SoundB.volume",
            DOUBLE,
            1.0,
            "Volume for returning join sound (Values: 0.0 -> 1.0)"
    );
    public static final ColdSetting<Double> SOUND_B_PITCH = create(
            "SoundB.pitch",
            DOUBLE,
            1.0,
            "Pitch for returning join sound (Values: 0.0 -> 1.0)"
    );

    // ----------------------------------------------------------------------
    //  Helper method to create a setting
    // ----------------------------------------------------------------------
    private static <T> ColdSetting<T> create(String key,
                                             ColdSettingSerializer<T> serializer,
                                             T defaultValue,
                                             String... comments) {
        ColdSetting<T> setting = ColdSetting.backed(
                WelcomeHead.getInstance(),
                key,
                serializer,
                defaultValue,
                comments
        );
        KEYS.add(setting);
        return setting;
    }

    private static ColdSetting<CommentedConfigurationSection> create(String key, String... comments) {
        ColdSetting<CommentedConfigurationSection> setting =
                ColdSetting.backedSection(WelcomeHead.getInstance(), key, comments);
        KEYS.add(setting);
        return setting;
    }

    public static List<ColdSetting<?>> getKeys() {
        return Collections.unmodifiableList(KEYS);
    }

    private SettingKey() {}
}