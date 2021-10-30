import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main<E extends Comparable> {

  public static void main(String[] args) { // add fighting system

    JDABuilder jdaBuilder = JDABuilder.createDefault("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    JDA jda = null;
    CommandHandler fkOff = new CommandHandler();
    jdaBuilder.addEventListeners(fkOff);

    try {
      jda = jdaBuilder.build();
    } catch (LoginException e) {
      e.printStackTrace();
    }
    jda.getPresence()
        .setActivity(
            Activity.listening(
                "the crys of the infedels i locked in in my basement and havent fed in a week because they forgot 1 of their daily prayers"));
    // jda.getPresence().setActivity(Activity.playing("New .stats and .levelup commands!!"));

  }
}
