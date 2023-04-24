package model.effects;

import model.world.*;
import exceptions.ChampionDisarmedException;
import model.abilities.*;
import exceptions.*;

public class Disarm extends Effect {
	

	public Disarm( int duration) {
		super("Disarm", duration, EffectType.DEBUFF);

	}



	@Override
	public void apply(Champion c) 

	{

		DamagingAbility tmp = new DamagingAbility("Punch", 0, 1, 1, AreaOfEffect.SINGLETARGET, 1, 50);
		c.getAbilities().add(tmp);

	}

	@Override
	public void remove(Champion c) 
	{

		for(int i=0;i<c.getAbilities().size();i++)
		{

			if (c.getAbilities().get(i).getName().equals("Punch"))
			{
				c.getAbilities().remove(i);
				return;
			}

		}

	}


	
//	public Object clone() 
//	{
//	
//		Disarm x=new Disarm (this.getDuration());
//		return x;
//		
//	}
//	
}
