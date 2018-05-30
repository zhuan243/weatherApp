package input_Control;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class DocumentControl implements DocumentListener{

	//initialization of private instance variables
	private JTextField searchField;
	private TableRowSorter<TableModel> sorter;

	//constructor
	public DocumentControl(JTextField searchField,	TableRowSorter<TableModel> sorter){
		this.searchField=searchField;
		this.sorter=sorter;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		update();
	}

	//method that filters through the data relevant to what the user has typed in the text field
	//and updates the table in concordance
	private void update(){
		String text=searchField.getText();
		if(text.trim().length()==0){
			sorter.setRowFilter(null);
		}
		else{
			sorter.setRowFilter(RowFilter.regexFilter(text));
		}	
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		update();

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		update();
	}
}
