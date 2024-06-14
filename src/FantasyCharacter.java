

public abstract class FantasyCharacter implements Fighter {
    //Имя персонажа
    protected String name;
    //Статы персонажа
    protected int healthPoints;
    protected int strength;
    protected int dexterity;

    //Опыт и золото
    private int xp;
    private int gold;
    protected int level;
    protected int experience;
    protected int experienceToLevelUp;


    //Конструктор
    public FantasyCharacter(String name, int healthPoints, int strength, int dexterity, int xp, int gold, int level) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.strength = strength;
        this.dexterity = dexterity;
        this.xp = xp;
        this.gold = gold;
        this.level = level;
    }

    //Метод для ведения боя
    @Override
    public int attack() {
        if (dexterity * 3 > getRandomValue()) return strength;
        else return 0;
    }

    public void levelUp() {
        this.level++;
    }

    public void gainExperience(int exp) {
        this.experience += exp;
    }

    //Геттеры и сеттеры
    public String getName() {
        return name;
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }


    public int getExperienceToLevelUp() {
        return experienceToLevelUp;
    }


    private int getRandomValue() {
        return (int) (Math.random() * 100);
    }

    //Переопределяем вывод в консоль, чтобы выводилось имя и очки здоровья
    @Override
    public String toString() {
        return String.format("%s здоровье:%d", name, healthPoints);
    }

}