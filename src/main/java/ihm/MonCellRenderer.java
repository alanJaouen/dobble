package ihm;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MonCellRenderer extends DefaultTableCellRenderer {

	private String nom;
	public MonCellRenderer(String nomJoueur)
	{
		super();
		this.nom=nomJoueur;
	}
	
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);


	            if (table.getValueAt(row, 0).equals(this.nom)) {
	                Color clr = new Color(255, 226, 198);
	                cell.setBackground(clr);
	            }
	            else  cell.setBackground(Color.white);

	            

		return cell;
	}
	
}
