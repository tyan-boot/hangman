import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.util.*;

public class MainController {
    @FXML
    public ComboBox<String> types;

    @FXML
    private ImageView hangManImg;

    @FXML
    private Label word;

    @FXML
    private TextField guess;

    private ArrayList<Image> images = new ArrayList<>();

    private Integer guessCount = 0;

    private String targetWord;

    private Map<String, List<String>> words = new HashMap<>();

    @FXML
    public void initialize() {
        for (Integer i = 0; i <= 7; i++) {
            Image image = new Image(getClass().getResourceAsStream(i.toString() + ".jpg"));
            this.images.add(image);
        }
        initCanvas();

        // load words
        final Scanner scanner = new Scanner(getClass().getResourceAsStream("words.txt"));

        while (scanner.hasNext()) {
            final String line = scanner.nextLine();
            final String[] split = line.split(" ");

            if (!words.containsKey(split[1])) {
                words.put(split[1], new ArrayList<>());
            }

            words.get(split[1]).add(split[0]);
        }

        final ObservableList<String> strings = FXCollections.observableList(new ArrayList<>(words.keySet()));

        types.setItems(strings);
        types.getSelectionModel().select(0);
    }

    public void onGuessClick(MouseEvent event) {
        String letter = guess.getText();
        String w = word.getText();

        StringBuilder sb = new StringBuilder();
        sb.append(w);

        if (letter.isEmpty()) return;

        if (letter.length() > 1) letter = letter.substring(0, 1).toLowerCase();

        // exist letter
        if (w.contains(letter)) {
            guessCount++;
            hangManImg.setImage(images.get(guessCount));
            checkCount();
            return;
        }

        // replace
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
            checkCount();
            return;
        }

        word.setText(sb.toString());
    }

    private void checkCount() {
        if (guessCount >= 7) {
            new Alert(Alert.AlertType.INFORMATION, "You Lose!").show();
        }

        if (!word.getText().contains("_")) {
            new Alert(Alert.AlertType.INFORMATION, "You Win").show();
        }
    }

    private void initCanvas() {
        guessCount = 0;
        hangManImg.setImage(images.get(0));
    }

    public void selectType(MouseEvent event) {
        final String type = types.getSelectionModel().getSelectedItem();

        final List<String> strings = words.get(type);
        Collections.shuffle(strings);

        targetWord = strings.get(0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < targetWord.length(); i++) {
            sb.append("_");
        }

        word.setText(sb.toString());

        initCanvas();
    }
}
