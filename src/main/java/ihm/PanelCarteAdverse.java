package ihm;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelCarteAdverse extends JPanel {
	private Image dosCarte;
	private int tailleCarte;
	
	public PanelCarteAdverse()
	{
		super();
		this.dosCarte = new ImageIcon("images/dosCarte.png").getImage();
		this.tailleCarte = 0;
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(this.tailleCarte == 0)
		{
			int w = this.getWidth();
			int h = this.getHeight();
			if(w >= h)
			{
				this.tailleCarte = h;
			}
			else this.tailleCarte = w;
		}
		
		g.drawImage(this.dosCarte, (this.getWidth() - this.tailleCarte)/2, 0, this.tailleCarte, this.tailleCarte, this);
		super.paint(g);
	}
}
