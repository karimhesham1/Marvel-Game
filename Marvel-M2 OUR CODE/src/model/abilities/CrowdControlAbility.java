package model.abilities;
import exceptions.*;
import java.util.ArrayList;
import model.world.*;

import model.effects.Effect;

public class CrowdControlAbility extends Ability {
	private Effect effect;

	public CrowdControlAbility(String name, int cost, int baseCoolDown, int castRadius, AreaOfEffect area, int required,
			Effect effect) {
		super(name, cost, baseCoolDown, castRadius, area, required);
		this.effect = effect;

	}

	public Effect getEffect() {
		return effect;
	}

	@Override
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException
	{

		for (int i=0; i<targets.size();i++)
		{
			if (targets.get(i) instanceof Champion)
			{
				Champion c=(Champion) targets.get(i);
			//	Effect x = this.getEffect();
				this.getEffect().apply(c);
				c.getAppliedEffects().add( (Effect) this.getEffect().clone());
			}


		}

		setCurrentCooldown(getBaseCooldown());


	}




}
