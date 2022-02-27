package Characters;
public class TerryfyingOgre extends BadOgre{
    public TerryfyingOgre() {
        super();
    }
    public TerryfyingOgre(int energy) {
        super(energy);
    }

    @Override
    public void eat(Knight knight) {
        super.eat(knight);
        roar();
    }
    public void roar() {
        System.out.println("AAAAAAA");
    }
}
