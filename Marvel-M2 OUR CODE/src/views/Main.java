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
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.media.MediaPlayer.Status;
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
import javafx.stage.Screen;
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


public class Main extends Application{
	MediaPlayer mediaPlayer;
	Player first;
	Player second;
	Game game;
	Scene names;
	Scene choosechamp;
	Scene chooselead1;
	Scene chooselead2;
	Scene thegame;
	Label firstplayername;
	Label secondplayername;
	ArrayList<Button> selected= new ArrayList<>();
	Button next3;
	Stage kaza;
	ArrayList<Button> champbuttons= new ArrayList<Button>();
	Direction currdirec;
	GridPane board;
	boolean ability1selected = false;
	boolean ability2selected = false;
	boolean ability3selected = false;
	boolean singletargetselected = false;
	Ability selectedability;
	Button ability1;
	Button ability2;
	Button ability3;
	Button ability4;
	Button attackb;
	Button moveb;
	String css;
	Button pausemusic;
	Image ability1img;
	Image ability2img;
	Image ability3img;
	Image ability4img;
	Image upimg;
	Image downimg;
	Image leftimg;
	Image rightimg;
	Image attackimg;
	Image coverimg1;
	Image coverimg2;
	Image coverimg3;
	Image coverimg4;
	Image coverimg5;
	Image boardimg;
	Image borderimg;
	Glow glow;
	Image chooseBack;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception 
	{
		glow = new Glow();
		glow.setLevel(1);
		kaza = new Stage();
		
		kaza.setFullScreenExitHint("");
		kaza.setFullScreen(true);
		
		intro();
		Image icon = new Image("marvel.png");
		kaza.getIcons().add(icon);
		
		
		
		
		 mediaPlayer.setOnEndOfMedia(() -> {
			 kaza.setScene(names);
				
			 
			 kaza.show();
				kaza.setFullScreen(true);
			
			 
				
	        });
		
		
		//Font avengers = Font.loadFont("C://Users//Daniel Sherif//Desktop//Marvel//avengeance//AVENGEANCE HEROIC AVENGER.ttf",40);
		
		//mediaPlayer.setVolume(0.1);
		pausemusic = new Button();
		Glow glow = new Glow();
		glow.setLevel(1);
		
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        
        VBox enternames = new VBox(20);
        
        
        
        
        Label enternamesL = new Label("Please enter your name: ");
        enternamesL.setFont(Font.font("verdana", 40));
        enternamesL.setTextFill(Color.BLACK);
        enternamesL.setMinSize(100, 100);
        //enternames.getChildren().add(enternamesL);
        
        
        enternames.setAlignment(Pos.CENTER);
        
        Label fname = new Label("First Player");
        VBox firstb= new VBox(7);
        firstb.getChildren().add(fname);
        fname.setFont(Font.font("helvetica.light", 20));
        fname.setTextFill(Color.BLACK);
        final TextField ft= new TextField();
        
        
        ft.getStyleClass().add("textField");
       
        
        ft.setMaxWidth(250);
        
        firstb.getChildren().add(ft);
        firstb.setAlignment(Pos.CENTER_LEFT);
        
        
        
        Label sname = new Label("Second Player");
        VBox secondb= new VBox(7);
        secondb.getChildren().add(sname);
        sname.setFont(Font.font("helvetica.light", 20));
        sname.setTextFill(Color.BLACK);
        final TextField st= new TextField();
        st.setMaxWidth(250);
        secondb.getChildren().add(st);
        secondb.setAlignment(Pos.CENTER_LEFT);
        
        st.getStyleClass().add("textField");
        Label fill = new Label("");
        fill.setPadding(new Insets(70));
        enternames.getChildren().add(fill);
		enternames.getChildren().add(firstb);
		enternames.getChildren().add(secondb);
		
		
		
		//enternames.getChildren().addAll(enternamesL);
		
		enternames.setAlignment(Pos.BASELINE_LEFT);
		enternames.setPadding(new Insets(100));
		
		Button next = new Button();
		enternames.getChildren().add(next);
		
		next.setMinSize(10, 10);
		
		next.getStyleClass().add("nextButtons");
		
		Image enternamesbackimg = new Image("neonback.gif");
		
		names=new Scene(enternames);
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		enternames.setBackground(new Background(new BackgroundImage(enternamesbackimg, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.DEFAULT,backgroundSize)));
		
		enternames.setAlignment(Pos.CENTER_LEFT);
		
		
		
		Image c1 = new Image("next.png");
		ImageView tmp = new ImageView(c1);
		
		tmp.setFitHeight(80);
		tmp.setFitWidth(100);
		
		
		//tmp.setEffect(glow);
		next.setGraphic(tmp);
		Bloom m = new Bloom();
		//m.setThreshold(1);
		//Lighting l = new Lighting();

		next.setEffect(glow);
		Image c2 = new Image("mute.png");
		ImageView tmp2 = new ImageView(c2);
		
		tmp2.setFitHeight(50);
		tmp2.setFitWidth(50);
		pausemusic.setGraphic(tmp2);
		pausemusic.getStyleClass().add("nextButtons");
		 pausemusic.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
					{
						public void handle(ActionEvent event) 
						{
							if(mediaPlayer.getStatus() == Status.PAUSED)
								mediaPlayer.play();
							else
								mediaPlayer.pause();
							
						}
					}
							);
		
		 
		 pausemusic.setAlignment(Pos.BOTTOM_LEFT);
		enternames.getChildren().add(pausemusic);
		
		
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
		
		
		
		Button button1 = new Button();
		Button button2 = new Button();
		Button button3 = new Button();
		Button button4 = new Button();
		Button button5 = new Button();
		Button button6 = new Button();
		Button button7 = new Button();
		Button button8 = new Button();
		Button button9 = new Button();
		Button button10 = new Button();
		Button button11= new Button();
		Button button12= new Button();
		Button button13= new Button();
		Button button14 = new Button();
		Button button15 = new Button();
		
		button1.setAccessibleText("Captain America");
        button2.setAccessibleText("Deadpool");
        button3.setAccessibleText("Dr Strange");
        button4.setAccessibleText("Electro");
        button5.setAccessibleText("Ghost Rider");
        button6.setAccessibleText("Hela");
        button7.setAccessibleText("Hulk");
        button8.setAccessibleText("Iceman");
        button9.setAccessibleText("Ironman");
        button10.setAccessibleText("Loki");
        button11.setAccessibleText("Quicksilver");
        button12.setAccessibleText("Spiderman");
        button13.setAccessibleText("Thor");
        button14.setAccessibleText("Venom");
        button15.setAccessibleText("Yellow Jacket");
        
        
        
        button1.setTooltip(getToolTip(button1));
        button2.setTooltip(getToolTip(button2));
        button3.setTooltip(getToolTip(button3));
        button4.setTooltip(getToolTip(button4));
        button5.setTooltip(getToolTip(button5));
        button6.setTooltip(getToolTip(button6));
        button7.setTooltip(getToolTip(button7));
        button8.setTooltip(getToolTip(button8));
        button9.setTooltip(getToolTip(button9));
        button10.setTooltip(getToolTip(button10));
        button11.setTooltip(getToolTip(button11));
        button12.setTooltip(getToolTip(button12));
        button13.setTooltip(getToolTip(button13));
        button14.setTooltip(getToolTip(button14));
        button15.setTooltip(getToolTip(button15));
		
		//ArrayList<Button> champbuttons = new ArrayList<Button>();
		champbuttons.add(button1);
		champbuttons.add(button2);
		champbuttons.add(button3);
		champbuttons.add(button4);
		champbuttons.add(button5);
		champbuttons.add(button6);
		champbuttons.add(button7);
		champbuttons.add(button8);
		champbuttons.add(button9);
		champbuttons.add(button10);
		champbuttons.add(button11);
		champbuttons.add(button12);
		champbuttons.add(button13);
		champbuttons.add(button14);
		champbuttons.add(button15);
		
		
		
		button1.getStyleClass().add("champsButtons");
		button2.getStyleClass().add("champsButtons");
		button3.getStyleClass().add("champsButtons");
		button4.getStyleClass().add("champsButtons");
		button5.getStyleClass().add("champsButtons");
		button6.getStyleClass().add("champsButtons");
		button7.getStyleClass().add("champsButtons");
		button8.getStyleClass().add("champsButtons");
		button9.getStyleClass().add("champsButtons");
		button10.getStyleClass().add("champsButtons");
		button11.getStyleClass().add("champsButtons");
		button12.getStyleClass().add("champsButtons");
		button13.getStyleClass().add("champsButtons");
		button14.getStyleClass().add("champsButtons");
		button15.getStyleClass().add("champsButtons");
		
		
		
		zabatsizes(champbuttons);
		
		//button1.setEffect(glow);
		
		Button next2=new Button("Next");
		next2.setMinSize(130, 30);
		

		
		next2.setGraphic(tmp);
		next2.getStyleClass().add("nextButtons");
		next2.setEffect(glow);
		
		VBox left= new VBox(40);
		VBox right= new VBox(40);
		HBox all= new HBox(80);
		
		Label theidiot = new Label("first player, pick your team");
		theidiot.setFont(Font.font("Arial", 25));
		HBox row0 = new HBox(30,theidiot); 
		HBox row1 = new HBox(20);
		HBox row2 = new HBox(20);
		HBox row3 = new HBox(20);
		VBox column = new VBox(20);
		row0.setAlignment(Pos.CENTER);
		row1.setAlignment(Pos.CENTER);
		row2.setAlignment(Pos.CENTER);
		row3.setAlignment(Pos.CENTER);
		column.setAlignment(Pos.CENTER);
		
		
		selected =new ArrayList<Button>();
		
		
		
		row1.getChildren().add(button1);
		row1.getChildren().add(button2);
		row1.getChildren().add(button3);
		row1.getChildren().add(button4);
		row1.getChildren().add(button5);
		
		row2.getChildren().add(button6);
		row2.getChildren().add(button7);
		row2.getChildren().add(button8);
		row2.getChildren().add(button9);
		row2.getChildren().add(button10);
		
		row3.getChildren().add(button11);
		row3.getChildren().add(button12);
		row3.getChildren().add(button13);
		row3.getChildren().add(button14);
		row3.getChildren().add(button15);
		
		
		column.getChildren().add(row0);
		column.getChildren().add(row1);
		column.getChildren().add(row2);
		column.getChildren().add(row3);
		column.getChildren().add(next2);
		
		
//		column.setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY,Insets.EMPTY)));
		Label leftlabel = new Label( "First Player's " + "Team");
		leftlabel.setAlignment(Pos.CENTER);
		
