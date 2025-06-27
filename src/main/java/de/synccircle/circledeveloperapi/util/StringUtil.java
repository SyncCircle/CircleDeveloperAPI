package de.synccircle.circledeveloperapi.util;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static Color HexToColor(String hex) {
        hex = hex.replace("#", "");
        return switch (hex.length()) {
            case 6 -> new Color(
                    Integer.valueOf(hex.substring(0, 2), 16),
                    Integer.valueOf(hex.substring(2, 4), 16),
                    Integer.valueOf(hex.substring(4, 6), 16));
            case 8 -> new Color(
                    Integer.valueOf(hex.substring(0, 2), 16),
                    Integer.valueOf(hex.substring(2, 4), 16),
                    Integer.valueOf(hex.substring(4, 6), 16),
                    Integer.valueOf(hex.substring(6, 8), 16));
            default -> null;
        };
    }

    private static final Pattern PATTERN_HEX = Pattern.compile("#[A-Fa-f0-9]{6}");
    private static final Pattern PATTERN_ETHEX = Pattern.compile(
            "&(#[0-9a-fA-F]{6}|aqua|black|blue|dark_(aqua|blue|gray|green|purple|red)|gray|gold|green|light_purple|red|white|yellow)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern PATTERN_STRIP = Pattern.compile(
            "(?i)" + '§' + "[0-9A-FK-ORX]",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern LEGACY_PATTERN = Pattern.compile("(?i)&([0-9A-FK-OR])");

    private static final Map<Character, TextColor> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put('0', NamedTextColor.BLACK);
        COLOR_MAP.put('1', NamedTextColor.DARK_BLUE);
        COLOR_MAP.put('2', NamedTextColor.DARK_GREEN);
        COLOR_MAP.put('3', NamedTextColor.DARK_AQUA);
        COLOR_MAP.put('4', NamedTextColor.DARK_RED);
        COLOR_MAP.put('5', NamedTextColor.DARK_PURPLE);
        COLOR_MAP.put('6', NamedTextColor.GOLD);
        COLOR_MAP.put('7', NamedTextColor.GRAY);
        COLOR_MAP.put('8', NamedTextColor.DARK_GRAY);
        COLOR_MAP.put('9', NamedTextColor.BLUE);
        COLOR_MAP.put('a', NamedTextColor.GREEN);
        COLOR_MAP.put('b', NamedTextColor.AQUA);
        COLOR_MAP.put('c', NamedTextColor.RED);
        COLOR_MAP.put('d', NamedTextColor.LIGHT_PURPLE);
        COLOR_MAP.put('e', NamedTextColor.YELLOW);
        COLOR_MAP.put('f', NamedTextColor.WHITE);
    }

    public static String colorizeString(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        final Matcher matcher = PATTERN_HEX.matcher(text);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        text = matcher.appendTail(buffer).toString();

        final Matcher matcher2 = PATTERN_ETHEX.matcher(text);
        while (matcher2.find()) {
            try {
                final ChatColor chatColor = ChatColor.of(matcher2.group(1));

                if (chatColor != null) {
                    text = StringUtils.replace(text, matcher2.group(), chatColor.toString());
                }
            } catch (IllegalArgumentException ignored) {
            }
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String convertLegacyToMiniMessage(String input) {
        Matcher matcher = LEGACY_PATTERN.matcher(input);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            char code = matcher.group(1).charAt(0);
            TextColor color = COLOR_MAP.get(code);
            if (color != null) {
                matcher.appendReplacement(sb, "");
                sb.append("<").append(color).append(">");
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    public static String uncolorizeString(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return PATTERN_STRIP.matcher(text).replaceAll("");
    }

    public static String replacePlaceholder(String input, Map<String, String> placeholders) {
        for (String placeholder : placeholders.keySet()) {
            // replace placeholder with value
            input = input.replace(placeholder, placeholders.get(placeholder));
        }
        return input;
    }
}
