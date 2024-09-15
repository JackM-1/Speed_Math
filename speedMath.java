/*
* speedMath.java
* Names: Jack Mackenzie and Kwab Asante
* Course: ICS4UC
* Date: 18/01/24
* Description: SpeedMath is a Java application that challenges players with rapid-fire math questions, testing the players math skills.  The ability
* to choose the difficulty, select operations, and aim for a high score as you race against the clock.
* Intended audience: individuals of all ages who enjoy math-based challenges and want to enhance their math skills in an engaging and
* fast-paced environment.
*/




import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import javax.swing.Timer;




/**
 * The main class representing the SpeedMath application.
 * Pre: None.
 * Post: The SpeedMath application is initialized and displayed.
 */
public class speedMath extends Frame {
    private PanelManager panelManager;
    /**
     * Initialize screen for the SpeedMath class.
     * Pre: None.
     * Post: The SpeedMath screen is created, and the main panel is set up.
     */
    public speedMath() {
        panelManager = new PanelManager(this);


        setSize(800, 400);
        setTitle("SpeedMath");
        setVisible(true);
        add(panelManager.getMainPanel());


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });


        panelManager.showPanel("PlayPanel");
    }
   
    /**
     * Run the SpeedMath application.
     * Pre: None.
     * Post: A new instance of SpeedMath is created.
     */
    public static void main(String args[]) {
        new speedMath();
    }
}


/**
 * Manages different panels and controls the navigation flow of the SpeedMath game.
 */
class PanelManager {
    private Panel mainPanel;
    private Panel playPanel;
    private Panel selectDifficultyPanel;
    private Panel creditsPanel;
    private Panel tutorialPanel;
    private Panel selectOperationsPanel;
    private int score;
    private String selectedDifficulty = "Medium"; // Default value
    private int num1;
    private int num2;
    private int timeAdd;
    private int timeReduce;
    private int time;
    private Timer timer;
    private int totalTimeSurvived;
    private static int highScore = 0;


    /**
     * Initializes the PanelManager with the main panels and sets up the game.
     * Pre: The frame parameter is not null.
     * Post: The main panels (Play, SelectDifficulty, SelectOperations, Credits, Tutorial) are created.
     */
    public PanelManager(Frame frame) {
        mainPanel = new Panel(new CardLayout());
        playPanel = createPlayPanel();
        selectDifficultyPanel = createSelectDifficultyPanel();
        creditsPanel = createCreditsPanel();
        tutorialPanel = createTutorialPanel();
        selectOperationsPanel = createSelectOperationsPanel();
   
        mainPanel.add(playPanel, "PlayPanel");
        mainPanel.add(selectDifficultyPanel, "SelectDifficultyPanel");
        mainPanel.add(selectOperationsPanel, "SelectOperationsPanel");
        mainPanel.add(creditsPanel, "CreditsPanel");
        mainPanel.add(tutorialPanel, "TutorialPanel");
        frame.add(mainPanel);
   
        timer = new Timer(1000, e -> {});
    }


