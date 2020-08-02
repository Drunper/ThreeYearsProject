package it.unibs.ing.domohouse.view2;

import java.awt.Color;
import java.awt.Font;

public class Preferences {

	private static Font myFont = new Font(ViewStrings.DEFAULT_FONT, 0, ViewStrings.DEFAULT_SIZE_FONT);
	private static Color backgroundColor = ViewStrings.DEFAULT_BACKGROUND_COLOR;
	private static Color secondaryBackgroundColor = ViewStrings.DEFAULT_SECONDARY_BACKGROUND_COLOR;
	private static Color foregroundColor = ViewStrings.DEFAULT_FOREGROUND;

	public static Font getFont() {
		return myFont;
	}

	public static void setFont(Font font) {
		myFont = font;
	}

	public static Color getBackgroundColor() {
		return backgroundColor;
	}

	public static void setBackgroundColor(Color backgroundColor) {
		Preferences.backgroundColor = backgroundColor;
	}

	public static Color getSecondaryBackgroundColor() {
		return secondaryBackgroundColor;
	}

	public static void setSecondaryBackgroundColor(Color secondaryBackgroundColor) {
		Preferences.secondaryBackgroundColor = secondaryBackgroundColor;
	}

	public static Color getForegroundColor() {
		return foregroundColor;
	}

	public static void setForegroundColor(Color foregroundColor) {
		Preferences.foregroundColor = foregroundColor;
	}

}
