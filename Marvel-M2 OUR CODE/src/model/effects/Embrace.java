package model.effects;

import model.world.Champion;

public class Embrace extends Effect {
	

	public Embrace(int duration) {
		super("Embrace", duration, EffectType.BUFF);
	}

	@Override
	public void apply(Champion c) 
	{
		
		
		int originalhp = c.getMaxHP();
		int addhp = (int) ((int)originalhp*0.2);
		c.setCurrentHP(c.getCurrentHP()+addhp);
		
		int newmana= (int) (c.getMana()*1.2);
		c.setMana(newmana);
		
		c.setSpeed( (int) (c.getSpeed()*1.2) );              //increases speed by 20%
		c.setAttackDamage((int)(c.getAttackDamage()*1.2));   //inc atta by 20
		
		
	}

	@Override
	public void remove(Champion c) 
	{
                  
		 c.setSpeed((int)(c.getSpeed()/1.2));
		 c.setAttackDamage((int)(c.getAttackDamage()/1.2));
		
	}
	
//	
//	public Object clone() 
//	{
//	
//		Embrace x=new Embrace (this.getDuration());
//		return x;
//	}

}
