package eq.system.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.github.cjengineer18.desktopwindowtemplate.JGenericWindow;
import com.github.cjengineer18.desktopwindowtemplate.exception.InvalidCommandException;

public class Window01 extends JGenericWindow {

	private static final long serialVersionUID = 48L;

	private ArrayList<double[]> table;
	private ArrayList<Double> error;

	public Window01(String method, ArrayList<double[]> table, ArrayList<Double> error) throws Exception {
		this.table = table;
		this.error = error;
		loadWorkArea(String.format(Locale.ENGLISH, "Results for %s", method), 1000, 300, true,
				JGenericWindow.NO_CONFIRM_AT_CLOSE);
	}

	@Override
	public Object singleGetter(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void singleSetter(int s, Object newObject) throws InvalidCommandException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void workArea() throws Exception {
		// TODO Auto-generated method stub
		Container mainBox = getContentPane();
		String[] cols = new String[table.get(0).length + 2];
		Object[][] data = new Object[table.size()][cols.length];

		int i;
		int j;

		mainBox.setLayout(new BorderLayout());

		cols[0] = "k";

		for (i = 1; i < cols.length; i++) {
			cols[i] = String.format(Locale.ENGLISH, "x%d(k)", i);
		}

		cols[cols.length - 1] = "Error";

		for (i = 0; i < data.length; i++) {
			for (j = 0; j < data[i].length; j++) {
				if (j == 0) {
					data[i][j] = i + 1;
				} else if (j == data[i].length - 1) {
					data[i][j] = error.get(i);
				} else {
					data[i][j] = table.get(i)[j - 1];
				}
			}
		}

		mainBox.add(BorderLayout.CENTER, new JScrollPane(new JTable(data, cols)));
	}

}
