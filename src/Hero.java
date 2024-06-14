public class Hero extends FantasyCharacter {

    private int experience;
    private int experienceToLevelUp;

    public Hero(String name, int healthPoints, int strength, int dexterity, int xp, int gold, int level, int experience, int experienceToLevelUp) {
        super(name, healthPoints, strength, dexterity, xp, gold, level);
        this.experience = experience;
        this.experienceToLevelUp = experienceToLevelUp; // опыт, необходимый для перехода на следующий уровень
    }


    public void gainExperience(int experience) {
        this.experience += experience;
        if (this.experience >= this.experienceToLevelUp) {
            levelUp();
        }
    }

    public void levelUp() {
        this.level++;
        this.healthPoints += 50;
        this.strength += 5;
        this.dexterity += 2;
        this.experience = 0;
        this.experienceToLevelUp *= 2; // увеличиваем количество опыта для следующего уровня
        System.out.println(String.format("Поздравляем! %s достиг нового уровня - %d", this.name, this.level));
    }
}

