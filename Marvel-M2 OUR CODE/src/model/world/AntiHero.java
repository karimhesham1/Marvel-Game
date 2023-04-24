package model.world;

import java.util.ArrayList;

import model.effects.Effect;
import model.effects.Stun;

public class AntiHero extends Champion {

	public AntiHero(String name, int maxHP, int maxMana, int actions, int speed, int attackRange, int attackDamage) {
		super(name, maxHP, maxMana, actions, speed, attackRange, attackDamage);

	}

	@Override
	public void useLeaderAbility(ArrayList<Champion> targets) throws CloneNotSupportedException  //assume en el arraylist mafehash leaders
	{
		for (int i=0 ; i<targets.size() ; i++ )
		{
			Stun x = new Stun(2);
			targets.get(i).getAppliedEffects().add( (Effect) x.clone());
			x.apply(targets.get(i));
			
		}
		
	}
}
