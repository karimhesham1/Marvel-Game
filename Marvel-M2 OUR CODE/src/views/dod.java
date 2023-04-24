package views;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.*;
import javax.print.DocFlavor.INPUT_STREAM;
import javax.sound.sampled.AudioInputStream;

import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.PowerUp;
import model.effects.SpeedUp;
import model.effects.Stun;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import javafx.util.Duration;

public class dod extends Application{

	GridPane board;	
	Game game;
	Stage stage;

	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage alo) throws Exception
	{
		stage = new Stage();
		board= new GridPane();
		try {
			Game.loadAbilities("Abilities.csv");
		} catch (IOException e) {

			e.printStackTrace();
		}
		game= new Game (new Player("dany"),new Player ("dina"));
		
		
		
		
		
		
		board.setVisible(true);
		Scene quiz= new Scene(board);
		stage.setMinHeight(600);
		stage.setMinWidth(800);
		stage.setScene(quiz);
		stage.setTitle("Quiz");
		help();
		stage.show();
		
	}
	public void help()
	{
		board = new GridPane();
		board.setGridLinesVisible(true);
		board.setMinSize(400, 400);
		board.setAlignment(Pos.CENTER);
		int random = ((int) (Math.random() * (Game.getAvailableAbilities().size())));
		Ability balah= Game.getAvailableAbilities().get(random);
		Label x= new Label(balah.getName());
		x.setFont(Font.font("Arial", 40));
		board.add(x, 0, 0, 1, 1);
		Label y= new Label(balah.getClass().getSimpleName());
		y.setFont(Font.font("Arial", 40));
		board.add(y, 1, 0, 1, 1);
		String rand= new String(""+random);
		Label s= new Label(rand);
		s.setFont(Font.font("Arial", 40));
		board.add(s, 0, 1, 1, 1);
		Label next= new Label("next");
		next.setFont(Font.font("Arial", 40));
		board.add(next, 1, 1, 1, 1);
		next.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			public void handle(MouseEvent event) 
			{
				help();
			}
		}
		);

		
		stage.getScene().setRoot(board);

		
		
		
}

}
