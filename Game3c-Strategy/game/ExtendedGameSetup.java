package game;

import characters.*;

public class ExtendedGameSetup implements GameSetup {
    @Override
    public void setup(Knight[] knights, Ogre[] ogres, int nKnights, int nBraveKnights, int nBadOgres) {
        int numberOfWarriors = nKnights + nBraveKnights;

        for (int i = 0; i < nBraveKnights; ++i){
            knights[i] = new BraveKnight(120, new Sword(i));
        }

        for (int i = nBraveKnights; i < numberOfWarriors; ++i) {
            knights[i] = new Knight(100, new Sword(i));
        }

        for (int i = 0; i < nBadOgres; ++i) {
            ogres[i] = new BadOgre(150);
        }

        for (int i = nBadOgres; i < numberOfWarriors; ++i) {
            ogres[i] = new Ogre(200);
        }
    }
}
