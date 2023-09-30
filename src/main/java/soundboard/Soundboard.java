package soundboard;

import com.formdev.flatlaf.FlatDarkLaf;
import soundboard.panels.CatalogueSelectorPanel;
import soundboard.panels.ChannelSelectorPanel;
import soundboard.panels.MusicPlayerPanel;

import javax.swing.*;

public class Soundboard {

    final static double VERSION = 0.3;
    final static String CATALOGUE = "sample_catalogue.json";

    Icons icons;
    Client client;
    API api;
    JPanel home;
    JPanel soundboard;
    JFrame app;

    private Soundboard() {
        icons = new Icons();
        home = buildHome();
        soundboard = buildSoundboard();

        //Create and set up the window.
        app = new JFrame(String.format("Soundboard V%s", VERSION));
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(home);
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new Soundboard().run();
    }

    private JPanel buildSoundboard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        CatalogueSelectorPanel catalogueSelector = new CatalogueSelectorPanel(api);
        catalogueSelector.loadUI(CATALOGUE);
        panel.add(catalogueSelector);

        JPanel mediaPanel = new JPanel();

        MusicPlayerPanel miniPlayer = new MusicPlayerPanel(api, icons);
        mediaPanel.add(miniPlayer);

        ChannelSelectorPanel channelSelector = new ChannelSelectorPanel(api, icons);
        channelSelector.populateChannelList(new String[]{"0", "1", "3", "4", "5", "6", "7", "8", "9"});
        mediaPanel.add(channelSelector);

        panel.add(mediaPanel);
        return panel;
    }

    private JPanel buildHome() {
        JPanel panel = new JPanel();

        JLabel status = new JLabel();
        JButton connect = new JButton("Connect");
        connect.addActionListener(e -> {
            try {
                client = Client.getClient();
                api = new API(client);
                status.setText("Connected!");

                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.out.println("Label clearing thread interrupted.");
                    }
                    status.setText("");
                }).start();

                openSoundboard();

            } catch (Client.ClientCreationException c) {
                status.setText("Connection Failed.");
            }
        });

        panel.add(connect);
        panel.add(status);

        return panel;
    }

    private void openSoundboard() {
        app.setContentPane(soundboard);
        app.pack();
    }

    public void run() {
        //Display the window.
        app.pack();
        app.setVisible(true);
    }
}