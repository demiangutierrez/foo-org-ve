package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import util.FileWatcherThread;

import com.jogamp.opengl.util.Animator;

/**
 * @author Alejandro Salas <br>
 *         Created on Oct 24, 2012
 */
public class FrmLegoViewer extends JFrame {

	private DefaultListModel<File> listModel = new DefaultListModel<File>();
	private JList<File> lstFile = new JList<File>(listModel);
	private final String[] args;
	private Main main;
	private Animator animator;
	private GLCanvas canvas;

	public FrmLegoViewer(String[] args) {
		this.args = args;
		setSize(900, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		load();
		startDirWatcher();
		initGUI();

		setVisible(true);
		animator.start();
	}

	// Loading models
	private void load() {
		File modelDir = new File(Main.model_base);
		for (File file : modelDir.listFiles()) {
			listModel.addElement(file);
		}
	}

	// Watching the dir for changes
	private void startDirWatcher() {
		FileWatcherThread t = new FileWatcherThread(lstFile);
		t.setDaemon(true);
		t.start();
	}

	private void initGUI() {
		lstFile.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstFile.setSelectedIndex(0);
		lstFile.setCellRenderer(new FileCellRenderer());
		lstFile.setPreferredSize(new Dimension(300, 100));
		lstFile.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				listSelectionChanged(evt);
			}
		});

		canvas = new GLCanvas();
		canvas.setBackground(Color.green);
		animator = new Animator(canvas);
		try {
			main = new Main(args);
			canvas.addGLEventListener(main);
			loadSelectedModel();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				lstFile, canvas);
		splitPane.setDividerLocation(115);
		getContentPane().add(splitPane, BorderLayout.CENTER);
	}

	private void listSelectionChanged(ListSelectionEvent evt) {
		if (!evt.getValueIsAdjusting()) {
			return;
		}

		loadSelectedModel();
	}

	private void loadSelectedModel() {
		File file = listModel.getElementAt(lstFile.getSelectedIndex());
		main.reload(file.getName());
	}

	public static void main(String[] args) {
		new FrmLegoViewer(args);
	}

	private final class FileCellRenderer implements ListCellRenderer<File> {
		@Override
		public Component getListCellRendererComponent(
				JList<? extends File> list, File value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel lbl = new JLabel(value.getName());

			if (isSelected) {
				lbl.setBackground(list.getSelectionBackground());
				lbl.setForeground(list.getSelectionForeground());
			} else {
				lbl.setBackground(list.getBackground());
				lbl.setForeground(list.getForeground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			lbl.setOpaque(true);
			return lbl;
		}
	}
}