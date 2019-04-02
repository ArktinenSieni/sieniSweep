package sieniSweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sieniSweeper.models.Tile;

public class SieniUI extends Application {
    private Scene menuScene;
    private Scene gameScene;
    private SweepLogic logic;

    //gamenlabel vaihda teksti kun voitto tai häviö
    //gameLabel.setText("teksti")
    private Label gameLabel;

    public void start(Stage stage) {
        //menu window

        logic = new SweepLogic(16, 16, 40);

        BorderPane menuPane = new BorderPane();
        Label title = new Label("Tervetuloa pelaamaan miinaharavaa!");
        Button startButton = new Button("Aloita");


        VBox results = new VBox();
        results.getChildren().add(new Label("Tulokset"));
        results.getChildren().add(new Label("toteuta listaus tähän"));


        menuPane.setTop(title);
        menuPane.setBottom(startButton);
        menuPane.setRight(results);


        menuPane.setPrefSize(500, 400);
        BorderPane.setMargin(title, new Insets(10,10,10,50));
        BorderPane.setMargin(startButton, new Insets(10,10,10,10));
        BorderPane.setMargin(results, new Insets(10,10,10,10));

        startButton.setOnAction((event) -> {
            //luo uusi peli
            stage.setScene(gameScene);
        });

        menuScene = new Scene(menuPane);



        //game window

        BorderPane gamePane = new BorderPane();
        Label label = new Label("peli tulee tähän");
        Button backButton = new Button("Palaa valikkoon");

        // Setting up the game canvas
        Canvas fieldCanvas = new Canvas(300, 300);
        drawMineField(fieldCanvas, logic);
        fieldCanvas.setOnMouseClicked((event) -> {
            handleMouseClick(event, fieldCanvas, logic);
            drawMineField(fieldCanvas, logic);
        });

        gamePane.setTop(label);
        gamePane.setBottom(backButton);
        gamePane.setCenter(fieldCanvas); // add the canvas to gamePane
        gamePane.setPrefSize(500, 400);

        BorderPane.setMargin(backButton, new Insets(10,10,10,10));

        backButton.setOnAction((event) -> {
            stage.setScene(menuScene);
        });

        gameScene = new Scene(gamePane);


        stage.setTitle("Miinaharava");
        stage.setScene(menuScene);
        stage.show();
    }

    private int locationInGrid(Double locationInCanvas, Double canvasSize, int gridSize) {
        double threshold = 0.5;
        long location = Math.round(
                (locationInCanvas / canvasSize) * (gridSize - 1) - threshold
        );

        return (int) location;
    }

    private void handleMouseClick(MouseEvent event, Canvas fieldCanvas, SweepLogic logic) {
        double mouseX = event.getX();
        double width = fieldCanvas.getWidth();
        int fieldWidth = logic.mineField.getMineField().length;

        double mouseY = event.getY();
        double height = fieldCanvas.getWidth();
        int fieldHeight = logic.mineField.getMineField()[0].length;

        int x = locationInGrid(mouseX, width, fieldWidth);
        int y = locationInGrid(mouseY, height, fieldHeight);

        logic.sweep(x, y);
    }

    private void drawMineField(Canvas canvas,  SweepLogic logic) {
        Tile[][] mineField = logic.mineField.getMineField();
        int fieldX = mineField.length;
        int fieldY = mineField[0].length;

        double squareWidth = canvas.getWidth() / fieldX;
        double squareHeight = canvas.getHeight() / fieldY;

        GraphicsContext brush = canvas.getGraphicsContext2D();

        for (int i = 0; i < fieldX; i++) {
            for (int j = 0; j < fieldY; j++) {
                double startX = i * squareWidth;
                double startY = j * squareHeight;
                Tile tile = mineField[i][j];

                if (tile.isVisible()) {
                    // Background
                    brush.setFill(Color.WHITE);
                    brush.fillRect(startX, startY, squareWidth, squareHeight);

                    // Desired text
                    brush.setFill(Color.BLACK);
                    brush.setTextAlign(TextAlignment.CENTER);
                    brush.setTextBaseline(VPos.CENTER);

                    // This could be done in UI as well to add colors etc.
                    String symbol = logic.getTileSymbol(tile);

                    brush.fillText(
                            symbol,
                            Math.round(startX) + squareWidth / 2,
                            Math.round(startY) + squareHeight / 2
                    );
                } else {
                    brush.setFill(Color.BLACK);
                    brush.fillRect(startX, startY, squareWidth, squareHeight);
                }
            }
        }
    }
}
