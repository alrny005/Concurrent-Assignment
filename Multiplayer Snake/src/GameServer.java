import java.util.concurrent.*;

public class GameServer {

    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i=0; i < 2; i++){
            Runnable thread = new Runnable(){
                public void run(){
                    System.out.println("check for collisions, check if games ended, update score" +
                            "maybe dont do this > something with players? handle user input?");
                }
            };
            executor.execute(thread);
        }

        executor.shutdown();

        while (!executor.isTerminated()){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("done");
    }
}