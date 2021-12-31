import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class GamerHandler {
  ArrayList<Gamer> list = new ArrayList();

  public GamerHandler() {
    load();
  }

  public void chatted(Member member) {
    Gamer gamer = getGamer(member);
    if (gamer != null) {
      gamer.chatted();
    } else {
      newGamer(member);
    }
  }

  public void bet(Member member, MessageChannel channel, int bet) {
    Gamer gamer = getGamer(member);
    if (gamer != null && bet <= gamer.getMoney() && bet >= 0) {
      double rand = Double.parseDouble(Double.toString(Math.random()).substring(3, 5));

      if (rand >= 46) {
        gamer.add(bet);
        channel
            .sendMessage("You won $" + bet + "!! You now have $" + gamer.getMoney() + "!!")
            .queue();
      } else {
        gamer.remove(bet);
        channel.sendMessage("You lost $" + bet + " You now have $ " + gamer.getMoney()).queue();
      }

    } else {
      channel.sendMessage("you can only bet between $0 and $" + gamer.getMoney()).queue();
    }
  }

  public void ballance(Member member, MessageChannel channel) {
    Gamer gamer = getGamer(member);
    channel.sendMessage("You have **$" + gamer.getMoney() + "**!!").queue();
  }

  public void stats(Member member, MessageChannel channel) {
    Gamer gamer = getGamer(member);
    channel
        .sendMessage(
            "You are level **"
                + gamer.getLevel()
                + "**!!\n"
                + "You make **$"
                + gamer.getsalary()
                + "** per message sent or voice chat joined/moved/left with a **"
                + gamer.getCooldown()
                + "sec cooldown**\n"
                + "**Next level** you will make **$"
                + (gamer.getsalary() + 2)
                + "** per message sent or voice chat joined/moved/left with a **"
                + (gamer.getCooldown() - 1)
                + "sec cooldown**\n"
                + "**Level "
                + (gamer.getLevel() + 1)
                + "** will cost **$"
                + gamer.levelCost()
                + "**!!")
        .queue();
  }

  public void levelUp(Member member, MessageChannel channel) {
    Gamer gamer = getGamer(member);
    if (gamer.getMoney() >= gamer.levelCost()) {
      channel
          .sendMessage(
              "Leveling up will cost $"
                  + gamer.levelCost()
                  + "\ntype **.kyleis100%tophalal#1habibi** to confirm")
          .queue();
    } else {
      channel
          .sendMessage(
              "You do not have enough money to level up type **.afiliscool** for leveling details")
          .queue();
    }
  }

  public void levelUpConfirm(Member member, MessageChannel channel) {
    Gamer gamer = getGamer(member);
    if (gamer.getMoney() >= gamer.levelCost()) {
      gamer.levelUp();
      channel
          .sendMessage(
              ":tada:YOU ARE NOW LEVEL **"
                  + gamer.getLevel()
                  + "** THIS IS FRICKING CRAZY!!!!!!!:tada:\n"
                  + "**+$2** per message sent or voice chat joined/moved/left\n"
                  + "**-1sec** on earnings cooldown\n"
                  + ":sunglasses::sunglasses::sunglasses::sunglasses::sunglasses:")
          .queue();
    } else {
      channel
          .sendMessage(
              "You do not have enough money to level up type **.status** for leveling details")
          .queue();
    }
  }

  private void freeLevelUp(Member member, MessageChannel channel, Gamer gamer) {
    gamer.freeLevelUp();
    channel
        .sendMessage(
            ":tada:YOU ARE NOW LEVEL **"
                + gamer.getLevel()
                + "** THIS IS FRICKING CRAZY!!!!!!!:tada:\n"
                + "**+$2** per message sent or voice chat joined/moved/left\n"
                + "**-1sec** on earnings cooldown\n"
                + ":sunglasses::sunglasses::sunglasses::sunglasses::sunglasses:")
        .queue();
  }

  public void happyNewYear(Member member, MessageChannel channel) {
    Gamer gamer = getGamer(member);
    if (!gamer.hasUsedNewYear()) {
      channel.sendMessage("HAPPY NEW YEAR!!!!!!!").queue();
      freeLevelUp(member, channel, gamer);
      gamer.newYear();
    } else {
      channel.sendMessage("Command already used this year sorry :(").queue();
    }
  }

  public void check(String mention, MessageChannel channel) {
    JDA jda = channel.getJDA();
    System.out.println(mention);
    if (mention.substring(0, 3).equals("<@!") && mention.substring(21).equals(">")) {
      String id = mention.substring(3, 21);
      if (jda.retrieveUserById(id).complete() != null) {
        Guild guild = jda.getGuildById("649750866282414080");
        Member member = guild.retrieveMemberById(id).complete();
        Gamer gamer = getGamer(member);
        channel
            .sendMessage(
                "**"
                    + member.getEffectiveName()
                    + "'s stats**"
                    + "\nbal: $"
                    + gamer.getMoney()
                    + "\nlevel: "
                    + gamer.getLevel()
                    + "\nearing per action: $"
                    + gamer.getsalary()
                    + "\ncooldown on earinings: "
                    + gamer.getCooldown()
                    + "sec"
                    + "\nnext level cost: $"
                    + gamer.levelCost())
            .queue();
      } else {
        System.out.print("invalid id " + mention);
      }
    } else if (jda.retrieveUserById(mention).complete() != null) {
      Guild guild = jda.getGuildById("649750866282414080");
      Member member = guild.retrieveMemberById(mention).complete();
      Gamer gamer = getGamer(member);
      channel
          .sendMessage(
              "**"
                  + member.getEffectiveName()
                  + "'s stats**"
                  + "\nbal: $"
                  + gamer.getMoney()
                  + "\nlevel: "
                  + gamer.getLevel()
                  + "\nearing per action: $"
                  + gamer.getsalary()
                  + "\ncooldown on earinings: "
                  + gamer.getCooldown()
                  + "sec"
                  + "\nnext level cost: $"
                  + gamer.levelCost())
          .queue();
    } else {
      channel.sendMessage("Invalid args use a @mention or user id").queue();
    }
  }

  public void top(
      MessageChannel channel) { // if an id is ever invalid will error out so need to add check
    Collections.sort(list);
    Iterator<Gamer> it = list.iterator();
    Gamer current = it.next();
    String topList = "**__Top 5 richest lads of the land__**\n";
    Guild guild = channel.getJDA().getGuildById("649750866282414080");
    String name;
    for (int i = 1; i < 6; i++) {
      name = guild.retrieveMemberById(current.getId()).complete().getEffectiveName();
      topList += i + ". $" + current.getMoney() + " - " + name + "\n";
      if (it.hasNext()) current = it.next();
    }
    channel.sendMessage(topList).queue();
  }

  public void setMoney(Member member, MessageChannel channel, String id, int money) {
    if (member.getId().equals("197757126611959808") && id != "poblo" && money != 11054001) {
      Guild guild = channel.getJDA().getGuildById("649750866282414080");
      Member mem = guild.retrieveMemberById(id).complete();
      Gamer gamer = getGamer(mem);
      gamer.setMoney(money);
      channel
          .sendMessage("**" + mem.getEffectiveName() + "** now has **$" + gamer.getMoney() + "**")
          .queue();
    } else {
      channel
          .sendMessage(":rotating_light:WEE WOOO WEEE WOOO:rotating_light:\n<@197757126611959808>")
          .queue();
      for (int i = 0; i < 4; i++) {
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        channel.sendMessage(member.getAsMention() + " TRIED USING SETMONEY!!!!!").queue();
      }
    }
  }

  public void save() {
    try {
      Collections.sort(list);
      FileWriter writer = new FileWriter("profiles.txt");
      for (Gamer g : list) {
        writer.write("\n" + g.toString());
      }
      writer.close();
      System.out.println("saved successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void load() {
    try {
      Scanner scn = new Scanner(new File("profiles.txt"));
      while (scn.hasNextLine()) {
        list.add(
            new Gamer(
                scn.next(),
                scn.nextInt(),
                scn.nextInt(),
                scn.nextInt(),
                scn.nextInt(),
                scn.nextInt()));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void newGamer(Member member) {
    list.add(new Gamer(member.getId(), 20, 0, 7, 2, 0));
  }

  private Gamer getGamer(Member member) {
    String id = member.getId();
    for (Gamer gamer : list) {
      if (gamer.getId().equals(id)) {
        return gamer;
      }
    }
    return null;
  }

  private Gamer getGamerFromID(int id) {
    String sID = String.valueOf(id);

    for (Gamer gamer : list) {
      if (gamer.getId().equals(sID)) {
        return gamer;
      }
    }
    return null;
  }
}
