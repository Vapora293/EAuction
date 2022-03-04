package game;

public class ClashLoosers implements ClashFollower {
    Game game;

    public ClashLoosers(Game game) {
        this.game = game;
    }

    @Override
    public void inform() {
        int knightsEnergy = 0;
        int ogresEnergy = 0;

        for (int i = 0; i < game.numberOfWarriors; ++i) {
            knightsEnergy += game.knights[i].getEnergy();
            ogresEnergy += game.ogres[i].getEnergy();
        }
        if (knightsEnergy > ogresEnergy)
            System.out.println("*** Ogres are loosing. ***");
        else
            System.out.println("*** Knights are loosing. ***");
    }
}