    /**
     * Shows the specified panel using CardLayout.
     * Pre: The panelName parameter is a valid panel name.
     * Post: The specified panel is displayed in the main panel using CardLayout.
     */
    public void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, panelName);
    }


    /**
     * Retrieves the main panel managed by this PanelManager.
     * Pre: None.
     * Post: The main panel is returned.
     */
    public Panel getMainPanel() {
        return mainPanel;
    }


    /**
     * Creates a panel with the specified layout manager.
     * Pre: The layout parameter is given.
     * Post: A new panel with the specified layout manager is created and returned.
     */
    public Panel createPanelWithLayout(LayoutManager layout) {
        return new Panel(layout);
    }


    /**
     * Creates a button with the specified label and ActionListener.
     * Pre: The label and actionListener parameters are given.
     * Post: A new button with the specified label and ActionListener is created and returned.
     */
    public Button createButton(String label, ActionListener actionListener) {
        Button button = new Button(label);
        button.addActionListener(actionListener);
        return button;
    }


    /**
     * Sets the selected difficulty for the game.
     * Pre: The selected difficulty is given.
     * Post: The selected difficulty for the game is updated.
     */
    private void setDifficulty(String selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }
   
    /**
     * Creates the Play panel, the initial screen of the game.
     * Pre: None.
     * Post: The Play panel is created with play, credits, and tutorial buttons.
     */
    public Panel createPlayPanel() {
        Panel panel = createPanelWithLayout(new BorderLayout());


        Label titleLabel = new Label("Welcome to Speed Math");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignment(Label.CENTER);


        Panel buttonPanel = createPanelWithLayout(new GridLayout(3, 1, 0, 20));


        Button playButton = createButton("Play", e -> showPanel("SelectDifficultyPanel"));
        Button creditsButton = createButton("Credits", e -> showPanel("CreditsPanel"));
        Button tutorialButton = createButton("Tutorial", e -> showPanel("TutorialPanel"));


        buttonPanel.add(playButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(tutorialButton);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);


        return panel;
    }


    /**
     * Creates the Select Difficulty panel, allowing the player to choose the game difficulty.
     * Pre: None.
     * Post: The Select Difficulty panel is created with easy, medium, and hard difficulty buttons and a return to menu button.
     */
    public Panel createSelectDifficultyPanel() {
        Panel panel = createPanelWithLayout(new BorderLayout());


        Label titleLabel = new Label("Select Difficulty");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignment(Label.CENTER);


        Panel difficultyButtonPanel = createPanelWithLayout(new GridLayout(3, 1));


        Button easyButton = createButton("Easy", e -> {
            setDifficulty("Easy");
            showPanel("SelectOperationsPanel");
        });


        Button mediumButton = createButton("Medium", e -> {
            setDifficulty("Medium");
            showPanel("SelectOperationsPanel");
        });


        Button hardButton = createButton("Hard", e -> {
            setDifficulty("Hard");
            showPanel("SelectOperationsPanel");
        });


        Button backToMenuButton = createButton("Back to Menu", e -> showPanel("PlayPanel"));


        difficultyButtonPanel.add(easyButton);
        difficultyButtonPanel.add(mediumButton);
        difficultyButtonPanel.add(hardButton);


        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(difficultyButtonPanel, BorderLayout.CENTER);
        panel.add(backToMenuButton, BorderLayout.SOUTH);


        return panel;
    }


    /**
     * Creates the Select Operations panel, allowing the player to choose the math operations.
     * Pre: None.
     * Post: The Select Operations panel is created with addition, subtraction, and multiplication buttons.
     */
    public Panel createSelectOperationsPanel() {
        Panel panel = createPanelWithLayout(new BorderLayout());


        Label titleLabel = new Label("Select Operations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignment(Label.CENTER);


        Panel operationsButtonPanel = createPanelWithLayout(new GridLayout(3, 1));
        Button additionButton = createButton("Addition", e -> showGamePanel("Addition"));
        Button subtractionButton = createButton("Subtraction", e -> showGamePanel("Subtraction"));
        Button multiplicationButton = createButton("Multiplication", e -> showGamePanel("Multiplication"));
        Button backToMenuButton = createButton("Back to Menu", e -> showPanel("PlayPanel"));


        operationsButtonPanel.add(additionButton);
        operationsButtonPanel.add(subtractionButton);
        operationsButtonPanel.add(multiplicationButton);


        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(operationsButtonPanel, BorderLayout.CENTER);
        panel.add(backToMenuButton, BorderLayout.SOUTH);


        return panel;
    }


    /**
     * Creates the Credits panel, displaying information about the creators and thanking the player.
     * Pre: None.
     * Post: The Credits panel is created with information about the creators of the game and a return to menu button.
     */
    public Panel createCreditsPanel() {
        Panel panel = createPanelWithLayout(new BorderLayout());


        Label titleCreditsLabel = new Label("Credits");
        titleCreditsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleCreditsLabel.setAlignment(Label.CENTER);


        Label[] creditsLabels = {
                new Label("Coded by: "),
                new Label("Kwabena Asante & Jack Mackenzie"),
                new Label(" "),
                new Label("Developed by:"),
                new Label("Kwabena Asante & Jack Mackenzie"),
                new Label(" "),
                new Label("Thank you for playing!")
        };


        Panel creditsPanel = createPanelWithLayout(new GridLayout(creditsLabels.length, 1));


        for (Label label : creditsLabels) {
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setAlignment(Label.LEFT);
            creditsPanel.add(label);
        }


        Button returnButton = createButton("Return to Main Menu", e -> showPanel("PlayPanel"));


        panel.add(titleCreditsLabel, BorderLayout.NORTH);
        panel.add(creditsPanel, BorderLayout.CENTER);
        panel.add(returnButton, BorderLayout.SOUTH);


        return panel;
    }


    /**
     * Creates the Tutorial panel, providing instructions on how to play the game.
     * Pre: None.
     * Post: The Tutorial panel is created with instructions on playing the game and a return to menu button.
     */
    public Panel createTutorialPanel() {
        Panel panel = createPanelWithLayout(new BorderLayout());


        Label tutorialTitleLabel = new Label("Speed Math Tutorial");
        tutorialTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tutorialTitleLabel.setAlignment(Label.CENTER);


        Label[] tutorialLabels = {
                new Label("Welcome to Speed Math Tutorial!"),
                new Label(""),
                new Label("Speed Math is a game where you'll be solving rapid-fire math questions within a time limit."),
                new Label(""),
                new Label("Instructions:"),
                new Label("- Choose a game type (Addition, Subtraction, Multiplication)."),
                new Label("- Select the difficulty level."),
                new Label("- Solve the math questions as fast as you can!"),
                new Label("- For each correct answer, you'll gain time. For each wrong answer, time will be deducted."),
                new Label(""),
                new Label("Have fun and challenge yourself!")
        };


        for (Label label : tutorialLabels) {
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setAlignment(Label.LEFT);
        }


        Button backButton = createButton("Back to Main Menu", e -> showPanel("PlayPanel"));
        Panel tutorialTextPanel = createPanelWithLayout(new GridLayout(tutorialLabels.length, 1));


        for (Label label : tutorialLabels) {
            tutorialTextPanel.add(label);
        }


        panel.add(tutorialTitleLabel, BorderLayout.NORTH);
        panel.add(tutorialTextPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);


        return panel;
    }


    /**
     * Creates the game panel for the specified operation (addition, subtraction, multiplication).
     * Pre: The operation parameter is a valid math operation.
     * Post: The game panel is created with a question label, input field, and buttons. The player answers math questions and earn scores and time.
     */
    public Panel createGamePanel(String operation) {
        Panel panel = createPanelWithLayout(new BorderLayout());
   
        // Reset the score when creating a new game panel
        score = 0;
   
        // Reset the time survived
        totalTimeSurvived = 0;
   
        // Stop and reset the timer
        if (timer != null) {
            timer.stop();
            timer = null;
        }
   
        Label questionLabel = new Label(generateQuestion(operation));
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        questionLabel.setAlignment(Label.CENTER);
        questionLabel.setPreferredSize(new Dimension(300, 30));
   
        TextField answerField = new TextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        answerField.setPreferredSize(new Dimension(50, 30));
   
        Label resultLabel = new Label("");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setAlignment(Label.CENTER);
   
        Label scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setAlignment(Label.LEFT);
   
        Label timeLabel = new Label("Time: 000");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timeLabel.setAlignment(Label.RIGHT);
   
        answerField.addActionListener(e -> {
            processAnswer(operation, questionLabel, resultLabel, answerField, scoreLabel, timeLabel);
        });
   
        Button submitButton = createButton("Submit", e -> {
            processAnswer(operation, questionLabel, resultLabel, answerField, scoreLabel, timeLabel);
        });
   
        Panel inputPanel = createPanelWithLayout(new FlowLayout());
        inputPanel.add(questionLabel);
        inputPanel.add(answerField);
        inputPanel.add(submitButton);
   
        Panel topPanel = createPanelWithLayout(new BorderLayout());
        topPanel.add(scoreLabel, BorderLayout.WEST);
        topPanel.add(timeLabel, BorderLayout.EAST);
   
        panel.add(resultLabel, BorderLayout.SOUTH);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
   
        initializeDifficulty(operation);
   
        //Creates a new timer
        timer = new Timer(1000, e -> updateTime(timeLabel, resultLabel));
        timer.start();
   
        return panel;
    }
   
    /**
     * Displays the game over panel with the final score, time survived, and high score.
     * Pre: None.
     * Post: The game over panel is displayed with the final score, time survived, and high score.
     */
    private Panel createEndPanel(int finalScore, int totalTimeSurvived) {
        Panel panel = createPanelWithLayout(new BorderLayout());


        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gameOverLabel.setAlignment(Label.CENTER);


        Label scoreLabel = new Label("Your Score: " + finalScore);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setAlignment(Label.CENTER);


        Label timeLabel = new Label("Time Survived: " + totalTimeSurvived + " seconds");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setAlignment(Label.CENTER);


        Label highScoreLabel = new Label("High Score: " + highScore); // Display the high score
        highScoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        highScoreLabel.setAlignment(Label.CENTER);


        Button returnButton = createButton("Return to Main Menu", e -> showPanel("PlayPanel"));


        // Create a new panel for the center area
        Panel centerPanel = createPanelWithLayout(new GridLayout(3, 1));
        centerPanel.add(scoreLabel);
        centerPanel.add(timeLabel);
        centerPanel.add(highScoreLabel);


        panel.add(gameOverLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(returnButton, BorderLayout.SOUTH);


        updateHighScore(finalScore); // Update the high score


        return panel;
    }
   
    /**
     * Updates the time label and checks for game over conditions.
     * Pre: The timeLabel and resultLabel parameters are given.
     * Post: The time label is updated, and the game over conditions are checked.
     */
    private void updateTime(Label timeLabel, Label resultLabel) {
        SwingUtilities.invokeLater(() -> {
            time--;
            totalTimeSurvived++; // Increment totalTimeSurvived
            timeLabel.setText("Time: " + time);
   
            if (time <= 0) {
                timer.stop();
                showEndPanel(); // Fix the method call
                resultLabel.setText("Game Over!");
            }
        });
    }
   
    /**
     * Handles the end of a game session by displaying the final score and time survived.
     *
     * Pre: None.
     * Post: The end panel is created with the final score, time survived, and high score.
     * The player can return to the main menu from here.
     */
    private void showEndPanel() {
        SwingUtilities.invokeLater(() -> {
            Panel endPanel = createEndPanel(score, totalTimeSurvived);
            mainPanel.add(endPanel, "EndPanel");
            showPanel("EndPanel");
        });
    }


    /**
     * Initializes the game difficulty settings based on the selected difficulty.
     * Pre: The operation parameter is a valid math operation.
     * Post: The time, timeAdd, and timeReduce values are set based on the selected difficulty.
     */
    private void initializeDifficulty(String operation) {
        switch (selectedDifficulty) {
            case "Easy":
                time = 90;
                timeAdd = 4;
                timeReduce = 1;
                break;
            case "Medium":
                time = 60;
                timeAdd = 2;
                timeReduce = 2;
                break;
            case "Hard":
                time = 30;
                timeAdd = 1;
                timeReduce = 4;
                break;
            default:
                time = 1;
                break;
        }
    }


    /**
     * Generates a math question based on the selected operation and difficulty.
     * Pre: The operation parameter is a valid math operation.
     * Post: A math question is generated based on the selected operation and difficulty.
     */
    private String generateQuestion(String operation) {
        setRandomNumbers(operation);
        switch (operation) {
            case "Addition":
                return "What is " + num1 + " + " + num2 + "?";
            case "Subtraction":
                return "What is " + Math.max(num1, num2) + " - " + Math.min(num1, num2) + "?";
            case "Multiplication":
                return "What is " + num1 + " * " + num2 + "?";
            default:
                return "";
        }
    }


    /**
     * Sets random numbers for the math question based on the selected difficulty.
     * Pre: The operation parameter is a valid math operation.
     * Post: Random numbers (num1 and num2) are set based on the selected difficulty.
     */
    private void setRandomNumbers(String operation) {
        switch (selectedDifficulty) {
            case "Easy":
                num1 = (int) (11 * (Math.random()));
                num2 = (int) (11 * (Math.random()));
                break;
            case "Medium":
                num1 = (int) (21 * (Math.random()) - 5);
                num2 = (int) (21 * (Math.random()) - 5);
                break;
            case "Hard":
                num1 = (int) (31 * (Math.random()) - 25);
                num2 = (int) (31 * (Math.random()) - 25);
                break;
            default:
                num1 = 1;
                num2 = 1;
                break;
        }
    }


    /**
     * Updates the high score if the final score surpasses the current high score.
     * Pre: None.
     * Post: The high score is updated if the final score is greater than the current high score.
     */
    private void updateHighScore(int finalScore) {
        if (finalScore > highScore) {
            highScore = finalScore;
        }
    }


    /**
     * Calculates the correct answer for a math question.
     * Pre: The operation and question parameters are valid.
     * Post: The correct answer for the math question is calculated.
     */
    private int calculateCorrectAnswer(String operation, String question) {
        switch (operation) {
            case "Addition":
                return num1 + num2;
            case "Subtraction":
                return Math.max(num1, num2) - Math.min(num1, num2);
            case "Multiplication":
                return num1 * num2;
            default:
                return 0;
        }
    }


    /**
     * Shows the next math question and resets the result label.
     * Pre: The operation parameter is a valid math operation.
     * Post: The next math question is displayed, and the result label is reset.
     */
    private void showNextQuestion(String operation, Label questionLabel, Label resultLabel) {
        SwingUtilities.invokeLater(() -> {
            questionLabel.setText(generateQuestion(operation));
            resultLabel.setText("");
        });
    }


    /**
     * Updates the player's score based on the correctness of their answer.
     * Pre: None.
     * Post: The player's score is updated based on the correctness of their answer.
     */
    private void updateScore(boolean isCorrect) {
        if (isCorrect) {
            score += 10;
        } else {
            // Adjust scoring for incorrect answers if needed
        }
    }


    /**
     * Displays the game panel for the specified mathematical operation.
     * Pre: The operation parameter is a valid mathematical operation (Addition, Subtraction, Multiplication).
     * Post: Creates and displays a new game panel with the specified mathematical operation.
     * The game panel includes a question, input field, and buttons for user interaction.
     */
    private void showGamePanel(String operation) {
        SwingUtilities.invokeLater(() -> {
            Panel gamePanel = createGamePanel(operation);
            mainPanel.add(gamePanel, "GamePanel");
            showPanel("GamePanel");
        });
    }


    /**
     * Checks if the user-provided answer matches the correct answer for the current question.
     * Pre: The userAnswer parameter is not null.
     * Post: Returns true if the user's answer is correct, false otherwise.
     * The method handles NumberFormatException if the user's answer is not a valid integer.
     */
    private boolean isUserAnswerCorrect(String userAnswer, int correctAnswer) {
        try {
            int userProvidedAnswer = Integer.parseInt(userAnswer);
            return userProvidedAnswer == correctAnswer;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    /**
     * Processes the player's answer by updating the score, time, and displaying the next question.
     * Pre: The operation, questionLabel, resultLabel, answerField, scoreLabel, and timeLabel parameters are given.
     * Post: The player's answer is processed, and the game state is updated accordingly.
     */
    private void processAnswer(String operation, Label questionLabel, Label resultLabel,TextField answerField, Label scoreLabel, Label timeLabel) {
        String userAnswer = answerField.getText();
        int correctAnswer = calculateCorrectAnswer(operation, questionLabel.getText());


        if (isUserAnswerCorrect(userAnswer, correctAnswer)) {
            updateScore(true);
            time += timeAdd;
        } else {
            updateScore(false);
            time -= timeReduce;
        }


        resultLabel.setText("");
        showNextQuestion(operation, questionLabel, resultLabel);


        scoreLabel.setText("Score: " + score);
        answerField.setText("");
    }
}


