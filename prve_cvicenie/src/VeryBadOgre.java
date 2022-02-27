public class VeryBadOgre extends BadOgre{
    public void revenge(Knight knight) {
        if (energy > knight.energy)
            knight.energy = (int) (0.8 * knight.energy);
    }
}
