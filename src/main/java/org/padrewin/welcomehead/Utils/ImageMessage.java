package org.padrewin.welcomehead.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class ImageMessage {
    private static final char TRANSPARENT_CHAR = ' ';

    private final Color[] colors = new Color[] {
            new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170),
            new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 255, 85), new Color(170, 170, 170),
            new Color(85, 85, 85), new Color(85, 85, 255), new Color(85, 255, 85), new Color(85, 255, 255),
            new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 170, 0), new Color(255, 255, 255)
    };

    private String[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        ChatColor[][] chatColors = toChatColorArray(image, height);
        this.lines = toImgMessage(chatColors, imgChar);
    }

    public ImageMessage(ChatColor[][] chatColors, char imgChar) {
        this.lines = toImgMessage(chatColors, imgChar);
    }

    public ImageMessage(String... imgLines) {
        this.lines = imgLines;
    }

    public ImageMessage appendText(String... text) {
        for (int y = 0; y < this.lines.length; y++) {
            if (text.length > y) {
                this.lines[y] = this.lines[y] + " " + text[y];
            }
        }
        return this;
    }

    public ImageMessage appendCenteredText(String... text) {
        for (int y = 0; y < this.lines.length; y++) {
            if (text.length > y) {
                int remaining = 65 - this.lines[y].length();
                if (remaining > 0) {
                    this.lines[y] = this.lines[y] + center(text[y], remaining);
                }
            } else {
                return this;
            }
        }
        return this;
    }

    private ChatColor[][] toChatColorArray(BufferedImage image, int targetHeight) {
        int srcW = image.getWidth();
        int srcH = image.getHeight();
        if (srcW <= 0 || srcH <= 0) {
            throw new IllegalArgumentException("Invalid source image dimensions");
        }

        // calcule pe double (evită 0 sau Inf)
        double ratio = (double) srcH / (double) srcW; // H/W
        int targetWidth = (int) Math.round(targetHeight / ratio);

        // limite utile pentru randarea în chat (ex: max 10 coloane)
        targetWidth = Math.max(1, Math.min(10, targetWidth));
        targetHeight = Math.max(1, targetHeight);

        BufferedImage resized = resizeImage(image, targetWidth, targetHeight);

        ChatColor[][] chatImg = new ChatColor[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                int argb = resized.getRGB(x, y);
                Color c = new Color(argb, true);

                // transparență -> caracter gol
                if (c.getAlpha() < 128) {
                    chatImg[x][y] = null;
                } else {
                    // ChatColor.of(Color) (Bungee) – păstrează nuanța exactă
                    chatImg[x][y] = ChatColor.of(c);
                }
            }
        }
        return chatImg;
    }

    private String[] toImgMessage(ChatColor[][] colors, char imgchar) {
        String[] lines = new String[colors[0].length];
        for (int y = 0; y < colors[0].length; y++) {
            StringBuilder lineBuilder = new StringBuilder();
            for (int x = 0; x < colors.length; x++) {
                ChatColor color = colors[x][y];
                if (color != null) {
                    lineBuilder.append(color).append(imgchar);
                } else {
                    lineBuilder.append(TRANSPARENT_CHAR);
                }
            }
            lines[y] = lineBuilder.toString() + ChatColor.RESET;
        }
        return lines;
    }

    // Variantă robustă: Graphics2D (fără AffineTransformOp care crapă la scale 0)
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        int tw = Math.max(1, width);
        int th = Math.max(1, height);

        BufferedImage dst = new BufferedImage(tw, th, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dst.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(originalImage, 0, 0, tw, th, null);
        g.dispose();
        return dst;
    }

    // Rămân aici în caz că vrei fallback pe paleta clasică de culori (momentan nu se folosește)
    private double getDistance(Color c1, Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2.0D;
        double r = (c1.getRed() - c2.getRed());
        double g = (c1.getGreen() - c2.getGreen());
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2.0D + rmean / 256.0D;
        double weightG = 4.0D;
        double weightB = 2.0D + (255.0D - rmean) / 256.0D;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private boolean areIdentical(Color c1, Color c2) {
        return (Math.abs(c1.getRed() - c2.getRed()) <= 5 &&
                Math.abs(c1.getGreen() - c2.getGreen()) <= 5 &&
                Math.abs(c1.getBlue() - c2.getBlue()) <= 5);
    }

    private ChatColor getClosestChatColor(Color color) {
        if (color.getAlpha() < 128) return null;
        int index = 0;
        double best = -1.0D;
        for (int i = 0; i < this.colors.length; i++) {
            if (areIdentical(this.colors[i], color)) return ChatColor.values()[i];
        }
        for (int i = 0; i < this.colors.length; i++) {
            double distance = getDistance(color, this.colors[i]);
            if (distance < best || best == -1.0D) {
                best = distance;
                index = i;
            }
        }
        return ChatColor.values()[index];
    }

    private String center(String s, int length) {
        if (length <= 0) return "";
        if (s.length() > length) return s.substring(0, length);
        if (s.length() == length) return s;
        int leftPadding = (length - s.length()) / 2;
        StringBuilder leftBuilder = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) leftBuilder.append(" ");
        return leftBuilder.toString() + s;
    }

    public String[] getLines() {
        return this.lines;
    }

    public void sendToPlayer(Player player) {
        for (String line : this.lines) {
            player.sendMessage(line);
        }
    }
}
