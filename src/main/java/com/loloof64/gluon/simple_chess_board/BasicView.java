package com.loloof64.gluon.simple_chess_board;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Icon;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BasicView extends View {

    public BasicView(String name) {
        super(name);

        try {
            Node pieceSvg = FXMLLoader.load(getClass().getClassLoader().getResource("kd.fxml"));
            Label pieceImage = new Label();
            pieceImage.setGraphic(pieceSvg);
            pieceImage.setScaleX(2.0);
            pieceImage.setScaleY(2.0);

            Label label = new Label("Hello JavaFX World!");

            Button button = new Button("Change the World by Loloof64 !");
            button.setGraphic(new Icon(MaterialDesignIcon.LANGUAGE));
            button.setOnAction(e -> label.setText("Hello JavaFX Universe!"));

            VBox controls = new VBox(15.0, pieceImage, label, button);
            controls.setAlignment(Pos.CENTER);

            setCenter(controls);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
        appBar.setTitleText("Basic View");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }
    
}
