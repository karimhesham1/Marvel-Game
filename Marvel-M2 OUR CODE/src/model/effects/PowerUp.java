package model.effects;

import java.util.ArrayList;
import model.abilities.*;
import model.world.*;

public class PowerUp extends Effect {
	

	public PowerUp(int duration) {
		super("PowerUp", duration, EffectType.BUFF);
		
	}

	@Override
	public void apply(Champion c) 
	{
		
		
		ArrayList<Ability> tmp = c.getAbilities();

		for(int i = 0; i<tmp.size() ; i++)
		{
			Ability currability= tmp.get(i);
			if(currability instanceof DamagingAbility) 

			{
				int currdmg = ((DamagingAbility) currability).getDamageAmount();
				int newdmg = (int)(currdmg*1.2);
				((DamagingAbility) currability).setDamageAmount(newdmg);
			}

			else if (currability instanceof HealingAbility)
			{
				int currheal = ((HealingAbility) currability).getHealAmount();
				int newheal  = (int)(currheal*1.2);
				((HealingAbility) currability).setHealAmount(newheal);
			}

		}
		
		
	}

	@Override
	public void remove(Champion c) 
	{
                   

		ArrayList<Ability> tmp = c.getAbilities();

		for(int i = 0; i<tmp.size() ; i++)
		{
			Ability currability= tmp.get(i);
			if(currability instanceof DamagingAbility) 

			{
				int currdmg = ((DamagingAbility) currability).getDamageAmount();
				int newdmg = (int)(currdmg/1.2);
				((DamagingAbility) currability).setDamageAmount(newdmg);
			}

			else if (currability instanceof HealingAbility)
			{
				int currheal = ((HealingAbility) currability).getHealAmount();
				int newheal  = (int)(currheal/1.2);
				((HealingAbility) currability).setHealAmount(newheal);
			}

		}

	}
	
	
	

//	public Object clone() 
//	{
//	
//		PowerUp x=new PowerUp (this.getDuration());
//		return x;
//	}
	
}
