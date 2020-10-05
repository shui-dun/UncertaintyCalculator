package sample;

import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setStyle("-fx-background-image: url('/icon/wallpaper.jpg');");

        Button buttonCount=(Button)root.lookup("#buttonCount");
        Button buttonClear=(Button)root.lookup("#buttonClear");
        Button buttonChoice=(Button)root.lookup("#buttonChoice");
        TextField textNum=(TextField)root.lookup("#textNum");
        TextField textData=(TextField)root.lookup("#textData");
        TextField textInstrument=(TextField)root.lookup("#textInstrument");
        TextField textMean=(TextField)root.lookup("#textMean");
        TextField textSD=(TextField)root.lookup("#textSD");
        TextField textMeanSD=(TextField)root.lookup("#textMeanSD");
        TextField textA=(TextField)root.lookup("#textA");
        TextField textB=(TextField)root.lookup("#textB");
        TextField textUncertainty=(TextField)root.lookup("#textUncertainty");
        TextField textRelative=(TextField)root.lookup("#textRelative");
        TextField textResult=(TextField)root.lookup("#textResult");

        buttonCount.setTooltip(new Tooltip("Count"));
        buttonClear.setTooltip(new Tooltip("Clear"));
        buttonChoice.setTooltip(new Tooltip("Choice"));

        ContextMenu contextMenu=new ContextMenu();
        MenuItem menuItem=new MenuItem("均匀分布");
        contextMenu.getItems().add(menuItem);
        buttonChoice.setContextMenu(contextMenu);

        buttonCount.setOnAction(event ->
        {
            int amount=Integer.parseInt(textNum.getText());
            double []data=new double[amount];
            String [] strings=textData.getText().split(" ");
            for(int i=0;i<amount;i++)
                data[i]=Double.parseDouble(strings[i]);
            double instrument=Double.parseDouble(textInstrument.getText());
            Uncertainty uncertainty=new Uncertainty(instrument,data);
            textMean.setText(Double.toString(uncertainty.getDataMean()));
            textSD.setText(Double.toString(uncertainty.getSD()));
            textMeanSD.setText(Double.toString(uncertainty.getAUncertainty()));
            textA.setText(Double.toString(uncertainty.getAUncertainty()));
            textB.setText(Double.toString(uncertainty.getBUncertainty()));
            textUncertainty.setText(Double.toString(uncertainty.getUncertain()));
            textRelative.setText(String.format("%f%%",100*uncertainty.getRelativeUncertainty()));
            textResult.setText(uncertainty.getMeasureResult());
        });

        buttonClear.setOnAction(event ->
        {
            textA.setText("");
            textB.setText("");
            textData.setText("");
            textInstrument.setText("");
            textMean.setText("");
            textMeanSD.setText("");
            textNum.setText("");
            textRelative.setText("");
            textResult.setText("");
            textSD.setText("");
            textUncertainty.setText("");
        });

        textNum.setOnKeyPressed(event ->
        {
            if(event.getCode()==KeyCode.TAB)
            {
                Platform.runLater(() -> textData.requestFocus());
            }
        });

        textData.setOnKeyPressed(event ->
        {
            if(event.getCode()==KeyCode.TAB)
            {
                Platform.runLater(() -> textInstrument.requestFocus());
            }
        });

        primaryStage.getIcons().add(new Image("/icon/white_machine.png"));
        primaryStage.setTitle("UncertaintyCalculator");
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        KeyCombination keyCombination=new KeyCodeCombination(KeyCode.ENTER);
        scene.getAccelerators().put(keyCombination, () -> buttonCount.fire());

    }
    public static void main(String[] args) { launch(args); }
}