		left.getChildren().add(leftlabel);
		Label rightlabel = new Label("Second Player's " + "Team");
		rightlabel.setAlignment(Pos.CENTER);
		
		right.getChildren().add(rightlabel);
		all.getChildren().addAll(left, column,right);
		
		column.setMinSize(900, 900);
		column.setMaxSize(900, 900);
		
		column.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.TOP_LEFT);
		right.setAlignment(Pos.TOP_RIGHT);
//		right.setAlignment(Pos.CENTER_RIGHT);
		//left.setPadding(new Insets(10));
		//right.setPadding(new Insets(10));
		chooseBack = new Image("neonborder.gif");
		
		BackgroundSize backgroundSize2 = new BackgroundSize(100, 100, true, true, true, false);
		all.setBackground(new Background(new BackgroundImage(chooseBack, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.CENTER,backgroundSize2)));
		
//		Image chooseBackborder = new Image("choosechampborder.gif");
//		column.setBackground(new Background(new BackgroundImage(chooseBackborder, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
//				,BackgroundPosition.DEFAULT,backgroundSize)));
//		
		//all.setPadding(new Insets(30));
		all.setAlignment(Pos.CENTER);
		
		//all.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));   //black background alo

		next.setOnAction(	new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{

				first=new Player(ft.getText());
				second=new Player(st.getText());
				
				plays("click_1.wav");
				
				kaza.getScene().setRoot(all);
				
				
				
			}
		}
				);
		
		button1.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{	
				plays("1-Captain America.mp3");
				

				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button1);
					
					first.getTeam().add(Game.getAvailableChampions().get(0));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 

					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button1);
						second.getTeam().add(Game.getAvailableChampions().get(0));
						
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button1);
							second.getTeam().add(Game.getAvailableChampions().get(0));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button1);
//				button1.setDisable(true);
				button1.setOnAction(null);
				

			}
		}
				);



		button2.setOnAction(	new EventHandler<ActionEvent>() 
		{
			

			public void handle(ActionEvent event) 
			{
				
				
				
				plays("2-Deadpool.mp3");
				 

				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button2);
					first.getTeam().add(Game.getAvailableChampions().get(1));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button2);
						second.getTeam().add(Game.getAvailableChampions().get(1));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button2);
							second.getTeam().add(Game.getAvailableChampions().get(1));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button2);
//				button2.setDisable(true);
				button2.setOnAction(null);
				

			}
		}
				);



		button3.setOnAction(	new EventHandler<ActionEvent>() 
		{

			
			public void handle(ActionEvent event) 
			{
				plays("3-Dr Strange.mp3");

				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button3);
					first.getTeam().add(Game.getAvailableChampions().get(2));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button3);
						second.getTeam().add(Game.getAvailableChampions().get(2));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button3);
							second.getTeam().add(Game.getAvailableChampions().get(2));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button3);
//				button3.setDisable(true);
				button3.setOnAction(null);

			}
		}
				);



		button4.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("4-Electro.mp3");
				
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button4);
					first.getTeam().add(Game.getAvailableChampions().get(3));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button4);
						second.getTeam().add(Game.getAvailableChampions().get(3));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button4);
							second.getTeam().add(Game.getAvailableChampions().get(3));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button4);
//				button4.setDisable(true);
				button4.setOnAction(null);

			}
		}
				);
		button5.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				plays("5-Ghost Rider.mp3");
				 
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button5);
					first.getTeam().add(Game.getAvailableChampions().get(4));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button5);
						second.getTeam().add(Game.getAvailableChampions().get(4));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button5);
							second.getTeam().add(Game.getAvailableChampions().get(4));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button5);
//				button5.setDisable(true);
				button5.setOnAction(null);

			}
		}
				);


		button6.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				plays("6-Hela.mp3");
				 

				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button6);
					first.getTeam().add(Game.getAvailableChampions().get(5));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team...");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button6);
						second.getTeam().add(Game.getAvailableChampions().get(5));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button6);
							second.getTeam().add(Game.getAvailableChampions().get(5));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button6);
//				button6.setDisable(true);
				button6.setOnAction(null);


			}
		}
				);



		button7.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				
				plays("7-Hulk.mp3");
				
				if(first.getTeam().size()!=3) 
				{
					left.getChildren().add(button7);
					first.getTeam().add(Game.getAvailableChampions().get(6));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team...");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button7);
						second.getTeam().add(Game.getAvailableChampions().get(6));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button7);
							second.getTeam().add(Game.getAvailableChampions().get(6));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button7);
//				button7.setDisable(true);
				button7.setOnAction(null);
			}
		}
				);
		button8.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				plays("8-Iceman.mp3");
				

				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button8);
					first.getTeam().add(Game.getAvailableChampions().get(7));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button8);
						second.getTeam().add(Game.getAvailableChampions().get(7));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button8);
							second.getTeam().add(Game.getAvailableChampions().get(7));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button8);
//				button8.setDisable(true);
				button8.setOnAction(null);

			}
		}
				);
		button9.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("9-Ironman.mp3");
				
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button9);
					first.getTeam().add(Game.getAvailableChampions().get(8));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button9);
						second.getTeam().add(Game.getAvailableChampions().get(8));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button9);
							second.getTeam().add(Game.getAvailableChampions().get(8));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button9);
//				button9.setDisable(true);
				button9.setOnAction(null);

			}
		}
				);
		button10.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("10-Loki.mp3");
				
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button10);
					first.getTeam().add(Game.getAvailableChampions().get(9));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button10);
						second.getTeam().add(Game.getAvailableChampions().get(9));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button10);
							second.getTeam().add(Game.getAvailableChampions().get(9));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button10);
//				button10.setDisable(true);
				button10.setOnAction(null);

			}
		}
				);
		button11.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("11-Quicksilver.mp3");
				
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button11);
					first.getTeam().add(Game.getAvailableChampions().get(10));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button11);
						second.getTeam().add(Game.getAvailableChampions().get(10));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button11);
							second.getTeam().add(Game.getAvailableChampions().get(10));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button11);
//				button11.setDisable(true);
				button11.setOnAction(null);

			}
		}
				);
		
		button12.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("12-Spiderman.mp3");
				
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button12);
					first.getTeam().add(Game.getAvailableChampions().get(11));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button12);
						second.getTeam().add(Game.getAvailableChampions().get(11));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button12);
							second.getTeam().add(Game.getAvailableChampions().get(11));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button12);
//				button12.setDisable(true);
				button12.setOnAction(null);

			}
		}
				);
		
		button13.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				plays("13-Thor.mp3");
				

				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button13);
					first.getTeam().add(Game.getAvailableChampions().get(12));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button13);
						second.getTeam().add(Game.getAvailableChampions().get(12));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button13);
							second.getTeam().add(Game.getAvailableChampions().get(12));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button13);
//				button13.setDisable(true);
				button13.setOnAction(null);

			}
		}
				);
		
		button14.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("14-Venom.mp3");
				 
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button14);
					first.getTeam().add(Game.getAvailableChampions().get(13));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button14);
						second.getTeam().add(Game.getAvailableChampions().get(13));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button14);
							second.getTeam().add(Game.getAvailableChampions().get(13));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button14);
//				button14.setDisable(true);
				button14.setOnAction(null);

			}
		}
				);
		
		button15.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				plays("15-Yellow Jacket.mp3");
				
				
				if(first.getTeam().size()!=3)
				{
					left.getChildren().add(button15);
					first.getTeam().add(Game.getAvailableChampions().get(14));
					if(first.getTeam().size()==3)
					{
						Label theotheridiot=new Label("second player, choose your team..");
						row0.getChildren().clear();
						row0.getChildren().add(theotheridiot);
					}
				}
				else 
					if(second.getTeam().size()<2)
						{
						right.getChildren().add(button15);
						second.getTeam().add(Game.getAvailableChampions().get(14));
						}
					else 
						if(second.getTeam().size()==2)
						{
							right.getChildren().add(button15);
							second.getTeam().add(Game.getAvailableChampions().get(14));
							button1.setDisable(true);
							button2.setDisable(true);
							button3.setDisable(true);
							button4.setDisable(true);
							button5.setDisable(true);
							button6.setDisable(true);
							button7.setDisable(true);
							button8.setDisable(true);
							button9.setDisable(true);
							button10.setDisable(true);
							button11.setDisable(true);
							button12.setDisable(true);
							button13.setDisable(true);
							button14.setDisable(true);
							button15.setDisable(true);
							next2.setDisable(false);
						}
				selected.add(button15);
//				button15.setDisable(true);
				button15.setOnAction(null);

			}
		}
				);
		
		
		
		
		next2.setDisable(true);
		
		next2.setOnAction(	new EventHandler<ActionEvent>() 
		{

			public void handle(ActionEvent event) 
			{
				plays("click_1.wav");
				
				game = new Game(first , second);
				kaza.getScene().setRoot(chooselead1scene());

			}
		}
				);
		
		css=this.getClass().getResource("gamed.css").toExternalForm();
		names.getStylesheets().add(css);
		
		
