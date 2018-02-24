package hw4;


public class PlantFactory implements AbstractSeaFactory {


	public SeaCreature produceSeaCreature(String type) {
		switch(type){
			case "Laminaria":
				return new Laminaria();
			case "Zostera":
				return new Zostera();
			default:
				return null;
		} 

	}




}
