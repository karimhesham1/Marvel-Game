package engine;

import java.awt.Point;
import model.world.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.math.*;
import exceptions.*;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Disarm;
import model.effects.Dodge;
import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;
import model.effects.PowerUp;
import model.effects.Root;
import model.effects.Shield;
import model.effects.Shock;
import model.effects.Silence;
import model.effects.SpeedUp;
import model.effects.Stun;

public class Game {
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private Player firstPlayer;
	private Player secondPlayer;
	private Object[][] board;
	private PriorityQueue turnOrder;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private final static int BOARDWIDTH = 5;
	private final static int BOARDHEIGHT = 5;

	public Game(Player first, Player second) {
		firstPlayer = first;
		secondPlayer = second;
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		board = new Object[BOARDWIDTH][BOARDHEIGHT];
		turnOrder = new PriorityQueue(6);
		placeChampions();
		placeCovers();
		prepareChampionTurns();
		
		
	}
	

	public static void loadAbilities(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Ability a = null;
			AreaOfEffect ar = null;
			switch (content[5]) {
			case "SINGLETARGET":
				ar = AreaOfEffect.SINGLETARGET;
				break;
			case "TEAMTARGET":
				ar = AreaOfEffect.TEAMTARGET;
				break;
			case "SURROUND":
				ar = AreaOfEffect.SURROUND;
				break;
			case "DIRECTIONAL":
				ar = AreaOfEffect.DIRECTIONAL;
				break;
			case "SELFTARGET":
				ar = AreaOfEffect.SELFTARGET;
				break;

			}
			Effect e = null;
			if (content[0].equals("CC")) {
				switch (content[7]) {
				case "Disarm":
					e = new Disarm(Integer.parseInt(content[8]));
					break;
				case "Dodge":
					e = new Dodge(Integer.parseInt(content[8]));
					break;
				case "Embrace":
					e = new Embrace(Integer.parseInt(content[8]));
					break;
				case "PowerUp":
					e = new PowerUp(Integer.parseInt(content[8]));
					break;
				case "Root":
					e = new Root(Integer.parseInt(content[8]));
					break;
				case "Shield":
					e = new Shield(Integer.parseInt(content[8]));
					break;
				case "Shock":
					e = new Shock(Integer.parseInt(content[8]));
					break;
				case "Silence":
					e = new Silence(Integer.parseInt(content[8]));
					break;
				case "SpeedUp":
					e = new SpeedUp(Integer.parseInt(content[8]));
					break;
				case "Stun":
					e = new Stun(Integer.parseInt(content[8]));
					break;
				}
			}
			switch (content[0]) {
			case "CC":
				a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
				break;
			case "DMG":
				a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			case "HEL":
				a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
				break;
			}
			availableAbilities.add(a);
			line = br.readLine();
		}
		br.close();
	}

	public static void loadChampions(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Champion c = null;
			switch (content[0]) {
			case "A":
				c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;

			case "H":
				c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			case "V":
				c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
						Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
						Integer.parseInt(content[7]));
				break;
			}

			c.getAbilities().add(findAbilityByName(content[8]));
			c.getAbilities().add(findAbilityByName(content[9]));
			c.getAbilities().add(findAbilityByName(content[10]));
			availableChampions.add(c);
			line = br.readLine();
		}
		br.close();
	}

	private static Ability findAbilityByName(String name) {
		for (Ability a : availableAbilities) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	public void placeCovers() {
		int i = 0;
		while (i < 5) {
			int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
			int y = (int) (Math.random() * BOARDHEIGHT);

			if (board[x][y] == null) {
				board[x][y] = new Cover(x, y);
				i++;
			}
		}

	}

	public void placeChampions() {
		int i = 1;
		for (Champion c : firstPlayer.getTeam()) {
			board[0][i] = c;
			c.setLocation(new Point(0, i));
			i++;
		}
		i = 1;
		for (Champion c : secondPlayer.getTeam()) {
			board[BOARDHEIGHT - 1][i] = c;
			c.setLocation(new Point(BOARDHEIGHT - 1, i));
			i++;
		}
	
	}


	public Champion getCurrentChampion()
	
	{
		
		return (Champion) turnOrder.peekMin();
		
	}
	
	private void prepareChampionTurns()
	{
		for(int i = 0 ; i<firstPlayer.getTeam().size() ; i++)
		{
			if(firstPlayer.getTeam().get(i).getCurrentHP()!=0)
			{
				turnOrder.insert(firstPlayer.getTeam().get(i));
			}
		}
		
		for(int i = 0 ; i<secondPlayer.getTeam().size() ; i++)
		{
			if(secondPlayer.getTeam().get(i).getCurrentHP()!=0)
			{
				turnOrder.insert(secondPlayer.getTeam().get(i));
			}
		}
	
	}
	
	
	
	public Player checkGameOver()
	{
		int sumf =0;
		int sums =0;
		for(int i=0 ; i<firstPlayer.getTeam().size() ; i++)
		{
			sumf = sumf +firstPlayer.getTeam().get(i).getCurrentHP();
		}
		
		for(int i=0 ; i<secondPlayer.getTeam().size() ; i++)
		{
			sums = sums +secondPlayer.getTeam().get(i).getCurrentHP();
		}
		
		if(sumf ==0)
		{
			return secondPlayer;
		}
		
		else if(sums== 0)
		{
			return firstPlayer;
		}
		else
			return null;
		

	}
	
	public void move(Direction d) throws UnallowedMovementException, NotEnoughResourcesException
	{
		
		if (getCurrentChampion().getCondition()== Condition.ROOTED)
			throw new UnallowedMovementException();
		
		if(getCurrentChampion().getCurrentActionPoints()<1)
			throw new NotEnoughResourcesException();

		int curx = getCurrentChampion().getLocation().x;
		int cury = getCurrentChampion().getLocation().y;
		if(d == Direction.DOWN)
			curx--;
		
		if(d == Direction.UP)
			curx++;
		
		
		if(d == Direction.LEFT)
			cury--;
		
		if(d == Direction.RIGHT)
			cury++;
		
		
		if (curx<0 || cury<0 || curx>4 || cury>4)
			throw new UnallowedMovementException();
		
		if( (board[curx][cury]!=null) )
			throw new UnallowedMovementException();
		
		
		board[getCurrentChampion().getLocation().x][getCurrentChampion().getLocation().y] = null;
		board[curx][cury] = getCurrentChampion(); 
		getCurrentChampion().setLocation(new Point(curx,cury));
		getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-1);
	
	}
	
	
	public void attack(Direction d) throws NotEnoughResourcesException, ChampionDisarmedException 
	{
		
		for(int i=0 ; i<getCurrentChampion().getAppliedEffects().size() ; i++)
		{
			if(getCurrentChampion().getAppliedEffects().get(i) instanceof Disarm)
				throw new ChampionDisarmedException();
		}
		
		if(getCurrentChampion().getCurrentActionPoints()<2)
			throw new NotEnoughResourcesException();
		
		
		
		int range = getCurrentChampion().getAttackRange();
		Point tmp = getCurrentChampion().getLocation();
		Champion champ;
		Cover cov;
		int xtmp = 0;
		int ytmp = 0;
		
		Damageable target=null;
		boolean flags=false;	
		boolean flagf=false;
		
		for (int i=0; i<firstPlayer.getTeam().size();i++)
		{
			if (firstPlayer.getTeam().get(i) == getCurrentChampion())
				flagf=true;
			
		}
		
		for (int i=0; i<secondPlayer.getTeam().size();i++)
		{
			if 	(secondPlayer.getTeam().get(i) == getCurrentChampion())
				flags=true;
		}
		
		if(d == Direction.UP)
			xtmp =1;
		if(d == Direction.DOWN)
			xtmp = -1;
		if(d == Direction.LEFT)
			ytmp = -1;
		if(d == Direction.RIGHT)
			ytmp = 1;
		
	
			
			for(int i=0 ; i<range ; i++)
			{
				tmp.x = tmp.x+xtmp;
				tmp.y = tmp.y+ytmp;
				if(tmp.x>4 || tmp.x<0 || tmp.y<0 || tmp.y>4)
				{
					
				    return;
				}
				
				if(board[tmp.x] [tmp.y]!=null) 
				{
					if( (flagf) && (board[tmp.x] [tmp.y] instanceof Champion) &&  (secondPlayer.getTeam().contains(board[tmp.x] [tmp.y])) )
					{
						target=(Damageable) board[tmp.x] [tmp.y];
						break;
					}

					if( (flags) && (board[tmp.x] [tmp.y] instanceof Champion) &&  (firstPlayer.getTeam().contains(board[tmp.x] [tmp.y])) )
					{
						target=(Damageable) board[tmp.x] [tmp.y];
						break;
					}
					
					if(board[tmp.x] [tmp.y] instanceof Cover )
					{
						target=(Damageable) board[tmp.x] [tmp.y];
						break;
					}
					
				}
				
			
			}
			getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-2);
			

			if(target instanceof Champion)
			{
				champ=(Champion)target;
				for(int j=0 ; j<champ.getAppliedEffects().size() ; j++)
				{
					if (champ.getAppliedEffects().get(j) instanceof Dodge)
					{
//						champ.getAppliedEffects().get(j).remove(champ);
//						champ.getAppliedEffects().remove(j);
						
						
						int random = (int) (Math.random()*1001)+0;                               //generate num 1 or 2, 50chance
						if(random %2 ==0)     
						{
							
							return;                                                     
						}
						else
						break;
						
					}
				}
				
				
				for(int j=0 ; j<champ.getAppliedEffects().size() ; j++)
				{
					if (champ.getAppliedEffects().get(j) instanceof Shield)
					{
						
						champ.getAppliedEffects().get(j).remove(champ);
						champ.getAppliedEffects().remove(j);                                  //shalet 2 fails
						return;
					}

				}
				
				if ( (getCurrentChampion() instanceof Hero && champ instanceof Villain) || 
						(getCurrentChampion() instanceof Villain && champ instanceof Hero) ||
						(getCurrentChampion() instanceof AntiHero && (!(champ instanceof AntiHero))) ||
						(!(getCurrentChampion() instanceof AntiHero))&& (champ instanceof AntiHero))    //not momken bayza
				{
					
					int dmg =(int)(getCurrentChampion().getAttackDamage()*1.5);
					int dmgg= champ.getCurrentHP()-dmg;
					champ.setCurrentHP(dmgg);
					
					
					if (champ.getCurrentHP() == 0)
					{

						firstPlayer.getTeam().remove(champ);
						secondPlayer.getTeam().remove(champ);
						champ.setCondition(Condition.KNOCKEDOUT);
						champ.setLocation(null);
						board [tmp.x][tmp.y] = null;
						helpelem();


					}
					
					return;
				
				}
				
				
				
				else
				{
					int dmg = (getCurrentChampion().getAttackDamage());

					champ.setCurrentHP(champ.getCurrentHP()-dmg);
					

					if (champ.getCurrentHP() == 0)
					{

						firstPlayer.getTeam().remove(champ);
						secondPlayer.getTeam().remove(champ);
						champ.setCondition(Condition.KNOCKEDOUT);
						champ.setLocation(null);
						board [tmp.x][tmp.y] = null;
						helpelem();


					}

					return;

				}

			}
			

			else if(target instanceof Cover)
			{
				Cover covv = (Cover) target;
				covv.setCurrentHP(covv.getCurrentHP()-getCurrentChampion().getAttackDamage());
				
				if(covv.getCurrentHP()==0)
				{
					board[tmp.x][tmp.y]=null;

				}
				return;

			}

	}

	

	
	public void castAbility(Ability a) throws NotEnoughResourcesException, AbilityUseException, InvalidTargetException, CloneNotSupportedException
	{

		if(a.getCurrentCooldown()>0)
			throw new AbilityUseException();
		if(getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints()||
				getCurrentChampion().getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();
		
		for(int i=0;i<getCurrentChampion().getAppliedEffects().size();i++)
			if (getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException();
		
		boolean flags=false;	
		boolean flagf=false;	
		for (int i=0; i<firstPlayer.getTeam().size();i++)
		{
			if (firstPlayer.getTeam().get(i) == getCurrentChampion())
				flagf=true;
			
		}
		
		for (int i=0; i<secondPlayer.getTeam().size();i++)
		{
			if 	(secondPlayer.getTeam().get(i) == getCurrentChampion())
				flags=true;
		}
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		if (a.getCastArea() == AreaOfEffect.TEAMTARGET)
		{
			
			if(a instanceof HealingAbility )
			{
				for(int i=0 ; i<firstPlayer.getTeam().size() && flagf ; i++)
				{
					targets.add(firstPlayer.getTeam().get(i));
				}
				
				for(int i=0 ; i<secondPlayer.getTeam().size() && flags ; i++)
				{
					targets.add(secondPlayer.getTeam().get(i));
				}
				
				int dist=0;
				for(int i=0 ; i<targets.size() ; i++)
				{
					dist= getmanhattan(getCurrentChampion().getLocation(), targets.get(i).getLocation());
					if(dist>a.getCastRange())
					{
						targets.remove(i);
						i--;
					}	
					
				}
				
				a.execute(targets);
				getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
				getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
				
			}
			
			if(a instanceof DamagingAbility )
			{
				for(int i=0 ; i<firstPlayer.getTeam().size() && flags ; i++)
				{
					targets.add(firstPlayer.getTeam().get(i));
				}
				
				for(int i=0 ; i<secondPlayer.getTeam().size() && flagf ; i++)
				{
					targets.add(secondPlayer.getTeam().get(i));
				}
				
				int dist=0;
				for(int i=0 ; i<targets.size() ; i++)
				{
					dist= getmanhattan(getCurrentChampion().getLocation(), targets.get(i).getLocation());       //range
					if(dist>a.getCastRange())
					{
						targets.remove(i);
						i--;
					}
						
					
				}
				
				for(int z=0; z < (targets.size()) ; z++)
				{
					Champion zft = (Champion) targets.get(z);

					for(int k =0 ; k<zft.getAppliedEffects().size() ; k++)
					{
						if(zft.getAppliedEffects().get(k) instanceof Shield)                   //sheild 3ala enemies
						{
							zft.getAppliedEffects().get(k).remove(zft);
							zft.getAppliedEffects().remove(k);
							targets.remove(z);
							z--;
							break;

						}
					}
				}
				
				

				a.execute(targets);

				for(int i=0 ; i<targets.size() ; i++)
				{
					if(targets.get(i) instanceof Champion)
					{
						Champion champ=(Champion) targets.get(i);
						if(targets.get(i).getCurrentHP() == 0)
						{

							firstPlayer.getTeam().remove(champ);

							secondPlayer.getTeam().remove(champ);
							champ.setCondition(Condition.KNOCKEDOUT);
							board [champ.getLocation().x][champ.getLocation().y] = null;
							champ.setLocation(null);
							helpelem();
							
						}

					}


					
				}
				
				
				getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
				getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
				
		
			}

			if(a instanceof CrowdControlAbility )
			{
				CrowdControlAbility tmp = (CrowdControlAbility) a;

				if(tmp.getEffect().getType() == EffectType.DEBUFF)
				{
					for(int i=0 ; i<firstPlayer.getTeam().size() && flags ; i++)
					{
						targets.add(firstPlayer.getTeam().get(i));
					}

					for(int i=0 ; i<secondPlayer.getTeam().size() && flagf ; i++)
					{
						targets.add(secondPlayer.getTeam().get(i));
					}

				

				
				
				
				}
				
				if(tmp.getEffect().getType() == EffectType.BUFF)
				{
					for(int i=0 ; i<firstPlayer.getTeam().size() && flagf ; i++)
					{
						targets.add(firstPlayer.getTeam().get(i));
					}

					for(int i=0 ; i<secondPlayer.getTeam().size() && flags ; i++)
					{
						targets.add(secondPlayer.getTeam().get(i));
					}
				}

				int dist=0;
				for(int i=0 ; i<targets.size() ; i++)
				{
					dist= getmanhattan(getCurrentChampion().getLocation(), targets.get(i).getLocation());               //range
					if(dist>a.getCastRange())
					{
						targets.remove(i);
						i--;
					}		
				}


				
				a.execute(targets);
				getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
				getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
				
		
			}
		}
		
		
		
		if (a.getCastArea() == AreaOfEffect.SELFTARGET)
		{
			if(a instanceof DamagingAbility )
				throw new InvalidTargetException();
			
			if(a instanceof CrowdControlAbility)
			{
				CrowdControlAbility tmp = (CrowdControlAbility) a;
				
				if(tmp.getEffect().getType() == EffectType.DEBUFF)
					throw new InvalidTargetException();
				

			}
			
			
			ArrayList<Damageable> targetsself = new ArrayList<Damageable>();
			targetsself.add(getCurrentChampion());
			a.execute(targetsself);
			getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
			getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
			
		}
			
	
			
			if (a.getCastArea() == AreaOfEffect.SURROUND)
			{
				Point currloc = getCurrentChampion().getLocation();
				ArrayList<Damageable> targetsurround = new ArrayList<Damageable>();
				Point tmplocation;
				Point up= new Point(1,0);
		        Point down= new Point(-1,0);
		        Point left = new Point(0,-1);
		        Point right = new Point(0,1);
		        Point dupright = new Point(1,1);
		        Point dupleft = new Point(1,-1);
		        Point ddownright = new Point(-1,1);
		        Point ddownleft = new Point(-1,-1);
		        
		        ArrayList<Point> tmp = new ArrayList<Point>();
		        tmp.add(up);  tmp.add(down); tmp.add(left); tmp.add(right); tmp.add(dupright);
		        tmp.add(dupleft); tmp.add(ddownright); tmp.add(ddownleft);
		        
		        for(int i=0 ; i<tmp.size() ; i++)
		        {
		        	tmplocation = addpoints(currloc, tmp.get(i));
		        	if(tmplocation.x <=4 && tmplocation.x>=0 && tmplocation.y<=4 && tmplocation.y>=0)             //shalet 33 failures
		        	{
		        		if(board[tmplocation.x][tmplocation.y]!=null)
		        		{
		        			targetsurround.add((Damageable) board[tmplocation.x][tmplocation.y]);
		        		}
		        	}
		        	
		        }
		        

		        if(a instanceof HealingAbility )
		        {
		        	if(flags)
		        	{

		        		for(int k=0; k<firstPlayer.getTeam().size() ; k++)
		        		{
		        			if(targetsurround.contains(firstPlayer.getTeam().get(k)))
		        				targetsurround.remove(firstPlayer.getTeam().get(k));

		        		}

		        	}

		        	if(flagf)
		        	{

		        		for(int k=0; k<secondPlayer.getTeam().size() ; k++)
		        		{
		        			if(targetsurround.contains(secondPlayer.getTeam().get(k)))
		        				targetsurround.remove(secondPlayer.getTeam().get(k));

		        		}

		        	}



		        	for(int j=0 ; j<targetsurround.size() ; j++)
		        	{
		        		if(targetsurround.get(j) instanceof Cover)
		        		{
		        			Cover cov = (Cover) targetsurround.get(j);
		        			targetsurround.remove(cov);
		        			j--;
		        		}
		        	}

		        }
				
		        
		        if(a instanceof DamagingAbility )
				{
		        	if(flagf)
					{
		        	
						for(int k=0; k<firstPlayer.getTeam().size() ; k++)
						{
							if(targetsurround.contains(firstPlayer.getTeam().get(k)))
								targetsurround.remove(firstPlayer.getTeam().get(k));
							
						}
		        		
					}
					
					if(flags)
					{
		        	
						for(int k=0; k<secondPlayer.getTeam().size() ; k++)
						{
							if(targetsurround.contains(secondPlayer.getTeam().get(k)))
								targetsurround.remove(secondPlayer.getTeam().get(k));
							
						}
		        		
					}
					
		        	
		        	
		        	
					for(int i=0 ; i<targetsurround.size() ; i++)
					{

						if( targetsurround.get(i) instanceof Champion)
						{
							Champion zft=(Champion)targetsurround.get(i);
							for(int z=0; z < (zft.getAppliedEffects().size()) ; z++)
							{
								if(zft.getAppliedEffects().get(z) instanceof Shield)
								{
									zft.getAppliedEffects().get(z).remove(zft);
									zft.getAppliedEffects().remove(z);                           //private
									targetsurround.remove(i);
									i--;
									break;

								}
							}


						}

					}
		        	
		        	
		      
				}
		        
		        
		        
		        if(a instanceof CrowdControlAbility )
				{
		        	CrowdControlAbility dina = (CrowdControlAbility) a;
		        	
		        	if(dina.getEffect().getType() == EffectType.DEBUFF)
		        	{
		        		
		        		if(flagf)
						{
			        	
							for(int k=0; k<firstPlayer.getTeam().size() ; k++)
							{
								if(targetsurround.contains(firstPlayer.getTeam().get(k)))
									targetsurround.remove(firstPlayer.getTeam().get(k));
								
							}
			        		
						}
						
						if(flags)
						{
			        	
							for(int k=0; k<secondPlayer.getTeam().size() ; k++)
							{
								if(targetsurround.contains(secondPlayer.getTeam().get(k)))
									targetsurround.remove(secondPlayer.getTeam().get(k));
								
							}
			        		
						}
						
						
						
		        	}
		        	
		        	if(dina.getEffect().getType() == EffectType.BUFF)
		        	{
		        		if(flags)
						{
			        	
							for(int k=0; k<firstPlayer.getTeam().size() ; k++)
							{
								if(targetsurround.contains(firstPlayer.getTeam().get(k)))
									targetsurround.remove(firstPlayer.getTeam().get(k));
								
							}
			        		
						}
						
						if(flagf)
						{
			        	
							for(int k=0; k<secondPlayer.getTeam().size() ; k++)
							{
								if(targetsurround.contains(secondPlayer.getTeam().get(k)))
									targetsurround.remove(secondPlayer.getTeam().get(k));
								
							}
			        		
						}
		        	
		        	}
		        	
		        	for(int j=0 ; j<targetsurround.size() ; j++)
		        	{
		        		if(targetsurround.get(j) instanceof Cover)
		        		{
		        			Cover cov = (Cover) targetsurround.get(j);
		        			targetsurround.remove(cov);
		        			j--;
		        		}
		        	}
		        	 
  	
				}
		        
		        
		       
		        
		        a.execute(targetsurround);

		        for(int i=0 ; i<targetsurround.size() ; i++)
		        {
		        	if(targetsurround.get(i) instanceof Champion)
		        	{
		        		Champion champ=(Champion) targetsurround.get(i);
		        		if(targetsurround.get(i).getCurrentHP() == 0)
		        		{

		        			firstPlayer.getTeam().remove(champ);

		        			secondPlayer.getTeam().remove(champ);

		        			champ.setCondition(Condition.KNOCKEDOUT);
		        			board [champ.getLocation().x][champ.getLocation().y] = null;
		        			champ.setLocation(null);
		        			helpelem();
		        		}

		        	}

		        	if(targetsurround.get(i) instanceof Cover)
		        	{
		        		Cover cov = (Cover) targetsurround.get(i);
		        		if(cov.getCurrentHP()==0)
		        			board [cov.getLocation().x][cov.getLocation().y] = null;
		        	}

		        }
		        getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
				getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
		        
		   
		        
			}
				
			
			
		}
		
		
	
	
	public static Point addpoints(Point a, Point b)
	{
		Point tmp = new Point(0,0);
		tmp.x = a.x + b.x;
		tmp.y = a.y + b.y;
		return tmp;
		
	}
	
	
	
	public int getmanhattan(Point kimo , Point dany)
	{
		return Math.abs(kimo.x-dany.x)+Math.abs(kimo.y-dany.y);
	}
	
	
	
	
	public void helpelem()
	{
		PriorityQueue tmp = new PriorityQueue(turnOrder.size());
		Champion lujina;
		while(!turnOrder.isEmpty())
		{
			lujina = (Champion) turnOrder.remove();
			if (lujina.getCurrentHP()!=0)
				tmp.insert(lujina);
		}
		
		while(!tmp.isEmpty())
		{
			turnOrder.insert(tmp.remove());
		}
		
	}
	
	
	
	
	public void castAbility(Ability a, Direction d) throws NotEnoughResourcesException, AbilityUseException, InvalidTargetException, CloneNotSupportedException
	{
		if(a.getCurrentCooldown()>0)
			throw new AbilityUseException();
		if(getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints()||
				getCurrentChampion().getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();
		
		for(int i=0;i<getCurrentChampion().getAppliedEffects().size();i++)
			if (getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException();
		
		
		int range = a.getCastRange();
		Point tmp = getCurrentChampion().getLocation();
		Champion champ;
		Cover cov;
		boolean flag = true;
		ArrayList<Damageable> balah = new ArrayList<Damageable>();
		int xtmp = 0;
		int ytmp = 0;
		
		if(d == Direction.UP)
			xtmp =1;
		if(d == Direction.DOWN)
			xtmp = -1;
		if(d == Direction.LEFT)
			ytmp = -1;
		if(d == Direction.RIGHT)
			ytmp = 1;
		
		
			for(int i=0 ; i<range ; i++)
			{
				                                                       //law el current hp ba2a 0, condition.knocked
				tmp.x = tmp.x+xtmp;
				tmp.y = tmp.y+ytmp;
				if(tmp.x >4 || tmp.x<0 || tmp.y>4 || tmp.y<0)
					break;
				
				if( board[tmp.x][tmp.y] !=null)
				{
					if(flag == true)
					{
						getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
						getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
						flag=false;
					}
					

					
					
					balah.add((Damageable) board[tmp.x][tmp.y]);
				}
			}
			
			
			
			boolean flags=false;	
			boolean flagf=false;	
			for (int i=0; i<firstPlayer.getTeam().size();i++)
			{
				if (firstPlayer.getTeam().get(i) == getCurrentChampion())
					flagf=true;
				
			}
			
			for (int i=0; i<secondPlayer.getTeam().size();i++)
			{
				if 	(secondPlayer.getTeam().get(i) == getCurrentChampion())
					flags=true;
			}
			
			
			if(a instanceof HealingAbility)
			{
				if(flags)
				{
	        	
					for(int k = 0; k<firstPlayer.getTeam().size() ; k++)
					{
						if(balah.contains(firstPlayer.getTeam().get(k)))
							balah.remove(firstPlayer.getTeam().get(k));
						
					}
	        		
				}
				
				if(flagf)
				{
	        	
					for(int k = 0; k<secondPlayer.getTeam().size() ; k++)
					{
						if(balah.contains(secondPlayer.getTeam().get(k)))
							balah.remove(secondPlayer.getTeam().get(k));
						
					}
	        		
				}
				
				
				for(int j=0 ; j<balah.size() ; j++)
	        	{
	        		if(balah.get(j) instanceof Cover)
	        		{
	        			Cover covv = (Cover) balah.get(j);
	        			balah.remove(covv);
	        			j--;
	        		}
	        	}
				
				
				
			}
			
			
			if( a instanceof DamagingAbility)
			{
				
				if(flagf)
				{
	        	
					for(int k = 0; k<firstPlayer.getTeam().size() ; k++)
					{
						if(balah.contains(firstPlayer.getTeam().get(k)))
							balah.remove(firstPlayer.getTeam().get(k));
						
					}
	        		
				}
				
				if(flags)
				{
	        	
					for(int k = 0; k<secondPlayer.getTeam().size() ; k++)
					{
						if(balah.contains(secondPlayer.getTeam().get(k)))
							balah.remove(secondPlayer.getTeam().get(k));
						
					}
	        		
				}
				
				for(int i=0 ; i<balah.size() ; i++)
				{
					if(balah.get(i) instanceof Champion)
					{

						Champion zft=(Champion)balah.get(i);
						for(int z=0; z < (zft.getAppliedEffects().size()) ; z++)
						{
							if(zft.getAppliedEffects().get(z) instanceof Shield)
							{
								zft.getAppliedEffects().get(z).remove(zft);                   //private
								zft.getAppliedEffects().remove(z);
								balah.remove(zft);
								i--;
								break;

							}
						}
					}
				}
				
				
			}
			
			
			
			if( a instanceof CrowdControlAbility)    //pointer
			{
				CrowdControlAbility dina = (CrowdControlAbility) a;
	        	
	        	if(dina.getEffect().getType() == EffectType.DEBUFF)
	        	{
	        		
	        		if(flagf)
					{
		        	
						for(int k = 0; k<firstPlayer.getTeam().size() ; k++)
						{
							if(balah.contains(firstPlayer.getTeam().get(k)))
								balah.remove(firstPlayer.getTeam().get(k));

						}

					}

	        		if(flags)
	        		{

	        			for(int k = 0; k<secondPlayer.getTeam().size() ; k++)
	        			{
	        				if(balah.contains(secondPlayer.getTeam().get(k)))
	        					balah.remove(secondPlayer.getTeam().get(k));

	        			}

	        		}

	        		for(int j=0 ; j<balah.size() ; j++)
	        		{
	        			if(balah.get(j) instanceof Cover)
	        			{
	        				Cover covv = (Cover) balah.get(j);
	        				balah.remove(covv);
	        				j--;
	        			}
	        		}




	        	}
	        	
	        	
	        	if(dina.getEffect().getType() == EffectType.BUFF)
	        	{

	        		if(flags)
	        		{

	        			for(int k=0; k<firstPlayer.getTeam().size() ; k++)
	        			{
	        				if(balah.contains(firstPlayer.getTeam().get(k)))
	        					balah.remove(firstPlayer.getTeam().get(k));

	        			}

	        		}

	        		if(flagf)
	        		{

	        			for(int k=0; k<secondPlayer.getTeam().size() ; k++)
	        			{
	        				if(balah.contains(secondPlayer.getTeam().get(k)))
	        					balah.remove(secondPlayer.getTeam().get(k));

	        			}

	        		}

					for(int j=0 ; j<balah.size() ; j++)
					{
						if(balah.get(j) instanceof Cover)
						{
							Cover covv = (Cover) balah.get(j);
							balah.remove(covv);
							j--;
						}
					}




	        	}


			}
			
			
			
			
			
				
			a.execute(balah);
			
			for(int i=0 ; i<balah.size() ; i++)
			{
				if(balah.get(i) instanceof Champion)
				{
					Champion champp=(Champion) balah.get(i);
					if(balah.get(i).getCurrentHP() == 0)
					{

						firstPlayer.getTeam().remove(champp);

						secondPlayer.getTeam().remove(champp);
						champp.setCondition(Condition.KNOCKEDOUT);
						board [champp.getLocation().x][champp.getLocation().y] = null;
						champp.setLocation(null);
						helpelem();
					}

				}
				
				if(balah.get(i) instanceof Cover)
				{
					Cover covv = (Cover) balah.get(i);
					if(covv.getCurrentHP()==0)
						board [covv.getLocation().x][covv.getLocation().y] = null;
				}
				
			}
		
		
	}
	
	
	
	
	public void castAbility(Ability a, int x, int y) throws InvalidTargetException, AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException
	{
		for(int i=0;i<getCurrentChampion().getAppliedEffects().size();i++)
			if (getCurrentChampion().getAppliedEffects().get(i) instanceof Silence)
				throw new AbilityUseException();
		if(a.getCurrentCooldown()>0)
			throw new AbilityUseException();
		if(getCurrentChampion().getCurrentActionPoints()<a.getRequiredActionPoints()||
				getCurrentChampion().getMana()<a.getManaCost())
			throw new NotEnoughResourcesException();
		
		if(x>4 || x<0 || y>4 || y<0)
			throw new InvalidTargetException();
		
		if (board[x][y]==null)
			throw new InvalidTargetException();
		
		
		
		Point p=getCurrentChampion().getLocation();
		Point p2= new Point(x,y);
		int distance= getmanhattan(p, p2);                                        //na2aset fail
		
		
		if (distance>a.getCastRange())
			throw new AbilityUseException();
		
		
		
	if (board[x][y] instanceof Cover && (a instanceof HealingAbility|| a instanceof CrowdControlAbility))
		throw new InvalidTargetException();
		
		
	boolean flags=false;	
	boolean flagf=false;	
	for (int i=0; i<firstPlayer.getTeam().size();i++)
	{
		if (firstPlayer.getTeam().get(i) == getCurrentChampion())
			flagf=true;
		
	}
	
	for (int i=0; i<secondPlayer.getTeam().size();i++)
	{
		if 	(secondPlayer.getTeam().get(i) == getCurrentChampion())
			flags=true;
	}
	
	
	if(a instanceof HealingAbility)
	{
		if(board[x][y] instanceof Cover)
			throw new InvalidTargetException();
		
		if(flags)
		{
			if(firstPlayer.getTeam().contains(board[x][y]))
				throw new InvalidTargetException();
		}
		
		if(flagf)
		{
			if(secondPlayer.getTeam().contains(board[x][y]))
				throw new InvalidTargetException();
		}
		
			
	}
	
	else if(a instanceof DamagingAbility)
	{
		if(flagf)
		{
			if(firstPlayer.getTeam().contains(board[x][y]))
				throw new InvalidTargetException();
		}
		
		if(flags)
		{
			if(secondPlayer.getTeam().contains(board[x][y]))
				throw new InvalidTargetException();
		}

		if(board[x][y] instanceof Champion)
		{
			Champion zft=(Champion) board[x][y];
			for(int z=0; z < (zft.getAppliedEffects().size()) ; z++)
			{
				if(zft.getAppliedEffects().get(z) instanceof Shield)
				{			
					zft.getAppliedEffects().get(z).remove(zft);
					zft.getAppliedEffects().remove(z);
					getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
					getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
					return;
				}
			}

		}

	}
	
	
	
	else if(a instanceof CrowdControlAbility)
	{
		
	
		CrowdControlAbility dina = (CrowdControlAbility) a;
		if(dina.getEffect().getType()==EffectType.BUFF)
		{
			if(flags)
			{
				if(firstPlayer.getTeam().contains(board[x][y]))
					throw new InvalidTargetException();
			}
			
			if(flagf)
			{
				if(secondPlayer.getTeam().contains(board[x][y]))
					throw new InvalidTargetException();
			}
			
		}

		else if(dina.getEffect().getType()==EffectType.DEBUFF)
		{
			if(flagf)
			{
				if(firstPlayer.getTeam().contains(board[x][y]))
					throw new InvalidTargetException();
			}

			if(flags)
			{
				if(secondPlayer.getTeam().contains(board[x][y]))
					throw new InvalidTargetException();
			}

		}
		
		
		
	}
	
	
	
	ArrayList<Damageable> targets = new ArrayList<Damageable>();
	
	targets.add((Damageable) board[x][y]);
	
	a.execute(targets);
	
	if(board [x][y] instanceof Cover)
	{
		Cover cov = (Cover) board[x][y];
		if(cov.getCurrentHP()==0)
		{
			board [x][y] = null;

		}
	}
	else if(board [x][y] instanceof Champion)
	{
		Champion champ=(Champion) board[x][y];
		if(firstPlayer.getTeam().contains(champ))
		{
			if (champ.getCurrentHP()==0)
			{
				firstPlayer.getTeam().remove(champ);
				champ.setCondition(Condition.KNOCKEDOUT);
				board [x][y] = null;
				champ.setLocation(null);
				helpelem();
				
			}	
		}
		
		else if(secondPlayer.getTeam().contains(champ))
		{
			if (champ.getCurrentHP()==0)
			{
				secondPlayer.getTeam().remove(champ);
				champ.setCondition(Condition.KNOCKEDOUT);
				board [x][y] = null;
				champ.setLocation(null);
				helpelem();
				
			}	
		}
		
	}
	
		getCurrentChampion().setMana(getCurrentChampion().getMana()-a.getManaCost());
		getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getCurrentActionPoints()-a.getRequiredActionPoints());
	
	
}		
	
	
	
	
	public void useLeaderAbility() throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException, CloneNotSupportedException
	{
		boolean flags=false;	
		boolean flagf=false;	
		for (int i=0; i<firstPlayer.getTeam().size();i++)
		{
			if (firstPlayer.getTeam().get(i).equals(getCurrentChampion()))
			{
				flagf=true;
				break;
			}
			if 	(secondPlayer.getTeam().get(i).equals( getCurrentChampion()) )
			{
				flags=true;
				break;
			}
		}
		
		if(flagf)                                                             //throw an exception if he is not a leader or if it is already used
			{
			if(!(getCurrentChampion().equals(firstPlayer.getLeader())))
				throw new LeaderNotCurrentException();
			if (firstLeaderAbilityUsed)
				throw new LeaderAbilityAlreadyUsedException();
			}
		
		if(flags == true && getCurrentChampion()!=secondPlayer.getLeader())
			throw new LeaderNotCurrentException();
		
		if(flags ==true && secondLeaderAbilityUsed == true)
			throw new LeaderAbilityAlreadyUsedException();
		
		
		
		
		
		ArrayList<Champion> targets = new ArrayList<Champion>();
		
		if (getCurrentChampion() instanceof Hero)       //get for each champ his targets 
		{
			Hero h=(Hero)getCurrentChampion();
			
			if (flagf == true)
			{
				for(int i=0; i<firstPlayer.getTeam().size();i++)
				{
					if(firstPlayer.getTeam().get(i).getCurrentHP()!=0)
						targets.add((Champion)firstPlayer.getTeam().get(i));
				}
			}	
			if (flags == true)
				for(int i=0; i<secondPlayer.getTeam().size();i++)
					if(secondPlayer.getTeam().get(i).getCurrentHP()!=0)
						targets.add((Champion)secondPlayer.getTeam().get(i));
			
			
			
			h.useLeaderAbility(targets);
			
		}
		
		
		if (getCurrentChampion() instanceof Villain)
		{
			Villain v=(Villain)getCurrentChampion();
			
			if (flags)
				for(int i=0; i<firstPlayer.getTeam().size();i++)
					if(firstPlayer.getTeam().get(i).getCurrentHP()!=0)
						targets.add((Champion)firstPlayer.getTeam().get(i));
			
			if (flagf)
				for(int i=0; i<secondPlayer.getTeam().size();i++)
					if(secondPlayer.getTeam().get(i).getCurrentHP()!=0)
						targets.add((Champion)secondPlayer.getTeam().get(i));
			
			
			v.useLeaderAbility(targets);
			
			
			for(int i=0 ; i<targets.size() ; i++)
			{
				if(targets.get(i) instanceof Champion)
				{
					Champion champp=(Champion) targets.get(i);
					if(targets.get(i).getCurrentHP() == 0)
					{

						firstPlayer.getTeam().remove(champp);

						secondPlayer.getTeam().remove(champp);
						//champp.setCondition(Condition.KNOCKEDOUT);
						board [champp.getLocation().x][champp.getLocation().y] = null;
						champp.setLocation(null);
						helpelem();
					}

				}
				
				
			}
			
		
			
		}
		
		
		if (getCurrentChampion() instanceof AntiHero)
		{
			AntiHero ant=(AntiHero)getCurrentChampion();

			for(int i=0 ; i<firstPlayer.getTeam().size(); i++)
			{	
				if(firstPlayer.getTeam().get(i) !=firstPlayer.getLeader() && firstPlayer.getTeam().get(i).getCurrentHP()!=0 )
				{
					targets.add(firstPlayer.getTeam().get(i));
				}
			}

			for(int i=0 ; i<secondPlayer.getTeam().size(); i++)
			{
				if(secondPlayer.getTeam().get(i) !=secondPlayer.getLeader() && secondPlayer.getTeam().get(i).getCurrentHP()!=0 )
				{
					targets.add(secondPlayer.getTeam().get(i));
				}

			}	

			ant.useLeaderAbility(targets);
		}
		
		
		//throw an invalid target exception?????
		
		
		
		if(flagf)
			firstLeaderAbilityUsed=true;
		if(flags)
			secondLeaderAbilityUsed=true;
	}	
	
	
	
	public void endTurn()
	{	
	
		turnOrder.remove();                                              //raga3t ely 3amalnah embare7,, na2aset 3 failures
		
		if(turnOrder.isEmpty())
			prepareChampionTurns();
		
		if(getCurrentChampion().getCondition()== Condition.INACTIVE)                               //shalet 4 failures
		{
			for(int i=0; i<getCurrentChampion().getAbilities().size();i++ )
				getCurrentChampion().getAbilities().get(i).setCurrentCooldown(
						getCurrentChampion().getAbilities().get(i).getCurrentCooldown()-1); //3la asas bybd2 mn el max w byfdl y2l
			
			
			for(int i=0; i<getCurrentChampion().getAppliedEffects().size();i++)
			{
				getCurrentChampion().getAppliedEffects().get(i).setDuration(
						getCurrentChampion().getAppliedEffects().get(i).getDuration()-1);   // zy el cooldown mn el max w bt2l
				
				if(getCurrentChampion().getAppliedEffects().get(i).getDuration()==0)
				{
					Effect tmp = getCurrentChampion().getAppliedEffects().get(i);
					getCurrentChampion().getAppliedEffects().remove(i);
					tmp.remove(getCurrentChampion());
					i--;
					
					
				}
				
			}

			endTurn();
		}
		
		
		
		for(int i=0; i<getCurrentChampion().getAbilities().size();i++ )
			getCurrentChampion().getAbilities().get(i).setCurrentCooldown(
					getCurrentChampion().getAbilities().get(i).getCurrentCooldown()-1); //3la asas bybd2 mn el max w byfdl y2l
		
		
		for(int i=0; i<getCurrentChampion().getAppliedEffects().size();i++)
		{
			getCurrentChampion().getAppliedEffects().get(i).setDuration(
					getCurrentChampion().getAppliedEffects().get(i).getDuration()-1);   // zy el cooldown mn el max w bt2l
			
			if(getCurrentChampion().getAppliedEffects().get(i).getDuration()==0)
			{
				Effect tmp = getCurrentChampion().getAppliedEffects().get(i);
				getCurrentChampion().getAppliedEffects().remove(i);
				tmp.remove(getCurrentChampion());
				i--;
				
				
			}
			
		}
		
		
		
		getCurrentChampion().setCurrentActionPoints(getCurrentChampion().getMaxActionPointsPerTurn()); // kol turn bynzl bl max 
	}
	
	
	
	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public Object[][] getBoard() {
		return board;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}
	
	
	
	
}
