package Game;
import Characters.*;

//TODO upcasting
//TODO polymorfizmus - sila overridu, pokial dame dve triedy ktore extenduju tak pri pouziti unikatnej metody z child triedy
//TODO interfaces - imswimmable
//TODO access modifiers (spristupnenie privatnych premien, pouzitie metod this)(private - odpali uplne, treba getter, setter, protected - viditelne v hierarchii dedenia (child moze modif. parenta), hodit do googla, porovnanie
//TODO class diagram
//TODO super - vyvolavame metodu nadtypu
public class Game {
	static void clash(Ogre ogre, Knight knight) {
		knight.attack(ogre);
	}
	static int countEnergy(Energy[] e) {
		int sum = 0;
		for(Energy actual : e) {
			sum += actual.getEnergy();
		}
		return sum;
	}

	// both ogre and knight are beings who have energy
	// an interface provides uniform access
	// then we can define a method that calculates the energy difference with any two beings
	static int findEnergyDifference(Energy being1, Energy being2) {
		return being1.getEnergy() - being2.getEnergy();
	}
	
	public static void main(String[] args) {
		Ogre[] o = new Ogre[100];
		Knight[] k = new Knight[120];
		TerryfyingOgre[] to = new TerryfyingOgre[20];
		Energy[] ogreEnergies = new Energy[120];
		Energy[] knightEnergies = new Energy[120];

		for (int i = 0; i < 20; i++) {
			k[i] = new BraveKnight(140, new Sword(i));
			o[i] = new BadOgre();
			o[i].setValues(50, true);
			knightEnergies[i] = k[i];
			ogreEnergies[i] = o[i];

//			o[i].eat(k[i]); // the eat() method is not in the Ogre class interface!
//			((BadOgre) o[i]).eat(k[i]); // if we are sure that an ogre is a BadOgre, we can force the compiler to see it as such
/*			
			System.out.println(findEnergyDifference(o[i], k[i]) + " " +
					findEnergyDifference(k[i], o[i]) + " " +
					findEnergyDifference(k[i], k[i]));
*/
		}

		for (int i = 20; i < 40; i++) {
			k[i] = new Knight(40, new Sword(i));
			o[i] = new BadOgre(i*3);
			knightEnergies[i] = k[i];
			ogreEnergies[i] = o[i];
		}

		for (int i = 40; i < 100; i++) {
			k[i] = new Knight((int) (i*2.1), new Sword(i));
			o[i] = new Ogre(i*2);
			knightEnergies[i] = k[i];
			ogreEnergies[i] = o[i];
		}
		for(int i = 0; i < 20; i++) { // na implementaciu metody eat potrebujeme nove pole terryfyingOgrov
			k[i+100] = new BraveKnight(i*2*100, new Sword(i));
			to[i] = new TerryfyingOgre(150+i*2);
			knightEnergies[i+100] = k[i+100];
			ogreEnergies[i+100] = to[i];
		}
		int pocitadlo = 0;
		for (int i = 0; i < 120; i++) { // tuto skutocnost zohladnime vo vypise, aby sme mohli mat knightov v jednom poli aj v boji s ogrami aj terryfying ogrami
			if (i < 100) {
				clash(o[i], k[i]);
				System.out.println(i + ":" + "knight " + k[i].getEnergy() +
					" / " + "ogre " + o[i].getEnergy() + " sword: " + k[i].showSword());
			}
			else {
				clash(to[pocitadlo], k[i]);
				to[pocitadlo].eat(k[i]);
				System.out.println(i + ":" + "knight " + k[i].getEnergy() +
						" / " + "ogre " + to[pocitadlo].getEnergy() + " sword: " + k[i].showSword());
				pocitadlo++;
			}
		}
		int energyKnightSum = countEnergy(knightEnergies);
		int energyOgreSum = countEnergy(ogreEnergies);
		System.out.println("Sum value of all energies of the ogres is:" + energyOgreSum);
		System.out.println("Sum value of all energies of the knights is:" + energyKnightSum);
	}
}
