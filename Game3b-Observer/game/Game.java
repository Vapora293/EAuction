package game;
import characters.*;
import java.util.*;

public class Game {
	//TODO document so strag
	/* add Observer(User u) {
	this.users.add(u);
	}
	public remove(User u) {
	this.user.remove(u);
	}
	public DecreaseRespCapacity(int a) {
	this.resp = a;
	Notify();
	}
	 */
	Ogre[] ogres;
	Knight[] knights;

	int numberOfWarriors;

	private ArrayList<ClashFollower> clashFollowers = new ArrayList<>();
	
	public void clash1on1(Knight knight, Ogre ogre) {
		knight.attack(ogre);
		informClashFollowers();
	}

	static int findEnergyDifference(Energy being1, Energy being2) {
		return being1.getEnergy() - being2.getEnergy();
	}

	public Game(int nKnights, int nBraveKnights, int nBadOgres) {
		numberOfWarriors = nKnights + nBraveKnights;
		knights = new Knight[numberOfWarriors];
		ogres = new Ogre[numberOfWarriors];

		for (int i = 0; i < nKnights; ++i){
			knights[i] = new Knight(80, new Sword(i));
		}

		for (int i = nKnights; i < numberOfWarriors; ++i) {
			knights[i] = new BraveKnight(100, new Sword(i));
		}

		for (int i = 0; i < nBadOgres; ++i) {
			ogres[i] = new BadOgre(100);
		}
		
		for (int i = nBadOgres; i < numberOfWarriors; ++i) {
			ogres[i] = new Ogre(100);
		}
	}

	public String clash() {
		String report = "";

		// we accumulate the information on the clash into the report variable
		for (int i = 0; i < knights.length; ++i){
			clash1on1(knights[i], ogres[i]);		
			report = report + (i + 1) + ":"
							+ "knight " + knights[i].getEnergy() + " / "
							+ "ogre " + ogres[i].getEnergy() + " / "
							+ knights[i].showSword()
							+ "\n";
		}

		return report;
	}
	
	public void followClash(ClashFollower clashFollower) {
		clashFollowers.add(clashFollower);
	}
	
	public void informClashFollowers() {
		for (ClashFollower clashFollower : clashFollowers)
			clashFollower.inform();
	}	

	public static void main(String[] args) {
		Game game = new Game(7, 3, 5);
		
		game.followClash(new ClashWinners(game));
		game.followClash(new ClashWinners(game)); // another observer of the same kind
		game.followClash(new ClashReport(game));
		game.followClash(new ClashReport(game));
		game.followClash(new ClashLoosers(game));
		
		System.out.println(game.clash());
	}
}
