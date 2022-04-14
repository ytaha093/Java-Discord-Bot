import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main<E extends Comparable<E>> {
 
	public static void main(String[] args) {

		run();

	}

	private static void run() {
		System.out.print("Enter Discord Bot Token:");
		Scanner kbd = new Scanner(System.in);
		String token = kbd.nextLine();

		JDABuilder jdaBuilder = JDABuilder.createDefault(token);

		JDA jda;
		CommandHandler handler = new CommandHandler();
		jdaBuilder.addEventListeners(handler);

		try {
			jda = jdaBuilder.build();
		} catch (LoginException e) {
			System.out.println("\n\n\n\n\n\n\n\n\n");
			System.err.println("Error: Invalid Token");
			run();
		}

		kbd.close();
	}

}