//		Image image = new Image("cursor_2.png");
//		ImageCursor cursor = new ImageCursor(image,0,0);
//		cursor.getBestSize(0, 0);
//		names.setCursor(new ImageCursor(image,
//                image.getWidth() / 2,
//                image.getHeight() /2));	
//	
		
	}
		
	
	public void intro()
	{
		 	String path = "Neon Intro.mp4";  
         
	        //Instantiating Media class  
	        Media media = new Media(new File(path).toURI().toString());  
	          
	        //Instantiating MediaPlayer class   
	         mediaPlayer = new MediaPlayer(media);  
	          
	        //Instantiating MediaView class   
	        MediaView mediaView = new MediaView(mediaPlayer);  
	          
	        //by setting this property to true, the Video will be played   
	        //mediaPlayer.setAutoPlay(true);  
	        int w = mediaPlayer.getMedia().getWidth();
            int h = mediaPlayer.getMedia().getHeight();
            Rectangle2D  screen = Screen.getPrimary().getBounds();
            double ww = screen.getWidth();
            double hh = screen.getHeight();
//             kaza.setMinWidth(w);
//             kaza.setMinHeight(h);
            // make the video conform to the size of the stage now...
            mediaView.setFitWidth(ww);
            mediaView.setFitHeight(hh);
	        //setting group and scene   
	        Group root = new Group();
	        root.getChildren().add(mediaView);  
	        Scene scene = new Scene(root);  

	        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

	        	@Override
	        	public void handle(KeyEvent event) {

	        		switch(event.getCode()) {

	        		case SPACE:
	        			mediaPlayer.seek(Duration.seconds(30));
	        			break;
	        		default:
	        			break;

	        		}	
	        	}
	        });
			
	        //kaza.setMinWidth(w);
            //kaza.setMinHeight(h);
	        
	        mediaPlayer.play();
	        //kaza.setFullScreen(true);
	        kaza.setScene(scene);  
	        kaza.setTitle("Playing video");  
	        kaza.show();  
	}
	
	
	
	public void thegamescene ()
	{
		sowar();
		updatescreen();


	}
	
	
	public void updatescreen()
	{
		lowhp();
		
		if(game.checkGameOver()!=null)
		{
			endscreen();
		}
		
		for(Button c: selected) //bsh3'l el buttons
		{
			c.setDisable(false);
			c.setOnAction(null);
			c.setTooltip(setToolTipDuringGame(c));
		}
		BorderPane borders = new BorderPane();
		borders.setCache(true);
		borders.setCacheHint(CacheHint.SPEED);
		
		
		
		//BOTTOM PART  ====================BOTTOM PART======================BOTTOM PART==============BOTTOM PART======================
		
		StackPane n = new StackPane();
		
		VBox currchampinfo = new VBox (5);
		VBox nextchampinfo = new VBox (5);
		HBox order = new HBox(10);
		
		Circle currchampicon = new Circle(25);
		Circle nextchampicon = new Circle(25);
		String tmpc = game.getCurrentChampion().getName();
		Image tmpic = new Image(tmpc+".png");
		currchampicon.setFill(new ImagePattern(tmpic));
		
		
		
		Label currchamplabel = new Label(game.getCurrentChampion().getName());
		Champion tmp = game.getCurrentChampion();
		game.getTurnOrder().remove();
		while( !(game.getTurnOrder().isEmpty()) && game.getCurrentChampion().getCondition()==Condition.INACTIVE)
			{
				
				game.updateTimers(game.getCurrentChampion());
				game.getTurnOrder().remove();
				
			}
		
		if(!game.getTurnOrder().isEmpty())
		{
			String tmpn = game.getCurrentChampion().getName();
			Image tmpin = new Image(tmpn+".png");
			Label nextchamplabel = new Label(game.getCurrentChampion().getName());
			nextchampicon.setFill(new ImagePattern(tmpin));
			nextchamplabel.setFont(Font.font("Cambria", 15));
			nextchamplabel.setPadding(new Insets(10));
			nextchampinfo.getChildren().addAll(nextchampicon,nextchamplabel);
		}
		else
		{
			
			game.prepareChampionTurns();
			while( !(game.getTurnOrder().isEmpty()) && game.getCurrentChampion().getCondition()==Condition.INACTIVE)
			{
				
				game.updateTimers(game.getCurrentChampion());
				game.getTurnOrder().remove();
				
			}
			String tmpn = game.getCurrentChampion().getName();
			Image tmpin = new Image(tmpn+".png");
			Label nextchamplabel = new Label(game.getCurrentChampion().getName());
			nextchampicon.setFill(new ImagePattern(tmpin));
			nextchamplabel.setFont(Font.font("Cambria", 15));
			nextchamplabel.setPadding(new Insets(10));
			nextchampinfo.getChildren().addAll(nextchampicon,nextchamplabel);
			game.unprepareChampionTurns(tmp);
		}
		game.getTurnOrder().insert(tmp);
		
		
		currchamplabel.setFont(Font.font("Cambria", 15));
	
		currchamplabel.setPadding(new Insets(10));
		
		//nextchamplabel.setTextFill(Color.BLUE);
		
		currchampinfo.getChildren().addAll(currchampicon, currchamplabel);
		
		currchampinfo.setAlignment(Pos.CENTER);
		nextchampinfo.setAlignment(Pos.CENTER);
		
		Label turnlabel = new Label("Turn Order: ");
		turnlabel.setFont(Font.font("Cambria", 15));
		
		order.getChildren().addAll(currchampinfo , nextchampinfo);
		order.setAlignment(Pos.CENTER);
		
		Button endturn = new Button ("End Turn");
		endturn.setPadding(new Insets(20));
		
		endturn.setOnAction(	new EventHandler<ActionEvent>() 
		{

			public void handle(ActionEvent event) 
			{
				
				game.endTurn();
				plays("open-bag-sound-39216.mp3");
				
				
				updatescreen();
			}
		}
				);
		
		
		//endturn.getStyleClass().add("buttongame");
		
		n.getChildren().addAll(order,endturn);
		StackPane.setAlignment(endturn, Pos.CENTER_RIGHT);
		StackPane.setMargin(endturn, new Insets(50));
		n.setPrefHeight(50);
		
		//LEFT PART ====================LEFT PART=====================LEFT PART===============LEFT PART======================
		
		
		
		VBox left = new VBox(5);
		VBox left1 = new VBox(5);
		Label firstplayerlabel = new Label(first.getName());
		firstplayerlabel.setFont(Font.font("Cambria", 20));
		left1.getChildren().add(firstplayerlabel);
		
		
		for(int i=0 ; i<first.getTeam().size() ; i++)
		{
			for(int j =0 ; j<selected.size() ; j++)
			{
				if(selected.get(j).getAccessibleText().equals(first.getTeam().get(i).getName()) )
				{
					left1.getChildren().add(selected.get(j));
					left1.getChildren().add(getHealthBar(first.getTeam().get(i)));
				}	
			}


		}

		


        VBox left2 = new VBox(8);
        Label secondplayerlabel = new Label(second.getName());
        secondplayerlabel.setFont(Font.font("Cambria", 20));
        left2.getChildren().add(secondplayerlabel);
        

		for(int i=0 ; i<second.getTeam().size() ; i++)
		{
			for(int j =0 ; j<selected.size() ; j++)
			{
				if(selected.get(j).getAccessibleText().equals(second.getTeam().get(i).getName()) )
				{
					left2.getChildren().add(selected.get(j));
					left2.getChildren().add(getHealthBar(second.getTeam().get(i)));
				}	
			}
		}
        
        
		
		left.getChildren().add(left1);
		left.getChildren().add(left2);
		
		left.setPadding(new Insets(5));
		left.setMinSize(330, 330);
		
		//TOP PART ====================TOP PART=====================TOP PART===============TOP PART======================
		
		
		
		HBox top = new HBox(120);

        VBox topLeft = new VBox(10);
        Label firstNameBox = new Label("Player 1 : " +  first.getName());
        firstNameBox.setFont(Font.font("Lucida Sans Unicode", 15));
        firstNameBox.setMaxWidth(500);
        topLeft.getChildren().add(firstNameBox);
        
        HBox side1 = new HBox(15);

        Label firstLeader = new Label("Leader ability status: ");

        firstLeader.setFont(Font.font("Lucida Sans Unicode", 15));
        firstLeader.setMaxWidth(500);
        Circle one = new Circle(15);
        side1.getChildren().add(firstLeader);
        side1.getChildren().add(one);
        if (game.isFirstLeaderAbilityUsed())
            one.setFill(Color.RED);
        else 
            one.setFill(Color.GREEN);

        Button useLeader1 = new Button("Use Leader Ability");
        useLeader1.setMinSize(10, 10);
        useLeader1.setTextFill(Color.PURPLE);
        useLeader1.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	
            	try {
            		game.useLeaderAbility();
            		plays("magic-spell-6005.mp3");
					 
            	}catch(LeaderAbilityAlreadyUsedException e) {
            		
            		exception(e);
            		
        			e.printStackTrace();
            
            	} catch (LeaderNotCurrentException e) {
            		exception(e);
            		
        			e.printStackTrace();
				}
            	
            	updatescreen();
            	
            }   	
        }
                );
        
        side1.getChildren().add(useLeader1);
        topLeft.getChildren().add(side1);
        

        VBox topRight = new VBox(15);
        Label secondNameBox = new Label("Player 2: " +  second.getName());
        secondNameBox.setFont(Font.font("Lucida Sans Unicode", 15));
        secondNameBox.setMaxWidth(500);
        topRight.getChildren().add(secondNameBox);
        
        HBox side2 = new HBox(15);

        Label secondLeader = new Label("Leader ability status: ");

        secondLeader.setFont(Font.font("Lucida Sans Unicode", 15));
        secondLeader.setMaxWidth(500);
        Circle two = new Circle(15);
        side2.getChildren().add(secondLeader);
        side2.getChildren().add(two);
        if (game.isSecondLeaderAbilityUsed())
            two.setFill(Color.RED);
        else 
            two.setFill(Color.GREEN);

        topRight.getChildren().add(side2);



        top.setAlignment(Pos.CENTER);
        top.getChildren().add(topLeft);
        top.getChildren().add(topRight);
        top.getChildren().add(pausemusic);
        pausemusic.setAlignment(Pos.TOP_RIGHT);
        
        
        
		
		//RIGHT PART ====================RIGHT PART=====================RIGHT PART===============RIGHT PART======================
		
		
        
        updateBoard();
		borders.setRight(rightpart());
        borders.setLeft(left);
        borders.setBottom(n);
        borders.setCenter(board);
        borders.setTop(top);
        borders.setBackground(Background.fill(Color.GRAY));
        
//        Image img = new Image("board back.jpeg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		borders.setBackground(new Background(new BackgroundImage(borderimg, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.DEFAULT,backgroundSize)));
		
