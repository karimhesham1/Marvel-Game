package model.abilities;
import java.util.ArrayList;

import exceptions.*;
import model.world.*;

public class DamagingAbility extends Ability {
	
	private int damageAmount;
	public DamagingAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area,int required,int damageAmount) {
		super(name, cost, baseCoolDown, castRadius, area,required);
		this.damageAmount=damageAmount;
	}
	public int getDamageAmount() {
		return damageAmount;
	}
	public void setDamageAmount(int damageAmount) 
	{
		this.damageAmount = damageAmount;
	}
	
	public void execute(ArrayList<Damageable> targets)  
	{

	
			for (int i=0; i<targets.size();i++)
		
				{
					
					if (targets.get(i) instanceof Champion)
					{
						Champion c=(Champion) targets.get(i);
						int newHP= c.getCurrentHP()-damageAmount;
						c.setCurrentHP(newHP);
					}
					if (targets.get(i) instanceof Cover)
					{
						Cover cover =(Cover) targets.get(i);
						int newHP= cover.getCurrentHP()-damageAmount;
						cover.setCurrentHP(newHP);
					}
				}
			setCurrentCooldown(getBaseCooldown());
	}
	
	

}
