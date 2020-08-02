package it.unibs.ing.domohouse.view2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class GraphicOptionPanel extends JPanel {

	private JButton btnCancel;

	/**
	 * Create the panel.
	 */
	public GraphicOptionPanel() {
		setLayout(new MigLayout("", "[center][][]", "[][][][][]"));

		JButton btnFont = new JButton("Font");
		add(btnFont, "cell 0 0");
		btnFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFontOptionPanel();
			}
		});

		JButton btnBackground = new JButton("Background");
		add(btnBackground, "cell 0 1");
		btnBackground.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = showColorChooser();
				if (newColor != null)
					Preferences.setBackgroundColor(newColor);
			}
		});

		JButton btnSecondaryBackground = new JButton("2nd Background");
		add(btnSecondaryBackground, "cell 0 2");
		btnSecondaryBackground.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = showColorChooser();
				if (newColor != null)
					Preferences.setSecondaryBackgroundColor(newColor);
			}
		});

		JButton btnForeground = new JButton("Foreground");
		add(btnForeground, "cell 0 3");
		
		btnForeground.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = showColorChooser();
				if (newColor != null)
					Preferences.setForegroundColor(newColor);
			}
		});
		
		btnCancel = new JButton("Annulla");
		add(btnCancel, "cell 1 4");
	}

	private Color showColorChooser() {
		Color newColor = JColorChooser.showDialog(null, "Scegli un colore", Color.RED);
		return newColor;
	}

	private void showFontOptionPanel() {
		String[] possibilities = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String s = (String) JOptionPane.showInputDialog(this, "Scegli il Font:", "Font", JOptionPane.PLAIN_MESSAGE,
				null, possibilities, ViewStrings.DEFAULT_FONT);

		if ((s != null) && (s.length() > 0)) {
			Preferences.setFont(new Font(s, 0, ViewStrings.DEFAULT_SIZE_FONT));
		}
	}
	
	public void addCancelListener(ActionListener l) {
		btnCancel.addActionListener(l);
	}
}
