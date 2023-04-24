package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Stun extends Effect {

	public Stun(int duration) {
		super("Stun", duration, EffectType.DEBUFF);
	}

	@Override
	public void apply(Champion c) 
	{
		
		c.setCondition(Condition.INACTIVE);
		
	}

	@Override
	public void remove(Champion c) 
	{


		boolean flagstun = false;

		for( int i = 0;i<c.getAppliedEffects().size();i++)
		{
			if(c.getAppliedEffects().get(i) instanceof Stun)                  
			{
				flagstun = true;
			}
		}
		
		
		boolean flagroot = false;

		for( int i = 0;i<c.getAppliedEffects().size();i++)
		{
			if(c.getAppliedEffects().get(i) instanceof Root)                          //if(c.getCondition() != Condition.ROOTED)  
			{
				flagroot = true;
			}
		}
		
		if(flagroot==true && flagstun ==false)
			c.setCondition(Condition.ROOTED);

		if(flagstun==false && flagroot==false)                                                       //inactive ,,, rooted ,,, active
			c.setCondition(Condition.ACTIVE);

	}




}
