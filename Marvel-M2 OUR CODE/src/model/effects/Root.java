package model.effects;

import model.world.Champion;
import model.world.Condition;

public class Root extends Effect 
{

	public Root( int duration) 
	{
		super("Root", duration, EffectType.DEBUFF);
		
	}

	@Override
public void apply(Champion c)
	{
		
		if(c.getCondition() == Condition.INACTIVE)
			return;
		c.setCondition(Condition.ROOTED);
		
	}

	
	public void remove(Champion c) 
	{

		
		boolean flag = false;
		
		for( int i = 0;i<c.getAppliedEffects().size();i++)
		{
			if(c.getAppliedEffects().get(i) instanceof Root)                  
			{
				flag = true;
			}
		}
		
		
		if(flag==false && c.getCondition()!= Condition.INACTIVE)
			c.setCondition(Condition.ACTIVE);
		                                                                             //raga3o active
		
	}
	

//	public Object clone() 
//	{
//	
//		Root x=new Root (this.getDuration());
//		return x;
//	}

	
}
