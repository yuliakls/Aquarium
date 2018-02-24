package hw4;


public class AnimalFactory implements AbstractSeaFactory {

	public SeaCreature produceSeaCreature(String type) {
		switch(type){
			case "Fish":
				return new Fish();
			case "Jellyfish":
				return new Jellyfish();
			default:
				return null;
		} 

	}

}