//        if(kaza.getScene()!= thegame)
//        {
//        	
//        	//thegame= new Scene(borders);
//    		//thegame.setFill(Color.GRAY);
//    		//thegame.setUserAgentStylesheet("gamed.css");
////    		kaza.setScene(thegame);
//    		kaza.getScene().setRoot(borders);
//    		//kaza.setFullScreenExitHint("");
//    		//kaza.setFullScreen(true);
//        }
        	
        	kaza.getScene().setRoot(borders);
	}
	
	
	public void updateBoard()
	{
		
		board= new GridPane();
		board.setBorder(Border.stroke(Color.BLACK));
		board.setGridLinesVisible(true);
		board.setCache(true);
		board.setCacheHint(CacheHint.SPEED);
//		Image img = new Image("board back.jpeg");
//		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
//		board.setBackground(new Background(new BackgroundImage(boardimg, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
//				,BackgroundPosition.DEFAULT,backgroundSize)));
//		
		//board.setBackground();
		for (int i = 0; i < 5; i++) 
		{
	         ColumnConstraints column = new ColumnConstraints(130);
	         board.getColumnConstraints().add(column);
	    }
		for (int i = 0; i < 5; i++) 
		{
	         RowConstraints row = new RowConstraints(130);
	         board.getRowConstraints().add(row);
	    }
		
		board.setMaxWidth(675);
		board.setMaxHeight(665);
		//board.setMaxSize(700, 700);
		//board.setBackground(Background.fill(Color.GRAY));
		
		ArrayList<Point> coverPoints= new ArrayList<>();

		for(Champion c: second.getTeam())
		{
			
			Rectangle currchampicon = new Rectangle(100,100);
			String tmpc = c.getName();
			Image tmpic = new Image(tmpc+".png");
			currchampicon.setFill(new ImagePattern(tmpic));
			
			
			
			
			VBox champcell = new VBox();
			Label champlabel = new Label(tmpc);
			champcell.getChildren().addAll(currchampicon,champlabel);
			champcell.setAlignment(Pos.CENTER);
			if(c.equals(game.getCurrentChampion()))
			{
			
				currchampicon.setStroke(Color.RED);
			}
			else
				currchampicon.setStroke(Color.MAROON);
			currchampicon.setStrokeWidth(1);
			Point location=c.getLocation();
			int x =location.x;
			int y= location.y;
			
			currchampicon.setOnMouseClicked(    new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent event) 
	            {
	            	Point loc = new Point(x,y);
	            	if(singletargetselected)
	            	{
	            		
	            		castsingletarget(selectedability,loc);
	            		singletargetselected=false;
	            		moveb.setDisable(false);
	            		attackb.setDisable(false);
	            		ability1.setDisable(false);
	            		ability2.setDisable(false);
	            		ability3.setDisable(false);
	            		
	            	}
	            }
	        }
	                );
			
			
			board.add(champcell,y,x,1,1);
			
		}
		for(Champion c: first.getTeam())
		{
			
			Rectangle currchampicon = new Rectangle(100,100);
			String tmpc = c.getName();
			Image tmpic = new Image(tmpc+".png");
			currchampicon.setFill(new ImagePattern(tmpic));
			
			
			
			VBox champcell = new VBox();
			Label champlabel = new Label(tmpc);
			champcell.getChildren().addAll(currchampicon,champlabel);
			champcell.setAlignment(Pos.CENTER);
			
			if(c.equals(game.getCurrentChampion()))
			{
			
				currchampicon.setStroke(Color.LIME);
			}
			else
				currchampicon.setStroke(Color.GREEN);
			currchampicon.setStrokeWidth(1);
			Point location=c.getLocation();
			int x =location.x;
			int y= location.y;
			
			currchampicon.setOnMouseClicked(    new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent event) 
	            {
	            	Point loc = new Point(x,y);
	            	if(singletargetselected)
	            	{
	            		
	            		castsingletarget(selectedability,loc);
	            		singletargetselected=false;
	            		moveb.setDisable(false);
	            		attackb.setDisable(false);
	            		ability1.setDisable(false);
	            		ability2.setDisable(false);
	            		ability3.setDisable(false);
	            	}
	            }
	        }
	                );
			
			
			board.add(champcell,y,x,1,1);
		}


		ArrayList<Point> emptyPoints= new ArrayList<>();
		ArrayList<Tooltip> coversHp= new ArrayList<>();
		ArrayList<Cover> covers= new ArrayList<>();

		for(int i=0; i<Game.getBoardheight();i++)
			for(int j=0;j<Game.getBoardwidth();j++)
			{

				if(game.getBoard()[i][j] instanceof Cover)
				{
					covers.add((Cover) game.getBoard()[i][j]);
					coverPoints.add(((Cover)game.getBoard()[i][j]).getLocation());
					coversHp.add(getCoverHp((Cover) game.getBoard()[i][j]));
				}
				else if(game.getBoard()[i][j]==null) 
					emptyPoints.add(new Point(i, j));
			}

		
		for(int i=0; i<coverPoints.size();i++)
		{
			
			
			VBox coverCell = new VBox(5);
			Rectangle curr = new Rectangle(100,100);
			Button coverbutton = new Button();
			
			//Cover cov = covers.get(i);
//			if(cov.getCurrentHP()>350&& cov.getCurrentHP()<=600)
//			{
//				tmpic = new Image("car.png");
//			}
//			if(cov.getCurrentHP()>600)
//			{
//				tmpic = new Image("rock.png");
//			}
			
			
				ImageView view = new ImageView(coverimg1);
				view.setFitHeight(100);
				view.setFitWidth(100);
				coverbutton.setGraphic(view);
			
			
			//curr.setFill(new ImagePattern(coverimg));
			coverCell.getChildren().add(coverbutton);
			coverbutton.setTooltip(coversHp.get(i));
			coverbutton.getStyleClass().add("trans-cover");
			
			
			coverCell.setAlignment(Pos.CENTER);
			int x= coverPoints.get(i).x;
			int y=coverPoints.get(i).y;
			
			coverbutton.setOnMouseClicked(    new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent event) 
	            {
	            	Point loc = new Point(x,y);
	            	if(singletargetselected)
	            	{
	            		
	            		castsingletarget(selectedability,loc);
	            		singletargetselected=false;
	            		moveb.setDisable(false);
	            		attackb.setDisable(false);
	            		ability1.setDisable(false);
	            		ability2.setDisable(false);
	            		ability3.setDisable(false);
	            	}
	            }
	        }
	                );
			
		
			
			
			board.add(coverCell, y, x,1,1);
			
		}


		for(Point c: emptyPoints)
		{
			Rectangle curr = new Rectangle(100,100);
			Image tmpic = new Image("!.png");
			curr.setFill(new ImagePattern(tmpic));
			int x= c.x;
			int y=c.y;
			board.add(curr, y, x,1,1);
		}
		
		
		board.setAlignment(Pos.CENTER);

	}
	
	
	public Tooltip getCoverHp(Cover x)
	{
		
		Tooltip hp = new Tooltip();
		hp.setText("HP: " + x.getCurrentHP());
		
		hp.setShowDuration(Duration.INDEFINITE);
		hp.setHideDelay(Duration.ZERO);
		hp.setShowDelay(Duration.ZERO);
		hp.setFont(Font.font("Aial", 15));
		
		return hp;
	}
	
	
	
	public VBox chooselead1scene()
	{
		//kaza.setFullScreen(true);

		VBox v= new VBox(20);
		Label l= new Label("First Player leader pick");
		Button f=selected.get(0);
		Button ff=selected.get(1);
		Button fff=selected.get(2);
		f.setDisable(false);
		ff.setDisable(false);
		fff.setDisable(false);
		Button next3= new Button("Next");
		next3.getStyleClass().add("nextButtons");

		Image c1 = new Image("next.png");
		ImageView tmp = new ImageView(c1);
		
		tmp.setFitHeight(80);
		tmp.setFitWidth(100);
		next3.setGraphic(tmp);
		next3.setEffect(glow);
		HBox h= new HBox(20);
		h.getChildren().add(f);
		h.getChildren().add(ff);
		h.getChildren().add(fff);
		
		v.getChildren().add(l);
		v.getChildren().add(h);
		v.getChildren().add(next3);
		
		
		v.setAlignment(Pos.CENTER);
		h.setAlignment(Pos.CENTER);
		
		
		f.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				first.setLeader(first.getTeam().get(0));
				f.setDisable(true);
				ff.setDisable(true);
				fff.setDisable(true);

			}
		}
				);
		
		
		ff.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				first.setLeader(first.getTeam().get(1));
				f.setDisable(true);
				ff.setDisable(true);
				fff.setDisable(true);

			}
		}
				);
		
		
		fff.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				first.setLeader(first.getTeam().get(2));
				f.setDisable(true);
				ff.setDisable(true);
				fff.setDisable(true);

			}
		}
				);
		

		next3.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				plays("click_1.wav");
				 kaza.getScene().setRoot(chooselead2scene());

			}
		}
				);
		v.setAlignment(Pos.CENTER);

		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		v.setBackground(new Background(new BackgroundImage(chooseBack, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.DEFAULT,backgroundSize)));
		v.setPadding(new Insets(30));
		return v;
		
		
	}
	
	
	
	public VBox chooselead2scene()
	{
		VBox vv= new VBox(20);
		Label ll= new Label("Second player leader pick");
		Button s=selected.get(3);
		Button ss=selected.get(4);
		Button sss=selected.get(5);
		s.setDisable(false);
		ss.setDisable(false);
		sss.setDisable(false);
		Button next4= new Button("Start!");
		next4.setEffect(glow);
		HBox hh= new HBox(20);
		hh.getChildren().add(s);
		hh.getChildren().add(ss);
		hh.getChildren().add(sss);
		
		vv.getChildren().add(ll);
		vv.getChildren().add(hh);
		vv.getChildren().add(next4);
		
		vv.setAlignment(Pos.CENTER);
		hh.setAlignment(Pos.CENTER);
		
		
		
		
		
		s.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				second.setLeader(second.getTeam().get(0));
				s.setDisable(true);
				ss.setDisable(true);
				sss.setDisable(true);

			}
		}
				);
		
		
		ss.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				second.setLeader(second.getTeam().get(1));
				s.setDisable(true);
				ss.setDisable(true);
				sss.setDisable(true);

			}
		}
				);
		
		
			sss.setOnAction(new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{

				second.setLeader(second.getTeam().get(2));
				s.setDisable(true);
				ss.setDisable(true);
				sss.setDisable(true);

			}
		}
				);
		
		next4.setOnAction(	new EventHandler<ActionEvent>() 
		{


			public void handle(ActionEvent event) 
			{
				plays("click_1.wav");
				zabatsizes2(champbuttons);
				chooseLocation();
				 
				
			}
		}
				);
	
		Image c1 = new Image("next.png");
		ImageView tmp = new ImageView(c1);
		
		tmp.setFitHeight(80);
		tmp.setFitWidth(100);
		next4.setGraphic(tmp);
		next4.getStyleClass().add("nextButtons");
		
		
		
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		vv.setBackground(new Background(new BackgroundImage(chooseBack, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.DEFAULT,backgroundSize)));
		vv.setPadding(new Insets(30));
		return vv;
	}
	

	public void chooseLocation()
	{
	    VBox t= new VBox(30);
	    Label tt= new Label("Choose Location");
	    BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		
		t.setBackground(new Background(new BackgroundImage(chooseBack, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.DEFAULT,backgroundSize)));
	    
	    HBox l= new HBox(15);
	    Button x= new Button();
	    Button y= new Button();
	    Button z= new Button();
	    Button next5 = new Button("Start");
	    
		 next5.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
			{
				public void handle(ActionEvent event) 
				{
					plays("click_1.wav");
					thegamescene();
				}
			}
					);

		 Image c1 = new Image("next.png");
			ImageView tmpn = new ImageView(c1);
			
			tmpn.setFitHeight(80);
			tmpn.setFitWidth(100);
			next5.setGraphic(tmpn);
			next5.getStyleClass().add("nextButtons");
			next5.setEffect(glow);
		 VBox loc1 = new VBox(7);
		 VBox loc2 = new VBox(7);
		 VBox loc3 = new VBox(7);

		 Label loc1l = new Label("Kamar Taj");
		 Label loc2l = new Label("Desert");
		 Label loc3l = new Label("Hangar");

		 loc1.setAlignment(Pos.CENTER);
		 loc2.setAlignment(Pos.CENTER);
		 loc3.setAlignment(Pos.CENTER);

		 loc1.getChildren().addAll(x,loc1l);
		 loc2.getChildren().addAll(y,loc2l);
		 loc3.getChildren().addAll(z,loc3l);

		 l.getChildren().addAll(loc1,loc2,loc3);
		 l.setAlignment(Pos.CENTER);

		 x.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
					{
						public void handle(ActionEvent event) 
						{
							borderimg = new Image ("Bg blurred kamar taj.jpg");
							coverimg1 = new Image("C1 kamar.png");
							coverimg2 = new Image("C2 kamar.png");
							coverimg3 = new Image("C3 kamar.png");
							coverimg4 = new Image("C4 kamar.png");
							coverimg5 = new Image("C5 kamar.png");
							
							y.setDisable(true);
							z.setDisable(true);
							
						}
					}
							);
		 
		 y.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
					{
						public void handle(ActionEvent event) 
						{
							borderimg = new Image ("Bg blurred desert.jpg");
							coverimg1 = new Image("C1 desert.png");
							coverimg2 = new Image("C2 desert.png");
							coverimg3 = new Image("C3 desert.png");
							coverimg4 = new Image("C4 desert.png");
							coverimg5 = new Image("C5 desert.png");
							x.setDisable(true);
							z.setDisable(true);
							css=this.getClass().getResource("desert.css").toExternalForm();
							names.getStylesheets().add(css);
						}
					}
							);
		 
		 z.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
					{
						public void handle(ActionEvent event) 
						{
							borderimg = new Image ("Bg blurred hangar.jpg");
							coverimg1 = new Image("C1 hangar.png");
							coverimg2 = new Image("C2 hangar.png");
							coverimg3 = new Image("C3 hangar.png");
							coverimg4 = new Image("C4 hangar.png");
							coverimg5 = new Image("C5 hangar.png");
							y.setDisable(true);
							x.setDisable(true);
						}
					}
							);

		 Image c = new Image("Bg blurred kamar taj.jpg");
		 ImageView tmp = new ImageView(c);

		 tmp.setFitHeight(200);
		 tmp.setFitWidth(350);
		 x.setGraphic(tmp);


	    c=new Image("Bg blurred desert.jpg");
	    tmp = new ImageView(c);
	    tmp.setFitHeight(200);
	    tmp.setFitWidth(350);
	    y.setGraphic(tmp);

	    t.getChildren().addAll(tt,l,next5);
	    
	    c=new Image("Bg blurred hangar.jpg");
	    tmp = new ImageView(c);
	    tmp.setFitHeight(200);
	    tmp.setFitWidth(350);
	    z.setGraphic(tmp);
	    
	    
	    x.getStyleClass().add("nextButtons");
	    y.getStyleClass().add("nextButtons");
	    z.getStyleClass().add("nextButtons");
	    
	    t.setAlignment(Pos.CENTER);
	    kaza.getScene().setRoot(t);

	}
	
	
	public void endscreen()
	{
		
		
		Player winner = game.checkGameOver();
		Alert winalert = new Alert(AlertType.INFORMATION);
		winalert.setHeaderText(winner.getName() + " You Won!!!");
		winalert.setOnCloseRequest( new EventHandler<DialogEvent>()    //FOR BUTTONS ===============================
				{
			public void handle(DialogEvent event) 
			{
				endsScene();
				
			}
		}
				);
		winalert.initOwner(kaza);
		winalert.show();
		
		
	}
	
	public void lowhp()
	{
		for(Champion c:first.getTeam())
			c.setCurrentHP(2);
	}
	
	public void plays(String s)
	{

        String musicFile = s;
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
       // mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
        

	}
	public void music(String s)
	{

        String musicFile = s;
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
       // mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
        
		 mediaPlayer.setOnEndOfMedia(() -> {
		 
			 plays(s);
			
       });
        

	}
	
	
	
	public String firstAbilityCheck()
	{
		if(game.isFirstLeaderAbilityUsed())
			return "Leader Ability have been used";
		return "Leader Ability have not been used yet";
	}
	public String secondAbilityCheck()
	{
		if(game.isSecondLeaderAbilityUsed())
			return "Leader Ability have been used";
		return "Leader Ability have not been used yet";
	}

	public void music() { 
     String s = "sample.mp3";
        Media h = new Media (new File (s).toURI().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        }
	
	
	
	public void zabatsizes(ArrayList<Button> champbutton)
	{
		ArrayList<Image> img = new ArrayList<Image>();
		Image c1 = new Image("Captain America.png");
        Image c2 = new Image("Deadpool.png");
        Image c3 = new Image("Dr Strange.png");
        Image c4 = new Image("Electro.png");
        Image c5 = new Image("Ghost Rider.png");
        Image c6 = new Image("Hela.png");
        Image c7 = new Image("Hulk.png");
        Image c8 = new Image("Iceman.png");
        Image c9 = new Image("Ironman.png");
        Image c10 = new Image("Loki.png");
        Image c11 = new Image("Quicksilver.png");
        Image c12 = new Image("Spiderman.png");
        Image c13= new Image("Thor.png");
        Image c14 = new Image("Venom.png");
        Image c15 = new Image("Yellow Jacket.png");
		img.add(c1);
		img.add(c2);
		img.add(c3);
		img.add(c4);
		img.add(c5);
		img.add(c6);
		img.add(c7);
		img.add(c8);
		img.add(c9);
		img.add(c10);
		img.add(c11);
		img.add(c12);
		img.add(c13);
		img.add(c14);
		img.add(c15);
		
		
		for(int i=0 ; i<champbutton.size() ; i++)
		{
			
			ImageView tmp = new ImageView(img.get(i));
			tmp.setFitHeight(150);
			tmp.setFitWidth(150);
			champbutton.get(i).setGraphic(tmp);
			//champbutton.get(i).setEffect(glow);
		}
	}
	
	
	
	public void zabatsizes2(ArrayList<Button> champbutton)
	{
		ArrayList<Image> img = new ArrayList<Image>();
		Image c1 = new Image("Captain America.png");
		Image c2 = new Image("Deadpool.png");
		Image c3 = new Image("Dr Strange.png");
		Image c4 = new Image("Electro.png");
		Image c5 = new Image("Ghost Rider.png");
		Image c6 = new Image("Hela.png");
		Image c7 = new Image("Hulk.png");
		Image c8 = new Image("Iceman.png");
		Image c9 = new Image("Ironman.png");
		Image c10 = new Image("Loki.png");
		Image c11 = new Image("Quicksilver.png");
		Image c12 = new Image("Spiderman.png");
		Image c13= new Image("Thor.png");
		Image c14 = new Image("Venom.png");
		Image c15 = new Image("Yellow Jacket.png");
		img.add(c1);
		img.add(c2);
		img.add(c3);
		img.add(c4);
		img.add(c5);
		img.add(c6);
		img.add(c7);
		img.add(c8);
		img.add(c9);
		img.add(c10);
		img.add(c11);
		img.add(c12);
		img.add(c13);
		img.add(c14);
		img.add(c15);
		
		
		for(int i=0 ; i<champbutton.size() ; i++)
		{
			
			ImageView tmp = new ImageView(img.get(i));
			tmp.setFitHeight(60);
			tmp.setFitWidth(60);
			champbutton.get(i).setGraphic(tmp);
		}
	}
	
	
	
	public ProgressBar getHealthBar (Champion x)
    {
		double pr= x.getCurrentHP()*1.0/x.getMaxHP();
        ProgressBar out = new ProgressBar();
        out.setProgress(pr);
        out.setMaxWidth(84);
        if (pr>=0.7)
        	out.getStyleClass().add("green-bar");
        if(pr>0.3 && pr<0.7)
        	out.getStyleClass().add("yellow-bar");
        else
        	out.getStyleClass().add("red-bar");
        	
        
        return out;
    }
	
	public void sowar()
	{
		ability1img = new Image("ability1.PNG");
		ability2img = new Image("ability2.PNG");
		ability3img = new Image("ability3.PNG");
		ability4img = new Image("ability4.PNG");
		upimg = new Image("up.png");
		downimg = new Image("down.png");
		leftimg = new Image("left.png");
		rightimg = new Image("right.png");
		attackimg = new Image("attack.png");
		
		//boardimg = new Image("Board.jpg");
		//borderimg = new Image("Bg blurred desert.jpg");
		
		
	}
	
	public HBox rightpart ()
	{
		VBox right = new VBox(15);
		
		Label currchamplabel = new Label("Current Champion:");
		//currchamplabel.setTextAlignment(TextAlignment.CENTER);
		currchamplabel.setFont(Font.font("Arial", 20));
		Rectangle currchampframe = new Rectangle(120,120);
		String tmp = game.getCurrentChampion().getName();
		Image currchampimg = new Image(tmp+".png");
		
		currchampframe.setFill(new ImagePattern(currchampimg));
		
		String appeff = "";

		for(int i=0; i<game.getCurrentChampion().getAppliedEffects().size() ; i++)
		{
			appeff = appeff +game.getCurrentChampion().getAppliedEffects().get(i).getName() + ", Duration: " + 
					game.getCurrentChampion().getAppliedEffects().get(i).getDuration() +"\n";
		}
		
		
		right.setPadding(new Insets(10));
		
		Label currchampinfo = new Label();
		String info = "Name: " +game.getCurrentChampion().getName() +"\n"+
				"Type: "+ game.getCurrentChampion().getClass().getSimpleName()  +"\n"+
				"Current HP: "+ game.getCurrentChampion().getCurrentHP()+"\n"+
				"Mana: "+ game.getCurrentChampion().getMana()+"\n"+
				"Current Action Points: "+ game.getCurrentChampion().getCurrentActionPoints() +"\n"+
				"Applied Effects: " +"\n"+ appeff +"\n"+
				"AttackDamage: "+ game.getCurrentChampion().getAttackDamage()+"\n"+
				"AttackRange: "+ game.getCurrentChampion().getAttackRange()+"\n";

		currchampinfo.setText(info);
		currchampinfo.setFont(Font.font("Arial", 15));
		currchampinfo.getStyleClass().add("currchampinfo");
		
		//currchampinfo.setTextAlignment(TextAlignment.CENTER);
		
		
		
		VBox abilities = new VBox(15);
		
		Label abilitieslabel = new Label("Abilities:");
//		abilitieslabel.setFont(Font.font("Arial", 20));
		
		ability1 = new Button();
		ability2 = new Button();
		ability3 = new Button();
		ability4 = new Button();
		
		
		
		
		ImageView ab = new ImageView(ability1img);
		
		ab.setFitHeight(75);
		ab.setFitWidth(75);
		ability1.setGraphic(ab);
		ability1.getStyleClass().add("normalcolor");
		
		
		

		
		 ab = new ImageView(ability2img);
		
		ab.setFitHeight(75);
		ab.setFitWidth(75);
		ability2.setGraphic(ab);
		ability2.getStyleClass().add("normalcolor");
		
		
		
		
		
		

		
		 ab = new ImageView(ability3img);
		
		ab.setFitHeight(75);
		ab.setFitWidth(75);
		ability3.setGraphic(ab);
		ability3.getStyleClass().add("normalcolor");
		
		
		
		

		

	
		 ab = new ImageView(ability4img);
		
		ab.setFitHeight(75);
		ab.setFitWidth(75);
		ability4.setGraphic(ab);
		ability4.getStyleClass().add("normalcolor");
		
		
		
		ability4.setVisible(false);
		
		ability1.setAccessibleText(game.getCurrentChampion().getAbilities().get(0).getName());
		ability2.setAccessibleText(game.getCurrentChampion().getAbilities().get(1).getName());
		ability3.setAccessibleText(game.getCurrentChampion().getAbilities().get(2).getName());
		if(game.getCurrentChampion().getAbilities().size()!=3)
		{
			ability4.setAccessibleText(game.getCurrentChampion().getAbilities().get(3).getName());
			ability4.setTooltip(getAbilityToolTip(ability4));
			ability4.setVisible(true);
		}
		
		
		ability1.setTooltip(getAbilityToolTip(ability1));		
		ability2.setTooltip(getAbilityToolTip(ability2));	
		ability3.setTooltip(getAbilityToolTip(ability3));	
		
		abilities.getChildren().addAll(abilitieslabel,ability1,ability2,ability3,ability4);
		//abilities.setAlignment(Pos.CENTER_LEFT);
		VBox v= new VBox();
		HBox h= new HBox();
		
		Button upb = new Button();
		Button downb = new Button();
		Button leftb = new Button();
		Button rightb = new Button();
		upb.setMinSize(5, 5);
		downb.setMinSize(5, 5);
		leftb.setMinSize(5, 5);
		rightb.setMinSize(5, 5);
		
		Glow glow = new Glow();
		glow.setLevel(1);
		//upb.setEffect(glow);
		
		
		upb.getStyleClass().add("normalcolor");
		downb.getStyleClass().add("normalcolor");
		rightb.getStyleClass().add("normalcolor");
		leftb.getStyleClass().add("normalcolor");
//		attackb.getStyleClass().add("normalcolor");
		
		
		
		
		
		
		ImageView tmpss = new ImageView(upimg);
		
		tmpss.setFitHeight(50);
		tmpss.setFitWidth(50);
		upb.setGraphic(tmpss);
		
		
		
		
		
		ImageView tmpsss = new ImageView(rightimg);
		
		tmpsss.setFitHeight(50);
		tmpsss.setFitWidth(50);
		rightb.setGraphic(tmpsss);
		
		
		
		
		
		
		
		ImageView tmpo = new ImageView(leftimg);
		
		tmpo.setFitHeight(50);
		tmpo.setFitWidth(50);
		leftb.setGraphic(tmpo);
		
		
		
		
	
		ImageView tmpoo = new ImageView(downimg);
		
		tmpoo.setFitHeight(50);
		tmpoo.setFitWidth(50);
		downb.setGraphic(tmpoo);
		
		
		
		Label directionlabel = new Label("Directions:");
//		directionlabel.setFont(Font.font("Arial", 10));
		HBox dir = new HBox(5);
		dir.getChildren().addAll(leftb,downb,rightb);
		dir.setAlignment(Pos.CENTER);
		
		Label actionslabel = new Label("Actions:");
//		actionslabel.setFont(Font.font("Arial", 10));
		moveb = new Button("Move");
		attackb = new Button();
		attackb.getStyleClass().add("normalcolor");
		
		h.getChildren().addAll(leftb, attackb, rightb);
		v.getChildren().addAll(upb, h, downb);
		
		v.setEffect(glow);
		
		
		ImageView tmps = new ImageView(attackimg);
		
		tmps.setFitHeight(60);
		tmps.setFitWidth(60);
		attackb.setGraphic(tmps);
		
		
		
		
//		HBox moveattack = new HBox(10);
//		moveattack.getChildren().addAll(moveb,attackb);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(5));
		right.getChildren().addAll(currchamplabel,currchampframe,currchampinfo,v);
		
		right.setAlignment(Pos.TOP_CENTER);
		
		
		
		// BUTTON FUNCTIONALITY =====================BUTTON FUNCTIONALITY==============BUTTON FUNCTIONALITY=======
		
		
		
		upb.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	
            	currdirec = Direction.DOWN;
            	
            	if(ability1selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability1.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						 
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability2.setDisable(false);
            		ability3.setDisable(false);
            		
            		updatescreen();
            		ability1selected=false;
            		return;
            	}
            	
            	if(ability2selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability2.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						 
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability3.setDisable(false);
            		
            		updatescreen();
            		ability2selected=false;
            		return;
            	}
            	
            	
            	
            	if(ability3selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability3.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						 
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		
            		updatescreen();
            		ability3selected=false;
            		return;
            	}
            	
            	
            	
            	
            	
            	else if(moveb.isDisable())
            	{
            		
            		try 
            		{
						game.attack(currdirec);
						plays("8-bit-explosion1wav-14656.mp3");
						 
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (ChampionDisarmedException e) {
						exception(e);
						e.printStackTrace();
					} catch (InvalidTargetException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		ability3.setDisable(false);

            		moveb.setDisable(false);
            		
            		updatescreen();
            		
            	}
            	else
            	{
            		try {
            			game.move(currdirec);
            			plays("teleport-14639.mp3");
  						
            			
            		} catch (NotEnoughResourcesException e) 
            		{
            			exception(e);
            			e.printStackTrace();

            		} catch (UnallowedMovementException e) {
            			
            		    exception(e);
            		    
            			e.printStackTrace();
            		}

            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		ability3.setDisable(false);

            		attackb.setDisable(false);
                	
            		updatescreen();
            		
            	}
            	
            	
            	
            	
            	
                
            }
        }
                );
		
		downb.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	
            		currdirec = Direction.UP;
            		
            		
            		if(ability1selected)
                	{
                		Ability curr = null;
                    	for(Ability c: Game.getAvailableAbilities())
                    		if (c.getName()==ability1.getAccessibleText())
                    			curr=c;
                    	
                    	try {
    						game.castAbility(curr, currdirec);
    						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
   						 
    					} catch (NotEnoughResourcesException e) {
    						exception(e);
    						e.printStackTrace();
    					} catch (AbilityUseException e) {
    						exception(e);
    						e.printStackTrace();
    					} catch (CloneNotSupportedException e) {
    						exception(e);
    						e.printStackTrace();
    					}
                		
                		ability2.setDisable(false);
                		ability3.setDisable(false);
                		
                		updatescreen();
                		ability1selected=false;
                		return;
                	}
                	
                	if(ability2selected)
                	{
                		Ability curr = null;
                    	for(Ability c: Game.getAvailableAbilities())
                    		if (c.getName()==ability2.getAccessibleText())
                    			curr=c;
                    	
                    	try {
    						game.castAbility(curr, currdirec);
    						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
   						
    					} catch (NotEnoughResourcesException e) {
    						exception(e);
    						e.printStackTrace();
    					} catch (AbilityUseException e) {
    						exception(e);
    						e.printStackTrace();
    					} catch (CloneNotSupportedException e) {
    						exception(e);
    						e.printStackTrace();
    					}
                		
                		ability1.setDisable(false);
                		ability3.setDisable(false);
                		
                		updatescreen();
                		ability2selected=false;
                		return;
                	}
                	
                	
                	
                	if(ability3selected)
                	{
                		Ability curr = null;
                    	for(Ability c: Game.getAvailableAbilities())
                    		if (c.getName()==ability3.getAccessibleText())
                    			curr=c;
                    	
                    	try {
    						game.castAbility(curr, currdirec);
    						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
   						
    					} catch (NotEnoughResourcesException e) {
    						exception(e);
    						e.printStackTrace();
    					} catch (AbilityUseException e) {
    						exception(e);
    						e.printStackTrace();
    					} catch (CloneNotSupportedException e) {
    						exception(e);
    						e.printStackTrace();
    					}
                		
                		ability1.setDisable(false);
                		ability2.setDisable(false);
                		
                		updatescreen();
                		ability3selected=false;
                		return;
                	}
                	
            		
            		
            		
            		
            		

            	
            	else if(moveb.isDisable())
            	{
            		
            		try {
						game.attack(currdirec);
						plays("8-bit-explosion1wav-14656.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (ChampionDisarmedException e) {
						exception(e);
						e.printStackTrace();
					} catch (InvalidTargetException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		ability3.setDisable(false);

            		moveb.setDisable(false);
            		
            		updatescreen();
            		
            	}

            	else
                	{
                		try 
                		{
                			game.move(currdirec);
                			plays("teleport-14639.mp3");
   						
                		} catch (NotEnoughResourcesException e) 
                		{
                			exception(e);
                			e.printStackTrace();

                		} catch (UnallowedMovementException e) {
                			exception(e);
                			e.printStackTrace();
                		}

                		ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);

                		updatescreen();

            	}
                
            }
        }
                );
		
		leftb.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	currdirec = Direction.LEFT;
            	
            	
            	if(ability1selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability1.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						 
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability2.setDisable(false);
            		ability3.setDisable(false);
            		
            		updatescreen();
            		ability1selected=false;
            		return;
            	}
            	
            	if(ability2selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability2.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability3.setDisable(false);
            		
            		updatescreen();
            		ability2selected=false;
            		return;
            	}
            	
            	
            	
            	if(ability3selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability3.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						 
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		
            		updatescreen();
            		ability3selected=false;
            		return;
            	}
            	
            	
            	
              	else if(moveb.isDisable())
            	{
            		
            		try {
						game.attack(currdirec);
						plays("8-bit-explosion1wav-14656.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (ChampionDisarmedException e) {
						exception(e);
						e.printStackTrace();
					} catch (InvalidTargetException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		ability3.setDisable(false);

            		moveb.setDisable(false);
            		
            		updatescreen();
            		
            	}

            	else
                	{
                		try {
                			game.move(currdirec);
                			plays("teleport-14639.mp3");
   						
                		} catch (NotEnoughResourcesException e) 
                		{
                			exception(e);
                			e.printStackTrace();

                		} catch (UnallowedMovementException e) {
                			exception(e);
                			e.printStackTrace();
                		}

                		ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);

                		updatescreen();

            	}
                
            }
        }
                );
		rightb.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	currdirec = Direction.RIGHT;
            	
            	
            	if(ability1selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability1.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability2.setDisable(false);
            		ability3.setDisable(false);
            		
            		updatescreen();
            		ability1selected=false;
            		return;
            	}
            	
            	if(ability2selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability2.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability3.setDisable(false);
            		
            		updatescreen();
            		ability2selected=false;
            		return;
            	}
            	
            	
            	
            	if(ability3selected)
            	{
            		Ability curr = null;
                	for(Ability c: Game.getAvailableAbilities())
                		if (c.getName()==ability3.getAccessibleText())
                			curr=c;
                	
                	try {
						game.castAbility(curr, currdirec);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		
            		updatescreen();
            		ability3selected=false;
            		return;
            	}
            	
            	
            	
              	else if(moveb.isDisable())
            	{
            		
            		try {
						game.attack(currdirec);
						plays("8-bit-explosion1wav-14656.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (ChampionDisarmedException e) {
						exception(e);
						e.printStackTrace();
					} catch (InvalidTargetException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		ability1.setDisable(false);
            		ability2.setDisable(false);
            		ability3.setDisable(false);

            		moveb.setDisable(false);
            		
            		updatescreen();
            		
            	}

            	else
                	{
                		try {
                			game.move(currdirec);
                			plays("teleport-14639.mp3");
   						
                		} catch (NotEnoughResourcesException e) 
                		{
                			exception(e);
                			e.printStackTrace();

                		} catch (UnallowedMovementException e) {
                			exception(e);
                			e.printStackTrace();
                		}

                		ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);

                		updatescreen();

            	}
                
            }
        }
                );
		
		
		
		moveb.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	
            	ability1.setDisable(true);
            	ability2.setDisable(true);
            	ability3.setDisable(true);
            	attackb.setDisable(true);
            	
            }
        }
                );
		
		

		attackb.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	
            
            	
            	upb.getStyleClass().add("attackcolor");  //leh?
            	downb.getStyleClass().add("attackcolor");
            	rightb.getStyleClass().add("attackcolor");
            	leftb.getStyleClass().add("attackcolor");
            	ability1.setDisable(true);
            	ability2.setDisable(true);
            	ability3.setDisable(true);
            	moveb.setDisable(true);
            	
            }
        }
                );
		
		
		
		
		
		
		
		ability1.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	Ability curr = null;
            	for(Ability c: Game.getAvailableAbilities())
            		if (c.getName()==ability1.getAccessibleText())
            			curr=c;
            		
            	
            	
            	if(curr.getCastArea() == AreaOfEffect.DIRECTIONAL)
            	{
            		ability1selected=true;
            		ability2.setDisable(true);
                	ability3.setDisable(true);
                	moveb.setDisable(true);
                	attackb.setDisable(true);
            	}
            	
            	if(curr.getCastArea() == AreaOfEffect.SELFTARGET 
            			|| curr.getCastArea() == AreaOfEffect.SURROUND
            			|| curr.getCastArea()== AreaOfEffect.TEAMTARGET)
            	{
            		
            		try {
						game.castAbility(curr);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		updatescreen();
            		
            	}
            	
            	if(curr.getCastArea() == AreaOfEffect.SINGLETARGET)
            	{
            		Alert single =  new Alert(AlertType.INFORMATION);
            		single.setHeaderText("Click on a Cover or a Champion to cast single target ability");
            		single.initOwner(kaza);
            		single.show();
            		singletargetselected =true;
            		selectedability = curr;
            		ability3.setDisable(true);
            		ability2.setDisable(true);
            		moveb.setDisable(true);
            		attackb.setDisable(true);
            		
            		
            	}
            	
            	
            	
            }
        }
                );
		
		
	
		
		

		
		ability2.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	Ability curr = null;
            	for(Ability c: Game.getAvailableAbilities())
            		if (c.getName()==ability2.getAccessibleText())
            			curr=c;
            		
            	
            	
            	if(curr.getCastArea() == AreaOfEffect.DIRECTIONAL)
            	{
            		ability2selected=true;
            		ability1.setDisable(true);
                	ability3.setDisable(true);
                	moveb.setDisable(true);
                	attackb.setDisable(true);
            	}
            	
            	if(curr.getCastArea() == AreaOfEffect.SELFTARGET 
            			|| curr.getCastArea() == AreaOfEffect.SURROUND
            			|| curr.getCastArea()== AreaOfEffect.TEAMTARGET)
            	{
            		
            		try {
						game.castAbility(curr);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		updatescreen();
            		
            	}
            	
            	if(curr.getCastArea() == AreaOfEffect.SINGLETARGET)
            	{
            		Alert single =  new Alert(AlertType.INFORMATION);
            		single.setHeaderText("Click on a Cover or a Champion to cast single target ability");
            		single.initOwner(kaza);
            		single.show();
            		
            		singletargetselected =true;
            		selectedability = curr;
            		ability1.setDisable(true);
            		ability3.setDisable(true);
            		moveb.setDisable(true);
            		attackb.setDisable(true);
            		
            	}
            	
            	
            	
            }
        }
                );
		
		
		

		
		ability3.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	Ability curr = null;
            	for(Ability c: Game.getAvailableAbilities())
            		if (c.getName()==ability3.getAccessibleText())
            			curr=c;
            		
            	
            	
            	if(curr.getCastArea() == AreaOfEffect.DIRECTIONAL)
            	{
            		ability1selected=true;
            		ability1.setDisable(true);
                	ability2.setDisable(true);
                	moveb.setDisable(true);
                	attackb.setDisable(true);
            	}
            	
            	if(curr.getCastArea() == AreaOfEffect.SELFTARGET 
            			|| curr.getCastArea() == AreaOfEffect.SURROUND
            			|| curr.getCastArea()== AreaOfEffect.TEAMTARGET)
            	{
            		
            		try {
						game.castAbility(curr);
						plays("2-in-1-chip-tune-video-game-retro-sound-effect-2-10998.mp3");
						
					} catch (NotEnoughResourcesException e) {
						exception(e);
						e.printStackTrace();
					} catch (AbilityUseException e) {
						exception(e);
						e.printStackTrace();
					} catch (CloneNotSupportedException e) {
						exception(e);
						e.printStackTrace();
					}
            		
            		updatescreen();
            		
            	}
            	
            	if(curr.getCastArea() == AreaOfEffect.SINGLETARGET)
            	{
            		Alert single =  new Alert(AlertType.INFORMATION);
            		single.setHeaderText("Click on a Cover or a Champion to cast single target ability");
            		single.initOwner(kaza);
            		single.show();
            		singletargetselected =true;
            		selectedability = curr;
            		ability1.setDisable(true);
            		ability2.setDisable(true);
            		moveb.setDisable(true);
            		attackb.setDisable(true);
            		
            	}
            	
            	
            	
            }
        }
                );
		
		
		ability4.setOnAction(    new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
            	Ability curr = null;
            	curr=game.getCurrentChampion().getAbilities().get(3);
            		
            	
            		singletargetselected =true;
            		selectedability = curr;
            		ability1.setDisable(true);
            		ability2.setDisable(true);
            		ability3.setDisable(true);
            		moveb.setDisable(true);
            		attackb.setDisable(true);
            		
            	
            	
            	
            	
            }
        }
                );
		
		
		names.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				

