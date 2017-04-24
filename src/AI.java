
public class AI extends Player {
	
	private static int[] actions = {Action.SHOOT, Action.RELOAD, Action.SHIELD};
	
	public AI(int ammo, int stock, String name) {
		super(ammo, stock, name);
	}

	@Override
	public int generateAction(int opponentAmmo){
		int index = 2;
		
		double lowerBound = 0.33;
		double upperBound = 0.67;
		
		if(opponentAmmo>0){
			if(super.getAmmo() == 0){
				lowerBound = 0.00;
				upperBound = 0.40;
			}
			else if(super.getAmmo()-1 > opponentAmmo){
				lowerBound = 0.50;
				upperBound = 0.75;
			}
			else if(super.getAmmo()+1 < opponentAmmo){
				lowerBound = 0.25;
				upperBound = 0.50;
			}
		}
		else{
			upperBound = 1.00;
			lowerBound = 0.50;
			if(super.getAmmo() == 0){
				lowerBound = 0.00;
			}
			else if(super.getAmmo()-3 > opponentAmmo){
				lowerBound = 0.75;
			}
			else if(super.getAmmo()-1 > opponentAmmo){
				lowerBound = 0.60;
			}
		}
		double rnd = rng.nextDouble();
		if(rnd < lowerBound){
			index = 0;
		}
		else if(rnd <= upperBound){
			index = 1;
		}
		else{
			index = 2;
		}
		return actions[index];
	}
}