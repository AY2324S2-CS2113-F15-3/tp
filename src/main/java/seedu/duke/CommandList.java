package seedu.duke;

import seedu.duke.ai.Ai;
import seedu.duke.stats.MatchStat;
import seedu.duke.stats.PlayerList;
import seedu.duke.ui.Ui;

public enum CommandList {

    BYE, SHOOT, PENALTY, YES, NO, UPGRADE, EASY, MEDIUM, HARD, SAVE, HEAD, TAIL, HELP, CUSTOMIZATION;

    //insert new user command name here
    ;

    /**
     * Exits the program
     */
    public static void executeBye() {
        Formatter.printGoodbyeMsg();
        Ui.setIsRunning(false);
    }

    public static boolean goalCheck(float userInput, float aiInput, float range) {
        assert userInput >= 0 && userInput <= 8 :
                "Illegal userInput generated!";
        assert aiInput >= 0 && aiInput <= 8 :
                "Illegal aiInput generated!";
        return ((userInput>(aiInput+range))||userInput<(aiInput-range));
    }

    public static void executePenalty(DifficultyLevel difficultyLevel) {
        Ai ai = new Ai(difficultyLevel);
        Penalty.executePenalty(ai);
    }
  
    public static void executeShoot(String[] readArgumentTokens) {
        String selectedDirection = readArgumentTokens[0];
        int selectedDirectionIndex = Integer.parseInt(selectedDirection);

        float adjustedDirection = PlayerList.playerList.get(Ui.curPlayer).shootDirectionAdjust(selectedDirectionIndex);
        float adjustedAiDirection = PlayerList.playerList.get(Ui.curPlayer).aiDirectionAdjust(Ai.getAiDirection());
        float adjustedRange = PlayerList.playerList.get(Ui.curPlayer).rangeAdjust();

        testForShoot(adjustedDirection, adjustedAiDirection, adjustedRange);
        boolean isScoreGoal = goalCheck(adjustedAiDirection, adjustedDirection, adjustedRange);

        MatchStat.updateStat(isScoreGoal);
        PlayerList.playerList.get(Ui.curPlayer).printGoalAfterShoot(isScoreGoal, Math.round(adjustedDirection));
    }

    private static void testForShoot(float adjustedDirection, float adjustedAiDirection, float adjustedRange) {
        System.out.println("Shoot: " + adjustedDirection);
        System.out.println("Save: " + adjustedAiDirection);
        System.out.println("Range: " + adjustedRange);
    }

    public static void executeUpgrade(String[] level){
        String upgradeLevel = level[0];
        int upgradeLevelIndex = Integer.parseInt(upgradeLevel);
        PlayerList.playerList.get(Ui.curPlayer).upgradePower(upgradeLevelIndex);
        PlayerList.playerList.get(Ui.curPlayer).printSelfInfo();
    }

    //@@author ymirmeddeb
    /**
     * Logs the details of a save attempt for testing and debugging purposes.
     * This method prints the adjusted direction of the saver's attempt, the direction of the AI's shot, and the range within which a save is considered successful.
     *
     * @param adjustedDirection The adjusted direction of the saver's save attempt.
     * @param adjustedAiDirection The direction of the AI's shot.
     * @param adjustedRange The range within which a save attempt is considered successful.
     */
    private static void testForSave(float adjustedDirection, float adjustedAiDirection, float adjustedRange) {
        System.out.println("Save: " + adjustedDirection);
        System.out.println("Shoot: " + adjustedAiDirection);
        System.out.println("Range: " + adjustedRange);
    }

    //@@author ymirmeddeb
    /**
     * Checks whether a save attempt was successful based on the user's input, AI's shot direction, and the allowed range for a successful save.
     * This method asserts that both the user's input and the AI's input are within valid bounds before determining the success of the save.
     *
     * @param userInput The direction of the saver's attempt to save the goal.
     * @param aiInput The direction of the AI's shot.
     * @param range The range within which a save attempt is considered successful.
     * @return {@code true} if the save attempt is successful, {@code false} otherwise.
     */
    public static boolean saveCheck(float userInput, float aiInput, float range) {
        assert userInput >= 0 && userInput <= 8 :
                "Illegal userInput generated!";
        assert aiInput >= 0 && aiInput <= 8 :
                "Illegal aiInput generated!";
        return ((userInput>(aiInput+range))||userInput<(aiInput-range));
    }

