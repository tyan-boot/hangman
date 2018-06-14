import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MainController {
    @FXML
    private ImageView hangManImg;

    @FXML
    private Label word;

    @FXML
    private TextField guess;

    private ArrayList<Image> images = new ArrayList<>();

    private Integer guessCount = 0;

    private String targetWord;

    @FXML
    public void initialize() {
        for (Integer i = 0; i <= 7; i++) {
            Image image = new Image(getClass().getResourceAsStream(i.toString() + ".jpg"));
            this.images.add(image);
        }

        hangManImg.setImage(images.get(0));

        targetWord = "hello";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < targetWord.length(); i++) {
            sb.append("_");
        }

        word.setText(sb.toString());
    }

    public void onGuessClick(MouseEvent event) {
        String letter = guess.getText();
        String w = word.getText();

        StringBuilder sb = new StringBuilder();
        sb.append(w);

        if (letter.isEmpty()) return;

        if (letter.length() > 1) letter = letter.substring(0, 1).toLowerCase();

        if (w.contains(letter)) {
            guessCount++;
            hangManImg.setImage(images.get(guessCount));
            return;
        }

        boolean find = false;

        for (int i = 0; i < targetWord.length(); i++) {
            Character c = targetWord.charAt(i);
            if (c.equals(letter.charAt(0))) {
                sb.replace(i, i + 1, c.toString());
                find = true;
            }
        }

        if (!find) {
            guessCount++;
            hangManImg.setImage(images.get(guessCount));
            return;
        }

        word.setText(sb.toString());
    }
}
