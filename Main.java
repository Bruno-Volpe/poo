// Guilherme Xavier 14575641
// Bruno Volpe 14651980
// Interfaces 
interface Animal {
    String getName();

    String getSpecies();

    void eat();

    void sound();
}

interface Ecology {
    String getHabitat();
}

// classe abstrata que implementa as interfaces Animal e Ecology
abstract class Mammal implements Animal, Ecology {
    protected String name;
    protected String species;

    // Constructor
    public Mammal(String name, String species) {
        this.name = name;
        this.species = species;
    }

    @Override
    public void eat() {
        System.out.println("Eats like a mammal.");
    }

    @Override
    public abstract void sound();

    @Override
    public abstract String getHabitat();

    // metodo fabrica
    public static Animal get(String name, String species) {
        if (species.equalsIgnoreCase("Elephant")) {
            return new Elephant(name);
        } else if (species.equalsIgnoreCase("Lion")) {
            return new Lion(name);
        }
        return null;
    }

    // gets dos atributos privados
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSpecies() {
        return species;
    }
}

// classe Elephant que herda de Mammal
class Elephant extends Mammal {
    public Elephant(String name) {
        super(name, "Elephant");
    }

    // reescrevendo os metodos abstratos
    @Override
    public void sound() {
        System.out.println("Trumpets");
    }

    @Override
    public String getHabitat() {
        return "Grasslands";
    }
}

// classe Lion que herda de Mammal
class Lion extends Mammal {
    public Lion(String name) {
        super(name, "Lion");
    }

    // reescrevendo os metodos abstratos
    @Override
    public void sound() {
        System.out.println("Roars");
    }

    @Override
    public String getHabitat() {
        return "Savannah";
    }
}

public class Main {
    public static void main(String[] args) {
        Animal elephant = Mammal.get("Caramelo", "Elephant");
        Animal lion = Mammal.get("Mufasa", "Lion");

        System.out.println("Name: " + elephant.getName() + ", Species: " + elephant.getSpecies());
        elephant.eat();
        elephant.sound();
        System.out.println("Habitat: " + ((Ecology) elephant).getHabitat());

        System.out.println("Name: " + lion.getName() + ", Species: " + lion.getSpecies());
        lion.eat();
        lion.sound();
        System.out.println("Habitat: " + ((Ecology) lion).getHabitat());
    }
}
