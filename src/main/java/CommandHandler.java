import java.util.List;
import java.util.Scanner;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
  GamerHandler betgame = new GamerHandler();

  @Override
  public void onMessageReceived(MessageReceivedEvent e) {
    Message msg = e.getMessage();
    String sMsg = msg.getContentRaw();
    boolean isBot = e.getAuthor().isBot();

    boolean isRightChannel = true;

    if (!isBot && !isRightChannel) betgame.chatted(e.getMember());

    boolean isCommand = (sMsg.length() > 0 && sMsg.substring(0, 1).equals(".")) ? true : false;

    if (!isRightChannel && isCommand && !isBot) {
      e.getChannel().sendMessage("commands can only be executed in <#655206189814120498>").queue();
    } else if (isRightChannel && isCommand && !isBot) {
      commandRunner(msg);
    }
  }

  private void commandRunner(Message msg) {
    Member member = msg.getMember();
    MessageChannel channel = msg.getChannel();
    Scanner scn = new Scanner(msg.getContentRaw());
    String command = scn.next();
    int arg = 11054001;
    if (scn.hasNextInt()) arg = scn.nextInt();
    String argS = "poblo";
    if (scn.hasNext()) argS = scn.next();

    switch (command) {
      case ".pyramid":
        pyramid(channel, arg);
        break;
      case ".bet":
        betgame.bet(member, channel, arg);
        break;
      case ".bal":
        betgame.ballance(member, channel);
        break;
      case ".top":
        betgame.top(channel);
        break;
      case ".stats":
        betgame.stats(member, channel);
        break;
      case ".check":
        betgame.check(argS, channel);
        break;
      case ".levelup":
        betgame.levelUp(member, channel);
        break;
      case ".afiliscool":
        betgame.levelUpConfirm(member, channel);
        break;
      case ".happynewyear":
        betgame.happyNewYear(member, channel);
        break;
      case ".setmoney":
        betgame.setMoney(member, channel, argS, arg);
        break;
      case ".shutdown":
        shutdown(msg);
        break;
      case ".save":
        betgame.save();
        channel.sendMessage("Save successful").queue();
        break;
      case ".help":
        channel
            .sendMessage(
                "__**Available commmands**__\n\n"
                    + "**.stats** will display current and next level stats along with leveling costs\n"
                    + "**.check [@mention or user id]** show the stats of another user\n"
                    + "**.levelup** will level you up\n"
                    + "**.bal** check how much money you have\n"
                    + "**.bet [ammount you wish to bet]** bet your money in a double or nothing\n"
                    + "**.top** see the top 5 richest dudes of the land\n"
                    + "**.pyramid [number of layers]** I will build you a nice pyramid\n"
                    + "**.save** force a save \n"
                    + "**.shutdown** shut the bot down (Commission+ Only)\n\n"
                    + ":tada:**.happynewyear** will give you a little gift can only be used once:tada:")
            .queue();
        break;
      default:
        channel
            .sendMessage("Invalid command type **.help** for a list of available commmands")
            .queue();
        break;
    }
  }

  private void shutdown(Message msg) {
    boolean authorized = false;
    List<Role> roles = msg.getMember().getRoles();
    for (Role role : roles) {
      if (role.getId().equals("751870583289020576")) authorized = true;
    }

    if (authorized) {
      msg.getChannel().sendMessage("Shuting down :(").queue();
      betgame.save();
      msg.getJDA().shutdown();
    } else {
      msg.getChannel()
          .sendMessage("Only members of the commission are authorized to shutdown this bot")
          .queue();
    }
  }

  private void pyramid(MessageChannel channel, int height) {
    if (height <= 20 && height > 0) {
      String toPrint = "";
      int h = height;
      for (int i = 0; i < h; i++) {
        String stars = "*";
        for (int s = 0; s < i; s++) {
          stars += "***";
        }
        String toPring = String.format("%" + ((h) + (i * 2)) + "s%n", stars);
        toPrint += "\n" + toPring;
      }
      channel.sendMessage("```fix" + toPrint + "```").queue();
    } else {
      channel.sendMessage("Height must be between 1 and 20").queue();
    }
  }

  //    @Override
  //    public void onGuildVoiceJoin(GuildVoiceJoinEvent e) {
  //        TextChannel textChannel = e.getGuild().getTextChannelById("655206189814120498");
  //        textChannel.sendMessage(
  //                "**------------------**" +
  //                "\nMember getEffectiveName of gamer who just joined vc: **" +
  // e.getMember().getEffectiveName() + "**" +
  //                "\nUser getAsTag of gamer who just joined vc: **" +
  // e.getMember().getUser().getAsTag() + "**" +
  //                "\nMember getId of gamer who just joined vc: **" + e.getMember().getId() + "**"
  // +
  //                "\nUser getEffectiveAvatarUrl of gamer who just joined vc: " +
  // e.getMember().getUser().getEffectiveAvatarUrl()
  //        ).queue();
  //    }

  @Override
  public void onGuildVoiceJoin(GuildVoiceJoinEvent e) {
    betgame.chatted(e.getMember());
  }

  @Override
  public void onGuildVoiceMove(GuildVoiceMoveEvent e) {
    betgame.chatted(e.getMember());
  }

  @Override
  public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
    betgame.chatted(e.getMember());
  }
  //    @Override
  //    public void onUserTyping( UserTypingEvent event) {
  //        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
  //        MessageChannel channel = event.getChannel();
  //        User user = event.getUser();
  //        Member member = event.getMember();
  //        channel.sendMessage("This is thing getEffectiveName " +
  // member.getEffectiveName()).queue();
  //        channel.sendMessage("This is thing getName " + user.getName()).queue();
  //        channel.sendMessage("This is thing getAsTag " + user.getAsTag()).queue();
  //        channel.sendMessage("This is thing getAvatarId " + user.getAvatarId()).submit();
  //        channel.sendMessage("This is thing getAvatarUrl " + user.getAvatarUrl()).queue();
  //        channel.sendMessage("This is thing getDefaultAvatarId " +
  // user.getDefaultAvatarId()).queue();
  //        channel.sendMessage("This is thing getDefaultAvatarUrl " +
  // user.getDefaultAvatarUrl()).queue();
  //        channel.sendMessage("This is thing getDefaultAvatarId " +
  // user.getDefaultAvatarId()).queue();
  //        channel.sendMessage("This is thing getDiscriminator " +
  // user.getDiscriminator()).queue();
  //        channel.sendMessage("This is thing getEffectiveAvatarUrl " +
  // user.getEffectiveAvatarUrl()).queue();
  //        channel.sendMessage("This is thing getFlagsRaw " + user.getFlagsRaw()).queue();
  //    }

  @Override
  public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
    if (!event.getAuthor().isBot()) {
      event
          .getChannel()
          .sendMessage(
              "I'm in development please go msg this sexy sonofabich <@197757126611959808>")
          .queue();
    }
  }
}
