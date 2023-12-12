package editor;

import com.formdev.flatlaf.FlatDarkLaf;
import editor.controller.EditorController;
import editor.view.EditorView;
import soundboard.model.catalogue.Catalogue;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame app = new JFrame("Catalogue Editor");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Catalogue catalogue = new Catalogue();
        catalogue.load("docs/catalogue_sample.json");
        EditorView view = new EditorView();
        new EditorController(view, catalogue, app);

        app.setContentPane(view);
        app.pack();
        app.setVisible(true);
    }
}
