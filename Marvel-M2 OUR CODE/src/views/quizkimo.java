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

public class quizkimo extends Application{
	
	Player first;
	Player second;
	Stage kaza;
	int counter;
	Player current;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception 
	{
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
		 second = new Player("DADDY");
		
		Game n = new Game(first,second);
		
		first.getTeam().add(Game.getAvailableChampions().get(0));
		first.getTeam().add(Game.getAvailableChampions().get(1));
		first.getTeam().add(Game.getAvailableChampions().get(2));
		second.getTeam().add(Game.getAvailableChampions().get(3));
		second.getTeam().add(Game.getAvailableChampions().get(4));
		second.getTeam().add(Game.getAvailableChampions().get(5));
		
		
		counter =0;
		current = first;
		HBox main = new HBox(20);
		VBox left = new VBox(20);
		VBox right = new VBox(20);
		
		
		
		Label playername = new Label(first.getName());
		Label Champ = new Label("Champion: ");
		Label Champname = new Label();
		Label Champtype = new Label();
		Label details = new Label();
		String tmp = "Name: " + first.getTeam().get(0).getName();
		String tmp2 = "Type: " + first.getTeam().get(0).getClass().getSimpleName();
		Champname.setText(tmp);
		Champtype.setText(tmp2);
		Button nextchamp = new Button("NEXT CHAMPION");
		
		left.getChildren().addAll(playername,Champ,Champname,Champtype,nextchamp);
		
		
		nextchamp.setOnAction(	new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				if(counter==2)
					counter=0;
				else
					counter++;
				
				
				updatescreen();
				
			
			}
		}
				);
		
		
		
		Label Ability = new Label("ABILITIES: ");
		Label names = new Label();
		String abb = "Names: "+ "\n" + first.getTeam().get(0).getAbilities().get(0).getName()
						+ "\n" + first.getTeam().get(0).getAbilities().get(1).getName()
						+ "\n" + first.getTeam().get(0).getAbilities().get(2).getName();
		names.setText(abb);
		Button nextplayer = new Button("NEXT PLAYER");
		
		nextplayer.setOnAction(	new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				if(counter==2)
					counter=0;
				else
					counter++;
				
				
				if(current == first)
					current = second;
				else
					current= first;
				
				updatescreen();
				
			
			}
		}
				);
		
		
		right.getChildren().addAll(Ability,names,nextplayer);
		main.getChildren().addAll(left,right);
		
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
		
		HBox main = new HBox(20);
		VBox left = new VBox(20);
		VBox right = new VBox(20);
		
		
		
		Label playername = new Label(current.getName());
		Label Champ = new Label("Champion: ");
		Label Champname = new Label();
		Label Champtype = new Label();
		Label details = new Label();
		String tmp = "Name: " + current.getTeam().get(counter).getName();
		String tmp2 = "Type: " + current.getTeam().get(counter).getClass().getSimpleName();
		Champname.setText(tmp);
		Champtype.setText(tmp2);
		Button nextchamp = new Button("NEXT CHAMPION");
		
		left.getChildren().addAll(playername,Champ,Champname,Champtype,nextchamp);
		
		
		nextchamp.setOnAction(	new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				if(counter==2)
					counter=0;
				else
					counter++;
				
				
				updatescreen();
				
			
			}
		}
				);
		
		
		
		Label Ability = new Label("ABILITIES: ");
		Label names = new Label();
		String abb = "Names: "+ "\n" + current.getTeam().get(counter).getAbilities().get(0).getName()
						+ "\n" + current.getTeam().get(counter).getAbilities().get(1).getName()
						+ "\n" + current.getTeam().get(counter).getAbilities().get(2).getName();
		names.setText(abb);
		Button nextplayer = new Button("NEXT PLAYER");
		nextplayer.setOnAction(	new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				
				
				
				if(current == first)
					current = second;
				else
					current= first;
				
				updatescreen();
				
			
			}
		}
				);
		right.getChildren().addAll(Ability,names,nextplayer);
		main.getChildren().addAll(left,right);
		
		kaza.getScene().setRoot(main);
		
		
		
		
	}
	
	
	

}













