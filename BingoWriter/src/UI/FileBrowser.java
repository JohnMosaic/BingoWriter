package UI;

import javax.swing.*;
import java.io.File;
import java.util.prefs.Preferences;

public class FileBrowser {
    public void SelectFile(BingoWriterUI ui) {
        try {
            Preferences pref = Preferences.userRoot().node(this.getClass().getName());
            String path = pref.get("lastPath", "");
            JFileChooser jfc;
            if (!path.equals("")) {
                jfc = new JFileChooser(path);
            } else {
                jfc = new JFileChooser();
            }
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int ret = jfc.showDialog(new JLabel(), "Select the input file.");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                String inputFile = file.getAbsolutePath();
                ui.tfdInputFile.setText(inputFile);
                String lastPath = "";
                if (inputFile.contains("\\")) {
                    lastPath = inputFile.substring(0, inputFile.lastIndexOf("\\"));
                } else if (inputFile.contains("/")) {
                    lastPath = inputFile.substring(0, inputFile.lastIndexOf("/"));
                }
                pref.put("lastPath", lastPath);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