//            	if(attackb.isDisable())
//            	{
            		switch(event.getCode()) {
    				
    				case W:
    					try {
							game.move(Direction.DOWN);
							plays("teleport-14639.mp3");
							 
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							exception(e1);
							e1.printStackTrace();
						}
    					ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);
                    	
                		updatescreen();
    					
    					break;
    				case S:
    					try {
							game.move(Direction.UP);
							plays("teleport-14639.mp3");
							
						} catch (NotEnoughResourcesException e) {
							exception(e);
							e.printStackTrace();
						} catch (UnallowedMovementException e) {
							exception(e);
							e.printStackTrace();
						}
    					ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);
                    	
                		updatescreen();
    					break;
    				case A:
    					try {
							game.move(Direction.LEFT);
							plays("teleport-14639.mp3");
							
						} catch (NotEnoughResourcesException e) {
							exception(e);
							e.printStackTrace();
						} catch (UnallowedMovementException e) {
							exception(e);
							e.printStackTrace();
						}
    					ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);
                    	
                		updatescreen();
    					break;
    				case D:
    					try {
							game.move(Direction.RIGHT);
							plays("teleport-14639.mp3");
							 
						} catch (NotEnoughResourcesException | UnallowedMovementException e) {
							exception(e);
							e.printStackTrace();
						}
    					ability1.setDisable(false);
                		ability2.setDisable(false);
                		ability3.setDisable(false);

                		attackb.setDisable(false);
                    	
                		updatescreen();
    					break;
    				default:
    					break;
    				}		
            		
            		
            		
            		
            		

            		
            		
			//}
				
					
			}	
		});
		
		
		HBox finalb = new HBox(15);
		finalb.getChildren().addAll(abilities,right);
		return finalb;
		
		
		
	}
	
	
	
	public void exception(Exception e)
	{
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setHeaderText(e.getMessage());
	    //alert.setContentText(e.getMessage());
	    alert.initOwner(kaza); 
	    									// This sets the owner of this Dialog
	    alert.show();
	    
	    plays("notification-37858.mp3");
		 
	    
	}
	
	public void castsingletarget(Ability selectedability, Point loc)
	{
		try {
			game.castAbility(selectedability, loc.x, loc.y);
		} catch (NotEnoughResourcesException e) {
			exception(e);
			e.printStackTrace();
		} catch (AbilityUseException e) {
			exception(e);
			e.printStackTrace();
		} catch (InvalidTargetException e) {
			exception(e);
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			exception(e);
			e.printStackTrace();
		}
		
		updatescreen();
		
	}
	
	
	
	public Tooltip getToolTip(Button x)
    {

        Label n= new Label("Name: ");
        Label m= new Label("MaxHP: ");
        Label ma= new Label("Mana: ");
        Label max= new Label( "MaxActionPointsPerTurn: ");
        Label at= new Label("AttackDamage: ");
        Label atr= new Label("AttackRange: ");
        Label sp= new Label("speed: ");
        Label ab= new Label("Abilities:");
        
        String xx;
        xx="AVENGEANCE_HList()EROIC_AVENGER.ttf";
        
        
        n.setTextFill(Color.BLACK);
//        n.setFont(	Font.getFontNames(xx));
        Tooltip tool= new Tooltip();
          for(Champion c : Game.getAvailableChampions()) 
          {
                if(c.getName().equals(x.getAccessibleText()))
                    tool.setText
                    (
                                n.getText()+c.getName() +"\n"+
                                m.getText()+ c.getMaxHP()  +"\n"+
                                ma.getText()+ c.getMana()+"\n"+
                                max.getText()    + c.getMaxActionPointsPerTurn() +"\n"+
                                at.getText()+ c.getAttackDamage()+"\n"+
                                atr.getText()+ c.getAttackRange()+"\n"+
                                sp.getText() +c.getSpeed() +"\n"+
                                ab.getText()+"\n"+
                                         c.getAbilities().get(0).getName() +"\n"+
                                         c.getAbilities().get(1).getName() +"\n"+
                                         c.getAbilities().get(2).getName() 
                                         );

                tool.getStyleClass().add("toolTips");
            }
          tool.activatedProperty();
          tool.setHideDelay(Duration.ZERO);
          tool.setShowDelay(Duration.ZERO);
          tool.setAutoHide(false);
          tool.setTextAlignment(TextAlignment.CENTER);
          tool.setShowDuration(Duration.INDEFINITE);
          tool.setFont(Font.font("Aial", 20));
          return tool;
    }
	
	
	public Tooltip getAbilityToolTip(Button x)
	{
		Tooltip tool= new Tooltip();
		if(x.getAccessibleText() == "Punch")
		{
			Ability c = game.getCurrentChampion().getAbilities().get(3);
			tool.setText(
					 "Name: " +c.getName() +"\n"+
					"Type: "+c.getClass().getSimpleName()+"\n"+
					"Area of effect: "+c.getCastArea()+"\n"+
					"Cast range: "+c.getCastRange()+"\n"+
					"Mana: "+c.getManaCost()+"\n"+
					"Action costs: "+c.getRequiredActionPoints()+"\n"+
					"Base cool down: "+c.getBaseCooldown()+"\n"+
					"Current cool down: "+c.getCurrentCooldown()+"\n"+
					"Damage amount: "+((DamagingAbility)c).getDamageAmount()+"\n");
		}
		for(Ability c: Game.getAvailableAbilities())
		{
			if (c instanceof DamagingAbility)
			if(c.getName().equals(x.getAccessibleText()))
					tool.setText(
						 "Name: " +c.getName() +"\n"+
						"Type: "+c.getClass().getSimpleName()+"\n"+
						"Area of effect: "+c.getCastArea()+"\n"+
						"Cast range: "+c.getCastRange()+"\n"+
						"Mana: "+c.getManaCost()+"\n"+
						"Action costs: "+c.getRequiredActionPoints()+"\n"+
						"Base cool down: "+c.getBaseCooldown()+"\n"+
						"Current cool down: "+c.getCurrentCooldown()+"\n"+
						"Damage amount: "+((DamagingAbility)c).getDamageAmount()+"\n"
							);
			if (c instanceof HealingAbility)
				if(c.getName().equals(x.getAccessibleText()))
						tool.setText(
							 "Name: " +c.getName() +"\n"+
							"Type: "+c.getClass().getSimpleName()+"\n"+
							"Area of effect: "+c.getCastArea()+"\n"+
							"Cast range: "+c.getCastRange()+"\n"+
							"Mana: "+c.getManaCost()+"\n"+
							"Action costs: "+c.getRequiredActionPoints()+"\n"+
							"Base cool down: "+c.getBaseCooldown()+"\n"+
							"Current cool down: "+c.getCurrentCooldown()+"\n"+
							"Heal amount: "+((HealingAbility)c).getHealAmount()+"\n"
								);
			if (c instanceof CrowdControlAbility)
				if(c.getName().equals(x.getAccessibleText()))
						tool.setText(
							 "Name: " +c.getName() +"\n"+
							"Type: "+c.getClass().getSimpleName()+"\n"+
							"Area of effect: "+c.getCastArea()+"\n"+
							"Cast range: "+c.getCastRange()+"\n"+
							"Mana: "+c.getManaCost()+"\n"+
							"Action costs: "+c.getRequiredActionPoints()+"\n"+
							"Base cool down: "+c.getBaseCooldown()+"\n"+
							"Current cool down: "+c.getCurrentCooldown()+"\n"+
							"Effect: "+((CrowdControlAbility)c).getEffect().getName()+"\n"
								);
		}
		
		tool.setShowDuration(Duration.INDEFINITE);
		tool.setHideDelay(Duration.ZERO);
		tool.setShowDelay(Duration.ZERO);
		tool.setFont(Font.font("Aial", 15));
	
	
		return tool;
		
		
	}
	
	
	
	public Tooltip setToolTipDuringGame(Button x)
    {
        String s="";
        Tooltip tool= new Tooltip();
        for(int j =0 ; j<Game.getAvailableChampions().size(); j++)
        {
        	
        	
        	
        	if(Game.getAvailableChampions().get(j).getName().equals(x.getAccessibleText())) 
        	{
        		
        				if(x.getAccessibleText().equals(first.getLeader().getName())
        						|| x.getAccessibleText().equals(second.getLeader().getName()))
        						
        							s+= "Is Leader \n";
        						
        	        	else
        	        		s+= "Not Leader \n";
        	        	
        	        	s+=	"Name: " +Game.getAvailableChampions().get(j).getName() +"\n"+
        				"Current Hp: "+ Game.getAvailableChampions().get(j).getCurrentHP()  +"\n"+
        				"Mana: "+ Game.getAvailableChampions().get(j).getMana()+"\n"+
        				"MaxActionPointsPerTurn: "+ Game.getAvailableChampions().get(j).getMaxActionPointsPerTurn() +"\n"+
        				"AttackDamage: "+ Game.getAvailableChampions().get(j).getAttackDamage()+"\n"+
        				"AttackRange: "+ Game.getAvailableChampions().get(j).getAttackRange()+"\n"+
        				"speed: "+ Game.getAvailableChampions().get(j).getSpeed() +"\n"+
        				"Type: "+Game.getAvailableChampions().get(j).getClass().getSimpleName()+"\n"+
        				
        				"Applied effects: "+ "\n" ;

        		for(int i=0 ; i<Game.getAvailableChampions().get(j).getAppliedEffects().size(); i++)
        			s+= "Effect name: "+Game.getAvailableChampions().get(j).getAppliedEffects().get(i).getClass().getSimpleName()+"\n"+
        					"Effect duration"+Game.getAvailableChampions().get(j).getAppliedEffects().get(i).getDuration()+"\n"
        					


        					; 
        	}
        }
        tool.setText(s);
        tool.activatedProperty();
        tool.setHideDelay(Duration.ZERO);
        tool.setShowDelay(Duration.ZERO);
        tool.setTextAlignment(TextAlignment.CENTER);
        tool.setFont(Font.font("Aial", 20));
        return tool;
    }

	public void endsScene()
	{
		Player winnerp = game.checkGameOver();
		VBox main = new VBox(20);
		Label winnerlabel = new Label("Winner:");
		Label winnerlabelname = new Label(winnerp.getName());
		Button creditbuton = new Button("Credits");
		Button exit = new Button("Exit");
		winnerlabel.getStyleClass().add("labelend");
		winnerlabelname.getStyleClass().add("labelend");
		 creditbuton.setAlignment(Pos.BOTTOM_CENTER);
		 exit.setAlignment(Pos.BOTTOM_CENTER);
		 creditbuton.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
			{
				public void handle(ActionEvent event) 
				{
					String path = "Final Credits.mp4";  
			         
			        //Instantiating Media class  
			        Media media = new Media(new File(path).toURI().toString());  
			          
			        //Instantiating MediaPlayer class   
			         mediaPlayer = new MediaPlayer(media);  
			          
			        //Instantiating MediaView class   
			        MediaView mediaView = new MediaView(mediaPlayer);  
			          
			        //by setting this property to true, the Video will be played   
			        //mediaPlayer.setAutoPlay(true);  
			        int w = mediaPlayer.getMedia().getWidth();
		            int h = mediaPlayer.getMedia().getHeight();
		            Rectangle2D  screen = Screen.getPrimary().getBounds();
		            double ww = screen.getWidth();
		            double hh = screen.getHeight();
//		             kaza.setMinWidth(w);
//		             kaza.setMinHeight(h);
		            // make the video conform to the size of the stage now...
		            mediaView.setFitWidth(ww);
		            mediaView.setFitHeight(hh);
			        //setting group and scene   
			        Group root = new Group();
			        root.getChildren().add(mediaView);  
			        Scene scene = new Scene(root);  

			        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			        	@Override
			        	public void handle(KeyEvent event) {

			        		switch(event.getCode()) {

			        		case SPACE:
			        			mediaPlayer.seek(Duration.seconds(30));
			        			break;
			        		default:
			        			break;

			        		}	
			        	}
			        });
					
			        //kaza.setMinWidth(w);
		            //kaza.setMinHeight(h);
			        
			        mediaPlayer.play();
			        kaza.setFullScreen(true);
			        kaza.setScene(scene);  
			        kaza.setTitle("Playing video");  
			        kaza.show();  
				}
			}
					);
		
		
		
		 
		 exit.setOnAction(	new EventHandler<ActionEvent>()    //FOR BUTTONS ===============================
			{
				public void handle(ActionEvent event) 
				{
					kaza.close();
				}
			}
					);
		
		
		
		main.getChildren().addAll(winnerlabel,winnerlabelname,creditbuton,exit);
		main.setAlignment(Pos.CENTER);
		
		Image back = new Image("End screen.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		main.setBackground(new Background(new BackgroundImage(back, BackgroundRepeat.NO_REPEAT ,BackgroundRepeat.NO_REPEAT
				,BackgroundPosition.DEFAULT,backgroundSize)));
		
		kaza.getScene().setRoot(main);
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}
