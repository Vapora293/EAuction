public class WhiteKnight extends Knight{
    @Override
    void attack(Ogre ogre) {
        ogre.energy = (int) 0.5 * ogre.energy;
    }
}
