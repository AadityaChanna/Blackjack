public class StartBlackjack{
  public static void main(String[] args) {
    try{
         Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"java Blackjack\"");
        }
        catch (Exception e){
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }
  }
}
