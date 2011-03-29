
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;

import com.xerox.amazonws.simpledb.Domain;
import com.xerox.amazonws.simpledb.DomainMetadata;
import com.xerox.amazonws.simpledb.Item;
import com.xerox.amazonws.simpledb.ItemVO;
import com.xerox.amazonws.simpledb.SDBException;
import com.xerox.amazonws.simpledb.SDBListResult;
import com.xerox.amazonws.simpledb.SDBResult;
import com.xerox.amazonws.simpledb.SimpleDB;

public class QueryTool extends JPanel implements ActionListener {
	private JFrame parent;
	private JComboBox regionList;
	private JComboBox domainList;
	private JTextArea querySpace;
	private JDesktopPane results;
	private SimpleDB sdb;
	private Domain dom;

	public QueryTool(JFrame parent, String accessId, String secretKey, String proxyHost, int proxyPort) {
		this.parent = parent;
		sdb = new SimpleDB(accessId, secretKey);
		if (proxyHost != null) {
			sdb.getConnectionDelegate().setProxyValues(proxyHost, proxyPort);
		}
		layoutGUI();
		loadPrefs();
	}
	
	public void shutdown() {
		savePrefs();
	}

	public void setDomain(String name) {
		try {
			dom = sdb.getDomain(name);
		} catch (SDBException ex) {
			System.err.println("Could not create domain object: "+ex.getMessage());
			System.exit(-1);
		}
	}

