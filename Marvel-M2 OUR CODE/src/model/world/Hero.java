package model.world;

import java.util.ArrayList;

import model.effects.Effect;
import model.effects.EffectType;
import model.effects.Embrace;

public class Hero extends Champion {

	public Hero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) throws CloneNotSupportedException  
	{
		
		for (int i=0; i<  targets.size() ; i++)
		{
			Champion dany = targets.get(i);
			
			for(int j=0 ; j<dany.getAppliedEffects().size() ; j++)
			{
				if(dany.getAppliedEffects().get(j).getType().equals(EffectType.DEBUFF))
				{
					dany.getAppliedEffects().remove(j);
					j--;
				
					
				}
			}
			Embrace x = new Embrace(2);
			dany.getAppliedEffects().add((Effect)x.clone());
			new Embrace(2).apply(dany);
			
		}
		

	}
}
