
import java.util.Random;
import java.util.Scanner;

class CodesoftTask1 {

    public static void main(String[] args) {

        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        int min = 1;
        int max = 100;
        int totalnoOfAttempts = 5;
        boolean playagain = true;
        int score = 0;

        System.out.println("Welcome to the Number Guessing Game ");
        while (playagain) {

            int randomNum = random.nextInt((max - min) + 1) + min; // it will generate the number from 1 to 100
            int attempts = 0;
            boolean guessCorrectOrNot = false;

            System.out.println("The Number is generated in the range  " + min + " to " + max);
            System.out.println("The total number of attempts you shoud have are  " + totalnoOfAttempts);

            while (attempts < totalnoOfAttempts) {
                System.out.println("Enter your guess: ");

                int guessNum = sc.nextInt();
                attempts++;

                if (guessNum == randomNum) {
                    System.out.println("Congratulations! You guessed the correct number!");
                    score += (totalnoOfAttempts - attempts + 1);
                    guessCorrectOrNot = true;
                    break;
                } else if (guessNum < randomNum) {
                    System.out.println("Your guess is too low, try to guess high number");
                } else {
                    System.out.println("Your guess is too high, try to guess low ");
                }
                System.out.println("The total number of attempts remaining are " + (totalnoOfAttempts - attempts));
            }
            if (!guessCorrectOrNot) {
                System.out.println("Sorry, you have used all your attempts no more chance to further play  ");
            }
            System.out.println("Whether you wanted more chance to play Yes or No ");
            String response = sc.next();
            playagain = response.equalsIgnoreCase("Yes");

        }
        System.out.println("\nThank you for playing the game!  Your final score is: " + score);
        sc.close();
    }
}
