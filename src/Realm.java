import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm {
    private static BufferedReader br;
    private static FantasyCharacter player = null;
    private static BattleScene battleScene = new BattleScene();
    private static Merchant merchant = new Merchant(); // новый конструктор без параметров

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Введите имя персонажа:");

        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        if (player == null) {
            player = new Hero(
                    string,
                    500,
                    30,
                    20,
                    0,
                    100,
                    1,
                    0,
                    100
            );

            System.out.println("Имя персонажа: " + player.getName());
            System.out.println("Текущий уровень: " + player.getLevel());
            System.out.println("Текущее здоровье: " + player.getHealthPoints());
            System.out.println("Текущее золото: " + player.getGold());
            System.out.println(String.format("Спасти наш мир от драконов вызвался %s! Да будет его броня крепка и бицепс кругл!", player.getName()));
            printNavigation();
        }
    }

    private static void buyFromMerchant() throws IOException {
        System.out.println("Торговец предлагает вам выбрать что-то из ассортимента:");
        System.out.println("1. Зелье (10 золота)");
        System.out.println("2. Оружие (30 золота)");
        System.out.println("3. Доспех (50 золота)");

        String choice = br.readLine();

        switch (choice) {
            case "1":
                if (player.getGold() >= 10) {
                    player.setGold(player.getGold() - 10);
                    player.setHealthPoints(player.getHealthPoints() + 50);
                    System.out.println("Вы купили лечебное зелье за 10 золотых и восстановили 50 единиц здоровья. Теперь у вас " + +player.getHealthPoints() + " единиц здоровья");
                    System.out.println("Золото после покупки: " + player.getGold());
                } else {
                    System.out.println("У вас недостаточно золота.");

                }
                break;
            case "2":
                if (player.getGold() >= 30) {
                    player.setGold(player.getGold() - 30);
                    System.out.println("Поздравляем! Вы успешно купили Оружие.");
                    System.out.println("Золото после покупки: " + player.getGold());
                } else {
                    System.out.println("У вас недостаточно золота.");
                }
                break;
            case "3":
                if (player.getGold() >= 50) {
                    player.setGold(player.getGold() - 50);
                    System.out.println("Поздравляем! Вы успешно купили Доспех.");
                    System.out.println("Золото после покупки: " + player.getGold());
                } else {
                    System.out.println("У вас недостаточно золота.");
                }
                break;
            default:
                System.out.println("Неверный выбор.");
        }

        if (player.getGold() >= 10) {
            System.out.println("Желаете продолжить покупки у торговца? (да/нет)");
            String continueShopping = br.readLine();
            if ("да".equalsIgnoreCase(continueShopping)) { // Проверяем желание продолжить покупки
                buyFromMerchant(); // Вызываем метод для выбора следующего предмета
            } else {
                printNavigation(); // Возвращаемся к выбору дальнейших действий
            }
        } else {
            System.out.println("У вас недостаточно золота для продолжения приключения.");
            printNavigation(); // Возвращаемся к выбору дальнейших действий
        }
    }

    private static void printNavigation() {
        System.out.println("================");
        System.out.println("Выберите действие:");
        System.out.println("1. Купить у торговца");
        System.out.println("2. В тёмный лес");
        System.out.println("3. Выйти из игры");
        System.out.println("================");

        try {
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1:
                    buyFromMerchant();
                    break;
                case 2:
                    commitFight();
                    break;
                case 3:
                    System.out.println("Игра завершена. Всего доброго!");
                    System.exit(0);
                default:
                    System.out.println("Некорректный выбор. Повторите попытку.");
            }

        } catch (NumberFormatException | IOException e) {
            System.out.println("Ошибка при чтении выбора. Повторите попытку.");
        }

    }


    private static void commitFight() {
        FantasyCharacter monster = createMonster();

        battleScene.fight(player, monster, new FightCallback() {
            @Override
            public void fightWin() {
                int gold = 100;
                player.setGold(player.getGold() + gold);
                player.gainExperience(50); // предположим, что за победу игрок получает 50 опыта

                System.out.println("Вы получили " + gold + " золота за победу над монстром.");
                System.out.println("У вас теперь " + player.getGold() + " золота.");

                // проверяем, если игрок накопил достаточно опыта для нового уровня
                if (player.getXp() >= player.getExperienceToLevelUp()) {
                    System.out.println("Поздравляем! Вы накопили достаточно опыта для нового уровня.");
                    player.levelUp();
                }


                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d единиц здоровья.", player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                try {
                    System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                    String choice = br.readLine();
                    if ("да".equalsIgnoreCase(choice)) {
                        commitFight();
                    } else if ("нет".equalsIgnoreCase(choice)) {
                        System.out.println("Вы решили вернуться в город.");
                        printNavigation();
                    } else {
                        System.out.println("Некорректный выбор. Попробуйте еще раз.");
                        commitFight(); // Повторный вызов метода для повторного ввода

                    }

                    String command = (br.readLine());
                    command(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }


    private static FantasyCharacter createMonster() {
        int random = (int) (Math.random() * 10);
        return random % 2 == 0 ?
                new Goblin("Гоблин", 150, 10, 10, 100, 20, 1) :
                new Skeleton("Скелет", 125, 20, 20, 100, 10, 1);
    }

    interface FightCallback {
        void fightWin();

        void fightLost();
    }
}