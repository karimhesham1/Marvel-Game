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
import javafx.scene.media.MediaView;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.PowerUp;
import model.effects.SpeedUp;
import model.effects.Stun;
import model.world.Champion;
import model.world.Condition;
import model.world.Cover;
import model.world.Direction;
import javafx.util.Duration;


import javafx.application.Application;
import javafx.stage.Stage;

public class Quiz3 extends Application {

	Player first;
	Player second;
	Stage kaza;
	Game game;
	VBox main;
	Champion currchamp;
	int counter;
	Label ability1;
	Label ability2;
	Label ability3;
	
	public static void main(String[] args) {
		launch(args);
	}

	
	@Override
	public void start(Stage arg0) throws Exception {
		
		
		
		try {
			Game.loadAbilities("Abilities.csv");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		try {
			Game.loadChampions("Champions.csv");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		 first = new Player("KIMO");
		 second = new Player("DANY");
		
		 game= new Game(first,second);
		 counter = 0;
		 main = new VBox(40);
		 
		 int random = ((int) (Math.random() * (Game.getAvailableChampions().size())));
		 currchamp = Game.getAvailableChampions().get(random);
		 
		 Label currchamplabel = new Label(currchamp.getName());
		 
		 currchamplabel.setOnMouseClicked(new EventHandler<MouseEvent>()   //FOR LABELS ON MOUSE CLICKED =======================
					{
						public void handle(MouseEvent event) 
						{
							if(counter == 0)
							{
								ability1.setVisible(true);
								counter++;
								return;
							}
							if(counter == 1)
							{
								ability2.setVisible(true);
								counter++;
								return;
							}
							if(counter == 2)
							{
								ability3.setVisible(true);
								counter++;
								return;
							}
							if(counter == 3)
							{
								updatescreen();
								counter=0;
								return;
								
							}
							
							
							
							
						}
					}
					);
				 
		 HBox main2 = new HBox(50);
		 main.getChildren().add(currchamplabel);
		
		 
		 
		 
		 ability1 = new Label();
		 ability1.setText(currchamp.getAbilities().get(0).getName());
		 ability2 = new Label();
		 ability2.setText(currchamp.getAbilities().get(1).getName());
		 ability3 = new Label();
		 ability3.setText(currchamp.getAbilities().get(2).getName());
		 
		 ability1.setVisible(false);
		 ability2.setVisible(false);
		 ability3.setVisible(false);
		 
		 main2.getChildren().add(ability1);
		 main2.getChildren().add(ability2);
		 main2.getChildren().add(ability3);
		 main.getChildren().add(main2);

		main.setAlignment(Pos.TOP_CENTER);
		main2.setAlignment(Pos.CENTER);
		 
		
		kaza = new Stage();
		Scene quiz= new Scene(main);
		kaza.setMinHeight(600);
		kaza.setMinWidth(800);
		kaza.setScene(quiz);
		kaza.setTitle("Quiz");
		kaza.show();


	}
	
	
	
	public void updatescreen()
	{
		
		main = new VBox(40);
		 
		 int random = ((int) (Math.random() * (Game.getAvailableChampions().size())));
		 currchamp = Game.getAvailableChampions().get(random);
		 
		 Label currchamplabel = new Label(currchamp.getName());
		 
		 currchamplabel.setOnMouseClicked(new EventHandler<MouseEvent>()   //FOR LABELS ON MOUSE CLICKED =======================
					{
						public void handle(MouseEvent event) 
						{
							if(counter == 0)
							{
								ability1.setVisible(true);
								counter++;
								return;
							}
							if(counter == 1)
							{
								ability2.setVisible(true);
								counter++;
								return;
							}
							if(counter == 2)
							{
								ability3.setVisible(true);
								counter++;
								return;
							}
							if(counter == 3)
							{
								updatescreen();
								counter=0;
								return;
								
							}
							
							
						}
					}
					);
				 
		 HBox main2 = new HBox(50);
		 main.getChildren().add(currchamplabel);
		
		 
		 
		 
		 ability1 = new Label();
		 ability1.setText(currchamp.getAbilities().get(0).getName());
		 ability2 = new Label();
		 ability2.setText(currchamp.getAbilities().get(1).getName());
		 ability3 = new Label();
		 ability3.setText(currchamp.getAbilities().get(2).getName());
		 
		 ability1.setVisible(false);
		 ability2.setVisible(false);
		 ability3.setVisible(false);
		 
		 main2.getChildren().add(ability1);
		 main2.getChildren().add(ability2);
		 main2.getChildren().add(ability3);
		 main.getChildren().add(main2);

		main.setAlignment(Pos.TOP_CENTER);
		main2.setAlignment(Pos.CENTER);
		 
		
		kaza.getScene().setRoot(main);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
