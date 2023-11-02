package soundboard.controller.discordbot;

import soundboard.controller.RequestHandler;
import soundboard.model.DiscordBot;
import soundboard.model.Icons;
import soundboard.view.discordbot.VolumeSlider;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

class VolumeSliderController implements DiscordBotController {

    private final DiscordBot model;
    private final JSlider volumeSlider;

    protected VolumeSliderController(DiscordBot model, VolumeSlider volumeSlider) {
        this.model = model;
        this.volumeSlider = volumeSlider;
    }

    public void sync() {
        volumeSlider.setValue(model.getVolume());
    }

    public void connect(RequestHandler requestHandler) {
        Arrays.stream(volumeSlider.getMouseListeners()).toList().forEach(volumeSlider::removeMouseListener);
        volumeSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int desiredVol = volumeSlider.getValue();
                if (model.getVolume() == desiredVol) return;
                requestHandler.set_volume(desiredVol);
            }
        });
    }

    @Override
    public void loadIcons(Icons icon) {
        //Cries in Spanish. I have no Icon.
    }
}