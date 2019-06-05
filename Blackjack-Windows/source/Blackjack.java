/*
       This program lets the user play Blackjack.  The computer
       acts as the dealer.  The user has a stake of $1000, and
       makes a bet on each game.  The user can leave at any time,
       or will be kicked out when he loses all the money.
 House rules:  The dealer hits on a total of 16 or less
 and stands on a total of 17 or more.  Dealer wins ties.
 A new deck of cards is used for each game.
*/
import java.awt.*;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;


public class Blackjack extends JFrame implements ActionListener{

  //GUI builders
  private ImageIcon image1;
  private JLabel label1;
  private ImageIcon image2;
  private JLabel label2;
  private JPanel panel1;
  //Blackjack hands and cards
  public static Blackjack frame;
  private static Deck deck;
  private static Card newCard;
  private static BlackjackHand userHand;
  public static BlackjackHand dealerHand;
  //buttons
  private JButton button1;
  private JButton button2;
  //did the user win
  private static boolean userWins;
  //the user statistics
  public static int money;
  public static int numtimeswon;
  public static int numtimeslost;
  public static int bet;

  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    System.out.println("Welcome to the game of blackjack.");
    System.out.println();
    money = 1000;  // User starts with $1000.
    askMoney();
  } // end main()

  public static void askMoney(){
    //beginning statements of asking for a bet
    Scanner console = new Scanner(System.in);
    System.out.println("You have $" + money + ".");
    if (money == 0) {
      //if user has no money
      System.out.println("Looks like you've are out of money!");
      System.out.println("Better luck next time");
      System.out.println();
      System.out.println("You won " + numtimeswon + " times.");
      System.out.println("Your success rate is " + (double) numtimeswon/(numtimeswon + numtimeslost) + ".");
      System.out.println("You leave with $" + money + '.');
      return;
    }
    else{
      //if user has money
      do {
        System.out.println("How many dollars do you want to bet?  (Enter 0 to leave with your money.)");
        System.out.print("? ");
        bet = console.nextInt();
        if (bet < 0 || bet > money)
          System.out.println("Your answer must be between 0 and " + money + '.');
        }
      while (bet < 0 || bet > money);
        if (bet == 0){
          //if user leaves
          if (money > 1000){
            Picture win = new Picture("win.jpg");
            win.show();
            return;
          }
          if (money < 1000){
            Picture lose = new Picture("lose.jpg");
            lose.show();
            return;
          }
          System.out.println();
          System.out.println("You won " + numtimeswon + " times.");
          System.out.println("Your success rate is " + (double) numtimeswon/(numtimeswon + numtimeslost) + ".");
          System.out.println("You leave with $" + money + '.');
        }
    }
    //if user inputs a bet then starts the game
    playBlackjack();
  }// end askMoney()

  public static void finalResults(){
    if (userWins){
      //adds money to user if user wins
      money = money + bet;
      numtimeswon ++;
    }
    else{
      //takes away money to user if user loses
      money = money - bet;
      numtimeslost ++;
    }
    System.out.println();
    //goes back to the start
    askMoney();
  }

  public static void playBlackjack() {
    Scanner console = new Scanner(System.in);
             // Let the user play one game of Blackjack
             // Return true if the user wins, false if the user loses
    deck = new Deck();
    dealerHand = new BlackjackHand();
    userHand = new BlackjackHand();
          //  Shuffle the deck, then deal two cards to each player

    deck.shuffle();
    dealerHand.addCard( deck.dealCard() );
    dealerHand.addCard( deck.dealCard() );
    userHand.addCard( deck.dealCard() );
    userHand.addCard( deck.dealCard() );

    if (dealerHand.getBlackjackValue() == 21) {
      System.out.println("Dealer has the " + dealerHand.getCard(0) + " and the " + dealerHand.getCard(1) + ".");
      System.out.println("User has the " + userHand.getCard(0) + " and the " + userHand.getCard(1) + ".");
      System.out.println();
      System.out.println("Dealer has Blackjack.  Dealer wins.");
      userWins = false;
      finalResults();
    }

    else if (userHand.getBlackjackValue() == 21) {
      System.out.println("Dealer has the " + dealerHand.getCard(0) + " and the " + dealerHand.getCard(1) + ".");
      System.out.println("User has the " + userHand.getCard(0) + " and the " + userHand.getCard(1) + ".");
      System.out.println();
      System.out.println("You have Blackjack.  You win.");
      userWins = true;
      finalResults();
    }

    else{
      frame = new Blackjack((userHand.getCard(0)).getSuitAsString(), (userHand.getCard(0)).getValueAsString());
      frame.addCard((userHand.getCard(1)).getSuitAsString(), (userHand.getCard(1)).getValueAsString());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(900,900);

      frame.setVisible(true);

      System.out.println();
      System.out.println();
    }
  }  // end playBlackjack()

  public static void findWinner(){
    //finds who one the game of blackjack
    if (userHand.getBlackjackValue() > 21) {
      System.out.println();
      System.out.println("You busted by going over 21.  You lose.");
      System.out.println("Dealer's other card was the " + dealerHand.getCard(1));
      userWins = false;
      finalResults();
      return;
    }

    else{
      System.out.println();
      System.out.println("User stands.");
      System.out.println("Dealer's cards are");
      System.out.println("    " + dealerHand.getCard(0));
      System.out.println("    " + dealerHand.getCard(1));
      while (dealerHand.getBlackjackValue() <= 16) {
        newCard = deck.dealCard();
        System.out.println("Dealer hits and gets the " + newCard);
        dealerHand.addCard(newCard);
        if (dealerHand.getBlackjackValue() > 21) {
          System.out.println();
          System.out.println("Dealer busted by going over 21.  You win.");
          userWins = true;
          finalResults();
          return;
        }
      }
      System.out.println("Dealer's total is " + dealerHand.getBlackjackValue());

      System.out.println();
      if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
        System.out.println("Dealer wins on a tie.  You lose.");
        userWins = false;
        finalResults();
        return;
      }
      else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
        System.out.println("Dealer wins, " + dealerHand.getBlackjackValue() + " points to " + userHand.getBlackjackValue() + ".");
        userWins = false;
        finalResults();
        return;
      }
      else {
        System.out.println("You win, " + userHand.getBlackjackValue() + " points to " + dealerHand.getBlackjackValue() + ".");
        userWins = true;
        finalResults();
        return;
      }
    }
  }// end findWinner()

  Blackjack(String suit1, String value1){
    //the GUI
    setLayout(new GridLayout(1, 6));

    //adds the two buttons to hit and stand
    button1 = new JButton("Hit");
    button2 = new JButton("Stand");
    add(button1);
    button1.addActionListener(this);
    add(button2);
    button2.addActionListener(this);

    //gets the files for the pictures of the cards
    String suitName1 = suit1.toUpperCase();
    String valueName1 = value1.toUpperCase();
    ImageIcon image = new ImageIcon(valueName1 + "_" + suitName1 + ".png");
    JLabel label = new JLabel(image);
    add(label);
  }// end Blackjack(String suit1, String value1)

  public void actionPerformed(ActionEvent e){
    //what happens if a button is pressed
    if(e.getSource() == button1){
      //if Hit is pressed
      getCard();
      addCard(newCard.getSuitAsString(), newCard.getValueAsString());
      userHand.addCard(newCard);
      if(userHand.getBlackjackValue() > 21){
        setVisible(false);
        findWinner();
      }
    }
    else{
      //if stand is pressed
      setVisible(false);
      findWinner();
    }
  }// end actionPerformed()

  public void addCard(String suit, String value){
    //adds card to the gui
    setVisible(false);
    String suitName = suit.toUpperCase();
    String valueName = value.toUpperCase();
    ImageIcon image = new ImageIcon(valueName + "_" + suitName + ".png");
    JLabel label = new JLabel(image);
    add(label);
    setVisible(true);
  }// end addCard()

  public static void getCard(){
    //gets Card
    newCard = deck.dealCard();
  }// end getCard()
} // end class Blackjack