    //@@author ymirmeddeb
    /**
     * Executes the upgrade of the current saver's power level based on the specified level.
     * This method retrieves the current saver from {@code SaverList}, upgrades their power, and then prints their updated information.
     *
     * @param level The array containing the new power level to upgrade the saver to.
     */
    public static void executeSaverUpgrade(String[] level){
        String upgradeLevel = level[0];
        int upgradeLevelIndex = Integer.parseInt(upgradeLevel);
        SaverList.saverList.get(Ui.curSaver).upgradePower(upgradeLevelIndex);
        SaverList.saverList.get(Ui.curSaver).printSelfInfo();
    }

    //@@author ymirmeddeb
    /**
     * Executes a save attempt, adjusting the direction based on the current saver's abilities, comparing it to the AI's shot direction, and determining the success of the save.
     * It then updates the match statistics and prints the outcome of the save attempt.
     *
     * @param readArgumentTokens The array containing the selected direction for the save attempt.
     */
    public static void executeSave(String[] readArgumentTokens) {
        String selectedDirection = readArgumentTokens[0];
        int selectedDirectionIndex = Integer.parseInt(selectedDirection);
        float adjustedSaveDirection = SaverList.saverList.get(Ui.curSaver).saveDirectionAdjust(selectedDirectionIndex);
        float adjustedAiDirection = SaverList.saverList.get(Ui.curSaver).aiDirectionAdjust(Ai.getAiDirection());
        float adjustedRange = SaverList.saverList.get(Ui.curSaver).rangeAdjust();
        testForSave(adjustedSaveDirection, adjustedAiDirection, adjustedRange);
        boolean isScoreGoal = goalCheck(adjustedSaveDirection, adjustedAiDirection, adjustedRange);
        MatchStat.updateStat(isScoreGoal);
        SaverList.saverList.get(Ui.curSaver).printGoalAfterSave(!isScoreGoal, Math.round(adjustedSaveDirection));
    }

    //@@author ymirmeddeb
    /**
     * Prints the commands available after a match is over
     */
    public static void executeHelpAfterMatch() {
        System.out.println("Here are the commands available");
        System.out.println("--------------------------------");
        System.out.println("    yes - If you want to start a new game.");
        System.out.println("    no - If you do not want to start a new game. Exit the game.");
        System.out.println("    bye - Exit the game.");
        System.out.println("    help - Display this help message again.");
        System.out.println("--------------------------------");
    }

    //@@author ymirmeddeb
    /**
     * Prints the commands available when you first start the game
     */
    public static void executeHelpAtStart() {
        System.out.println("Here are the commands available");
        System.out.println("--------------------------------");
        System.out.println("    head - Choose 'head' in a coin toss to decide who starts the game.");
        System.out.println("    tail - Choose 'tail' in the same coin toss.");
        System.out.println("    bye - Exit the game.");
        System.out.println("    help - Display this help message again.");
        System.out.println("--------------------------------");
    }

    //@@author ymirmeddeb
    /**
     * Prints the commands available during the game
     */
    public static void executeHelpDuringGame() {
        System.out.println("Here are the commands available");
        System.out.println("--------------------------------");
        System.out.println("    If it says 'Select direction to shoot :'");
        System.out.println("        shoot[direction] - Choose a direction to shoot.");
        System.out.println("    ----------------------------------------------------");
        System.out.println("    If it says 'Select direction to save :'");
        System.out.println("        save[direction] - Choose a direction to save.");
        System.out.println("    ----------------------------------------------------");
        System.out.println("    upgrade[power level] - Upgrade the power level of your player.");
        System.out.println("        0 - Low power level.");
        System.out.println("        1 - Medium power level.");
        System.out.println("        2 - High power level.");
        System.out.println("    ----------------------------------------------------");
        System.out.println("    easy - Set the difficulty to 'easy'.");
        System.out.println("    meduim - Set the difficulty to 'medium'.");
        System.out.println("    hard - Set the difficulty to 'hard'.");
        System.out.println("    bye - Exit the game.");
        System.out.println("    help - Display this help message again.");
        System.out.println("--------------------------------");
    }
}

