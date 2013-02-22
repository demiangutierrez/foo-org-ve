package util;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import view.Main;

/**
 * @author Alejandro Salas <br>
 *         Created on Oct 24, 2012
 */
public class FileWatcherThread extends Thread {

	private JList<File> lstFile;
	private DefaultListModel<File> listModel;

	public FileWatcherThread(JList<File> lstFile) {
		super("Directory Watch Thread");
		this.lstFile = lstFile;
		this.listModel = (DefaultListModel<File>) lstFile.getModel();
	}

	@Override
	public void run() {
		Path modelDir = Paths.get(Main.model_base);

		try {
			WatchService watcher = modelDir.getFileSystem().newWatchService();
			modelDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

			while (true) {
				WatchKey watchKey = watcher.take();
				List<WatchEvent<?>> eventList = watchKey.pollEvents();

				for (WatchEvent<?> evt : eventList) {
					@SuppressWarnings("unchecked")
					File file = ((WatchEvent<Path>) evt).context().toFile();

					if (evt.kind() == OVERFLOW) {
						continue;
					}

					if (evt.kind() == ENTRY_CREATE) {
						listModel.addElement(file);
					}

					if (evt.kind() == ENTRY_DELETE) {
						listModel.removeElement(file);
					}

					// The same file that is selected was modified,
					// should reload
					if (evt.kind() == ENTRY_MODIFY
							&& !lstFile.isSelectionEmpty()
							&& lstFile.getSelectedValue().equals(file)) {
						System.out.println("file changed. Reload!!!");
					}
				}
				boolean valid = watchKey.reset();
				if (!valid) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}