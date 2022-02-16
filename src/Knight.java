
public class Knight {
	int energy;
	
	void attack(Ogre ogre) {
		ogre.energy = (int) (0.9 * ogre.energy); // (int) just casts a float result into an int
		ogre.revenge(this); // this represents a reference to the current knight object
		System.out.println(this);
	}
}
