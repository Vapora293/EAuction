import java.util.Random;

public class Game {
	static void clash(Ogre ogre, Knight knight) {
		knight.attack(ogre);
	}

	public static void main(String[] args) {
		Knight[] k = new Knight[120];
		Ogre[] o = new Ogre[120];


		// another kind of an ogre has been added: BadOgre
		// this is just the initialization of ogre and knight arrays so that different combinations would occur
		for (int i = 0; i < 20; i++) {
			k[i] = new Knight();
			k[i].energy = 40;
			o[i] = new BadOgre();
			o[i].energy = 50;
			o[i].hungry = true;
		}

		for (int i = 20; i < 40; i++) {
			k[i] = new Knight();
			k[i].energy = 40;
			o[i] = new BadOgre();
			o[i].energy = 50;
		}

		for (int i = 40; i < 100; i++) {
			k[i] = new Knight();
			k[i].energy = 40;
			o[i] = new Ogre();
			o[i].energy = 50;
		}
		for (int i = 100; i < 120; i++) { // for cyklus s novymi instanciami tried VerBadOgre a WhiteKnight
			k[i] = new WhiteKnight();
			k[i].energy = 80;
			o[i] = new VeryBadOgre();
			Random r = new Random();
			int randomInt = r.nextInt(100) + 1;
			o[i].energy = randomInt;
		}

		// the clash loop with an output statement
		for (int i = 0; i < 120; i++) {
			clash(o[i], k[i]);
			
			System.out.println(i + ":"
					+ "knight " + k[i].energy + " / "
					+ "ogre " + o[i].energy);
		}
	}
}
