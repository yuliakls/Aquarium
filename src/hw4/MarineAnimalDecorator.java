package hw4;

import java.awt.Color;

public class MarineAnimalDecorator implements MarineAnimal {

	private MarineAnimal animal;
	public MarineAnimalDecorator(MarineAnimal obj){
		animal=obj;
		
	}
	
	public void PaintFish(Color col) {
		animal.PaintFish(col);
		
	}



}