	private void layoutGUI() {
		setLayout(new GridBagLayout());

		JButton runQuery = new JButton("run");
		runQuery.addActionListener(this);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		add(runQuery, gbc);

		JButton sponsor = new JButton("Sponsored by:", new ImageIcon("build/classes/dtlogo150.png"));
		sponsor.setHorizontalTextPosition(SwingConstants.LEFT);
		sponsor.setBorderPainted(false);
		sponsor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showInternalMessageDialog(results, 
							"<html><center>Work on QueryTool has been generously sponsored by directThought.<br>Please consider using them for your next project.<br>See <a href=\"http://directThought.com/\">directThought.com</a> for more information.</center></html>",
							dom.getName()+" metadata",
							JOptionPane.PLAIN_MESSAGE);
			}
		});
		gbc = new GridBagConstraints();
		add(sponsor, gbc);

		JLabel regLab = new JLabel("Region:");
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		add(regLab, gbc);

		regionList = new JComboBox(new String [] {"US", "EU"});
		regionList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String endpoint = "sdb.amazonaws.com";
				if (regionList.getSelectedItem().equals("EU")) {
					endpoint = "sdb.eu-west-1.amazonaws.com";
				}
				sdb.setEndpoint(endpoint);
				populateDomainList();
				setDomain((String)domainList.getSelectedItem());
			}
		});
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		add(regionList, gbc);

		JLabel domLab = new JLabel("Domain:");
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		add(domLab, gbc);

		domainList = new JComboBox();
		domainList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setDomain((String)domainList.getSelectedItem());
			}
		});
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		add(domainList, gbc);
		populateDomainList();
		setDomain((String)domainList.getSelectedItem());

		JButton metadata = new JButton("Get Metadata");
		metadata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					DomainMetadata dm = dom.getMetadata().getResult();
					StringBuilder dmOutput = new StringBuilder();
					dmOutput.append("Item Count : ");
					dmOutput.append(dm.getItemCount());
					dmOutput.append("\nAttr Name Count : ");
					dmOutput.append(dm.getAttributeNameCount());
					dmOutput.append("\nAttr Value Count : ");
					dmOutput.append(dm.getAttributeValueCount());
					dmOutput.append("\nItem Names Size : ");
					dmOutput.append(dm.getItemNamesSizeBytes());
					dmOutput.append("\nAttribute Names Size : ");
					dmOutput.append(dm.getAttributeNamesSizeBytes());
					dmOutput.append("\nAttribute Value Size : ");
					dmOutput.append(dm.getAttributeValuesSizeBytes());
					int result = JOptionPane.showInternalOptionDialog(results, dmOutput.toString(),
								dom.getName()+" Domain",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.PLAIN_MESSAGE,
								null, new String [] {"Delete", "Close"}, "Close");
					if (result == 0) {
						result = JOptionPane.showInternalConfirmDialog(results,
									"Do you really want to delete domain : "+dom.getName(),
									"Delete "+dom.getName(),
									JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							sdb.deleteDomain(dom);
							dom = null;
							populateDomainList();
							setDomain((String)domainList.getSelectedItem());
						}
					}
				} catch (SDBException ex) {
					System.err.println("Problem fetching metadata or deleting domain: "+ex.getMessage());
				}
			}
		});
		gbc = new GridBagConstraints();
		add(metadata, gbc);

		JButton newDom = new JButton("New Domain");
		newDom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					String result = JOptionPane.showInternalInputDialog(results,
								"Enter the name of the domain you'd like to create.");
					if (result != null) {
						dom = sdb.createDomain(result).getResult();
						populateDomainList();
					}
				} catch (SDBException ex) {
					System.err.println("Problem creating domain : "+ex.getMessage());
				}
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(newDom, gbc);

		querySpace = new JTextArea();
		querySpace.setFont(new Font("monospaced", Font.PLAIN, 14));
		querySpace.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				if (evt.isControlDown() && evt.getKeyChar() == '\r') {
					executeQuery();
				}
			}
		});

		results = new JDesktopPane();

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(querySpace), results);
		querySpace.setMinimumSize(new Dimension(100, 100));

		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(split, gbc);
	}

	public void populateDomainList() {
		String [] domainNames = new String [] {"no domains found"};
		try {
			List<Domain> list = sdb.listDomains().getItems();
			ArrayList<String> tmp = new ArrayList<String>();
			for (Domain d : list) {
				tmp.add(d.getName());
			}
			domainNames = tmp.toArray(domainNames);
		} catch (SDBException ex) {
			System.err.println("problem communicating with SimpleDB: "+ex.getMessage());
			System.err.println(ex.getCause().getMessage());
		}
		domainList.setModel(new DefaultComboBoxModel(domainNames));
	}

	public void actionPerformed(ActionEvent evt) {
		executeQuery();
	}

	private void executeQuery() {
		try {
			int lineNum = querySpace.getLineOfOffset(querySpace.getCaretPosition())+1;
			StringTokenizer st = new StringTokenizer(querySpace.getText(), "\n", true);
			int lineCount = 0;
			String val = "";
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				if (tok.equals("\n")) {
					lineCount++;
				}
				else {
					val = tok;
				}
				if (lineCount == lineNum) break;
			}
			final String query = val;
			final ResultsFrame resultFrame = new ResultsFrame(query, dom);

			results.add(resultFrame, 1);
			try {
				resultFrame.setSelected(true);
			} catch (java.beans.PropertyVetoException ex) { }
			resultFrame.show();

			// start fetching the data
			resultFrame.reload();
		} catch (BadLocationException ex) {
		}
	}

	private void loadPrefs() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(System.getProperty("user.home", ".")+"/.typica.query.prefs"));
			querySpace.setText(props.getProperty("query.history"));
			domainList.setSelectedItem(props.getProperty("query.domain"));
		} catch (FileNotFoundException ioex) {
			// ignore.... might not be a file yet
		} catch (IOException ioex) {
			System.err.println("Error loading user preferences");
		}
	}

	private void savePrefs() {
		Properties props = new Properties();
		props.setProperty("query.history", querySpace.getText());
		props.setProperty("query.domain", (String)domainList.getSelectedItem());
		try {
			props.store(new FileOutputStream(System.getProperty("user.home", ".")+"/.typica.query.prefs"),
						"http://code.google.com/p/typica");
		} catch (IOException ioex) {
			System.err.println("Error saving user preferences");
		}
	}

	public static void main(String [] args) {
		final JFrame frame = new JFrame("SimpleDB Query Tool");
		if (args.length != 2 && args.length != 4) {
			System.err.println("You must supply your access id and secret key on the command line");
			System.err.println("Usage: QueryTool <AccessId> <SecretKey> [ProxyHost] [ProxyPort]");
			System.exit(-1);
		}
		String host = null;
		int port = 1080;
		try {
			if (args.length == 4) {
				host = args[2];
				port = Integer.parseInt(args[3]);
			}
		} catch (NumberFormatException ex) {
			System.err.println("proxy port not a valid integer, defaulting to "+port);
		}
		final QueryTool controls = new QueryTool(frame, args[0], args[1], host, port);
		Dimension size = controls.getPreferredSize();
		frame.setSize(800, 600);
		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent event) {
					controls.shutdown();
					System.exit(0);
				}
			});
		frame.setContentPane(controls);
		frame.setVisible(true);
	}

	// a table model that shows items from SimpleDB
	public class ItemTableModel extends AbstractTableModel {
		private ArrayList<String> columns;
		private ArrayList<Item> items;

		public ItemTableModel() {
			clearData();
		}

		public void addItems(List<Item> newItems) {
			// ensure all attrs have a column
			int firstRow = items.size();
			boolean colsChanged = false;
			for (Item i : newItems) {
				for (String name : i.getAttributes().keySet()) {
					if (!columns.contains(name)) {
						columns.add(name);
						colsChanged = true;
					}
				}
			}
			items.addAll(newItems);
			if (colsChanged) {
				fireTableStructureChanged();
				if (firstRow > 0) {
					fireTableRowsInserted(firstRow, items.size());
				}
			}
		}

		public void clearData() {
			columns = new ArrayList<String>();
			columns.add("itemName()");
			items = new ArrayList<Item>();
		}

		public int getRowCount() {
			return items.size();
		}

		public int getColumnCount() {
			return columns.size();
		}

		public Class getColumnClass(int column) {
			return String.class;
		}

		public String getColumnName(int column) {
			return columns.get(column);
		}

		public boolean isCellEditable(int row, int column) {
			return false;
		}

		public Object getValueAt(int row, int column) {
			if (column == 0) {
				return items.get(row).getIdentifier();
			}
			else {
				String colName = columns.get(column);
				Set<String> vals = items.get(row).getAttributes().get(colName);
				if (vals == null) {
					return " -- ";
				}
				if (vals.size() == 1) {
					return vals.iterator().next();
				}
				else {
					StringBuilder ret = new StringBuilder();
					for (String val : vals) {
						ret.append(val);
						ret.append(",");
					}
					return ret.toString();
				}
			}

		}
	}

	public class ResultsFrame extends JInternalFrame implements Runnable {
		private ItemTableModel tm;
		private double boxUsage = 0.0;
		private String time = "0";
		private JLabel stats;
		private JButton reload;
		private JButton export;
		private boolean countMode;
		public JPopupMenu menu;
		public int row;
		public int col;
		public Domain domain;

		public ResultsFrame(String title, Domain domain) {
			super(title);
			this.domain = domain;
			setClosable(true);
			setIconifiable(true);
			setMaximizable(true);
			setResizable(true);
			setBounds(0, 0, 550, 300);
			setLayout(new BorderLayout());

			JPanel topPan = new JPanel(new GridBagLayout());
			
			reload = new JButton("reload") {
				public int getWidth() { return getPreferredSize().width; }
				public int getHeight() { return getPreferredSize().height; }
			};
			reload.setEnabled(false);
			reload.setMargin(new Insets(0, 0, 0, 0));
			reload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					reload();
				}
			});
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 0.5;
			gbc.insets = new Insets(0, 0, 0, 0);
			topPan.add(reload, gbc);

			export = new JButton("export") {
				public int getWidth() { return getPreferredSize().width; }
				public int getHeight() { return getPreferredSize().height; }
			};
			export.setEnabled(false);
			export.setMargin(new Insets(0, 0, 0, 0));
			export.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					export();
				}
			});
			topPan.add(export, gbc);

			stats = new JLabel("-");
			gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1.0;
			gbc.insets = new Insets(0, 0, 0, 5);
			topPan.add(stats, gbc);

			add(topPan, BorderLayout.NORTH);
			tm = new ItemTableModel();
			final JTable resultSpace = new JTable(tm);
			try {
				Class [] params = new Class[] { Boolean.TYPE };
				JTable.class.getMethod("setAutoCreateRowSorter", params);
				resultSpace.setAutoCreateRowSorter(true);
			} catch (NoSuchMethodException ex) { }	// ok, we simply won't sort
			resultSpace.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					row = resultSpace.rowAtPoint(evt.getPoint());
					col = resultSpace.columnAtPoint(evt.getPoint());
					menu.show(resultSpace, evt.getX(), evt.getY());
				}
			});
			JScrollPane sp = new JScrollPane(resultSpace);
			add(sp, BorderLayout.CENTER);

			menu = new JPopupMenu();
			JMenuItem copy1 = new JMenuItem("copy cell");
			copy1.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent evt) {
					copyValue((String)resultSpace.getModel().getValueAt(row, col));
				}
			});
			JMenuItem copy2 = new JMenuItem("copy row");
			copy2.addActionListener(new ActionListener () {
				public void actionPerformed(ActionEvent evt) {
					StringBuilder sb = new StringBuilder();
					AbstractTableModel tm = (AbstractTableModel)resultSpace.getModel();
					for (int i=0; i<tm.getColumnCount(); i++) {
						if (i > 0) sb.append("\t");
						sb.append(tm.getValueAt(row, i));
					}
					copyValue(sb.toString());
				}
			});
			menu.add(copy1);
			menu.add(copy2);
		}

		public void copyValue(String value) {
			Clipboard clip = getToolkit().getSystemClipboard();
			StringSelection newData = new StringSelection(value);
			clip.setContents(newData, newData);
		}

		public void reload() {
			reload.setEnabled(false);
			export.setEnabled(false);
			boxUsage = 0.0;
			time = "0";
			updateStats();
			tm.clearData();
			new Thread(this).start();
		}

		public void export() {
			// file picker
			JFileChooser chooser = new JFileChooser(".");
			chooser.setMultiSelectionEnabled(false);
			int result = chooser.showSaveDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION) {
				// scan table model and emit file
				File exportFile = chooser.getSelectedFile();
				if (exportFile.exists()) {
					// ask about overwrite
				}
				try {
					PrintWriter pw = new PrintWriter(exportFile);
					int numColumns = tm.getColumnCount();
					int numRows = tm.getRowCount();
					for (int j=0; j<numColumns; j++) {
						pw.print(tm.getColumnName(j));
						if (j < (numColumns-1)) {
							pw.print(",");
						}
					}
					pw.println("");
					for (int i=0; i<numRows; i++) {
						for (int j=0; j<numColumns; j++) {
							pw.print(tm.getValueAt(i, j).toString());
							if (j < (numColumns-1)) {
								pw.print(",");
							}
						}
						pw.println("");
					}
					pw.close();
				} catch (IOException ex) {
					System.err.println("Error exporting data: "+ex.getMessage());
				}
			}
		}

		public void run() {
			StringBuilder resText = new StringBuilder();
			try {
				int itemCount = 0;
				long start = System.currentTimeMillis();
				String nextToken = null;
				countMode = (getTitle().indexOf("count(*)") > -1);
				long total = 0;
				do {
					SDBListResult<Item> sr = domain.selectItems(getTitle(), nextToken, false);
					List<Item> items = sr.getItems();
					nextToken = sr.getNextToken();
					itemCount += items.size();
					if (countMode) {
						total += Long.parseLong(items.get(0).getAttribute("Count"));
					}
					updateResults(items);
					updateBoxUsage(sr.getBoxUsage());
					if (itemCount > 1000) {
						nextToken = null;
						ArrayList<Item> trunc = new ArrayList<Item>();
						trunc.add(domain.getItem("- truncated -").getResult());
						updateResults(trunc);
					}
				} while (nextToken != null && !nextToken.trim().equals(""));
				long end = System.currentTimeMillis();
				updateTime((end-start)/1000.0);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						reload.setEnabled(true);
						export.setEnabled(true);
					}
				});
				if (countMode) {
					List<Item> tmp = new ArrayList<Item>();
					Item i = new ItemVO("Total");
					HashSet<String> vals = new HashSet<String>();
					vals.add(""+total);
					i.getAttributes().put("Count", vals);
					tmp.add(i);
					updateResults(tmp);
				}
			} catch (SDBException ex) {
				resText.append(ex.getMessage());
			}
		}

		void updateResults(final List<Item> data) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					tm.addItems(data);
					updateStats();
				}
			});
		}

		void updateBoxUsage(final String usage) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						double newUsage = Double.parseDouble(usage);
						boxUsage += newUsage;
						updateStats();
					} catch (NumberFormatException ex) {
						System.err.println("error parsing box usage : "+ex.getMessage());
					}
				}
			});
		}

		void updateTime(final double timeVal) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					time = ""+timeVal;
					updateStats();
				}
			});
		}

		private void updateStats() {
			String usage = ""+boxUsage;
			if (usage.length() > 10) usage = usage.substring(0, 10);
			stats.setText("Box Usage:"+usage+"  Items:"+tm.getRowCount()+"  Time To Run:"+time+" secs");
		}
	}
}
