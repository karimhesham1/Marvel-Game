package model.world;

import java.util.ArrayList;

public class Villain extends Champion {

	public Villain(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) 
	{
		for (int i=0 ; i<targets.size() ; i++ )
		{
			if(targets.get(i).getCurrentHP()/targets.get(i).getMaxHP() < 0.3)      //law el health less than 30% sala7et failure 
			{
				targets.get(i).setCondition(Condition.KNOCKEDOUT);
			}
			
		}
		
	}

	
}
