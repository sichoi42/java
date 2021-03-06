import java.util.*;

public class OneCard
{
	static ArrayList<Card> Cardset = new ArrayList<Card>();
	static ArrayList<Card> turned_in = new ArrayList<Card>();
	static Scanner sc = new Scanner(System.in);
	static Random rand = new Random();
	static int turn = 0;
	static int vector = 1;
	static int total_damage = 0;
	static boolean after_atack = false;
	static String next_type;

	static void bulid_Cardset()
	{
		int i;

		Cardset.clear();
		Cardset.add(new Card("spade","A"));
		Cardset.add(new Card("dia","A"));
		Cardset.add(new Card("heart","A"));
		Cardset.add(new Card("clover","A"));
		for (i=2;i<=10;i++)
			Cardset.add(new Card("spade", Integer.toString(i)));
		for (i=2;i<=10;i++)
			Cardset.add(new Card("dia", Integer.toString(i)));
		for (i=2;i<=10;i++)
			Cardset.add(new Card("heart", Integer.toString(i)));
		for (i=2;i<=10;i++)
			Cardset.add(new Card("clover", Integer.toString(i)));
		Cardset.add(new Card("spade","K"));
		Cardset.add(new Card("dia","K"));
		Cardset.add(new Card("heart","K"));
		Cardset.add(new Card("clover","K"));
		Cardset.add(new Card("spade","Q"));
		Cardset.add(new Card("dia","Q"));
		Cardset.add(new Card("heart","Q"));
		Cardset.add(new Card("clover","Q"));
		Cardset.add(new Card("spade","J"));
		Cardset.add(new Card("dia","J"));
		Cardset.add(new Card("heart","J"));
		Cardset.add(new Card("clover","J"));
		Cardset.add(new Card("Joker","Joker"));
	}

	static void give_cards_to_users(User u)
	{
		int index;
		String type;
		String num;
		int i;
		for(i=0;i<7;i++)
		{
			index = rand.nextInt(Cardset.size() - 1);
			type = Cardset.get(index).type;
			num = Cardset.get(index).num;
			Cardset.remove(index);
			u.u_deck.add(new Card(type,num));
		}
	}

	static void shuffle_card()
	{
		String type, num;

		for (int i=0;i<turned_in.size() - 1;i++)
		{
			type = turned_in.get(i).type;
			num = turned_in.get(i).num;
			turned_in.remove(i);
			Cardset.add(new Card(type, num));
		}
	}
	public static void main(String[] args)
	{
		User p = new User("player");
		User com1 = new User("com1");
		User com2 = new User("com2");
		System.out.printf("?????? ?????????: %s %s %s\n",p.name, com1.name, com2.name);
		bulid_Cardset();
        give_cards_to_users(p);
		give_cards_to_users(com1);
		give_cards_to_users(com2);
		System.out.println("?????? ?????? ??????. ????????? ???????????????.");
		int index = rand.nextInt(Cardset.size() - 1);
		String type = Cardset.get(index).type;
		String num = Cardset.get(index).num;
		Cardset.remove(index);
		turned_in.add(new Card(type, num));
		next_type = turned_in.get(0).type;
        after_atack = true;
		while (true)
		{
			int open_idx = turned_in.size() - 1;
			if (turned_in.get(open_idx).type.equals("Joker"))
                System.out.printf("????????? ????????? ?????? : %s\n", turned_in.get(open_idx).type);
            else
                System.out.printf("????????? ????????? ?????? : %s %s\n", turned_in.get(open_idx).type, turned_in.get(open_idx).num);
            if (after_atack == true && turned_in.get(open_idx).type.equals("Joker"))
                System.out.println("??? ??? ?????? ?????? : ?????? ??????");
            else if (after_atack == false && turned_in.get(open_idx).type.equals("Joker"))
                ;
            else
                System.out.printf("??? ??? ?????? ?????? : %s\n", next_type);//7?????? ??????
			if (turn % 3 == 0)//player ???
			{
                System.out.printf("\n%s ???\n", p.name);
				if (p.check_have_can_turn_in(p) == false)
					p.draw_cards(p);
				else
					p.player_select(p);
			}
			else if (turn % 3 == 1 || turn % 3 == -2)//com1 ???
			{
				System.out.printf("\n%s ???\n", com1.name);
				if (com1.check_have_can_turn_in(com1) == false)
					com1.draw_cards(com1);
				else
					com1.computer_select(com1);
			}
			else if (turn % 3 == 2 || turn % 3 == -1)//com2 ???
			{
				System.out.printf("\n%s ???\n", com2.name);
				if (com2.check_have_can_turn_in(com2) == false)
					com2.draw_cards(com2);
				else
					com2.computer_select(com2);
			}
			turn += vector;
			if (p.u_deck.size() == 0 || com1.u_deck.size() == 0 || com2.u_deck.size() == 0)
				break;
		}
		if (p.u_deck.size() == 0)
			System.out.printf("%s win!\n", p.name);
		else if (com1.u_deck.size() == 0)
			System.out.printf("%s win!\n", com1.name);
		else
			System.out.printf("%s win!\n", com2.name);
	}
}

class Card
{
	String type;
	String num;
	int damage;

	Card(String type, String num)
	{
		this.type=type;
		this.num=num;
		switch(this.num)
		{
			case "A":
			this.damage = 3;
			break;
			case "2":
			this.damage = 2;
			break;
			case "Joker":
			this.damage = 5;
			break;
			default:
			this.damage = 0;
			break;
		}
	}
}

class User
{
	ArrayList<Card> u_deck = new ArrayList<Card>();
	String name;
    User(String name)
	{
       this.name = name;
	}
	boolean check_have_can_turn_in(User u)
	{
		int count;
		int open_card = OneCard.turned_in.size() - 1;
		String type = OneCard.next_type;
		String num = OneCard.turned_in.get(open_card).num;
		int damage = OneCard.turned_in.get(open_card).damage;

		count=0;
		if (OneCard.after_atack == true)
		{
			damage = 0;
			if (type.equals("Joker"))
				count++;
		}
		for(int i=0;i<u.u_deck.size();i++)
			if(u.u_deck.get(i).num.equals(num) || u.u_deck.get(i).type.equals(type) && u.u_deck.get(i).damage >= damage)
				count++;
		if (count == 0)
			return false;
		return true;
	}

	void draw_cards(User u)
	{
		int damage = OneCard.total_damage;
		int index;
		String type, num;
		if (damage == 0)
			damage++;
		for (int i=0;i<damage;i++)
		{
			if (OneCard.Cardset.size() == 0)
				OneCard.shuffle_card();
			index = OneCard.rand.nextInt(OneCard.Cardset.size() - 1);
			type = OneCard.Cardset.get(index).type;
			num = OneCard.Cardset.get(index).num;
			OneCard.Cardset.remove(index);
			u.u_deck.add(new Card(type, num));
		}
		OneCard.total_damage = 0;
		OneCard.after_atack = true;
		System.out.printf("%d??? ?????? ???????????????.\n", damage);
	}

	int can_return_this_card(Card c, User u)
	{
		int count=0;
		int i;
		int open_idx = OneCard.turned_in.size() - 1;
		int damage = OneCard.turned_in.get(open_idx).damage;
		for(i=0;i<u.u_deck.size();i++)
        {
            if (c.type.equals(u.u_deck.get(i).type) && c.num.equals(u.u_deck.get(i).num))
			{
			     count++;
				 break;
            }
        }
		if (OneCard.after_atack == true)
			damage = 0;
        if (c.type.equals("Joker") || OneCard.next_type.equals("Joker"))
				return i;
		if ((c.type.equals(OneCard.next_type) || c.num.equals(OneCard.turned_in.get(open_idx).num)) && c.damage >= damage && count == 1)
			return i;
		return -1;
	}

	void player_select(User u)
	{
		String type, num;
		int card_idx;
		Card do_return;
		while (true)
		{
			System.out.println("????????? ?????? ??????:");
			for (int i=0;i<u.u_deck.size();i++)
			{
                if (u.u_deck.get(i).type.equals("Joker"))
                    System.out.printf("%s\n",u.u_deck.get(i).type);
                else
                    System.out.printf("%s %s\n",u.u_deck.get(i).type, u.u_deck.get(i).num);
            }
			System.out.print("?????? ????????? ???????????????????:");
			type = OneCard.sc.next();
            if (type.equals("Joker") == false)
			{
                num = OneCard.sc.next();
			    do_return = new Card(type, num);
            }
            else
                do_return = new Card(type, type);
            card_idx = can_return_this_card(do_return, u);
			if (card_idx >= 0)
				break;
			else
				System.out.println("??? ??? ?????? ???????????????.");
		}
		if (OneCard.after_atack == true)
			OneCard.after_atack = false;
		type = u.u_deck.get(card_idx).type;
		num = u.u_deck.get(card_idx).num;
		u.u_deck.remove(card_idx);
		OneCard.turned_in.add(new Card(type, num));
		switch (num)
		{
			case "K":
			OneCard.turn += OneCard.vector * (-1);
			break;
			case "Q":
			OneCard.vector *= -1;
			break;
			case "J":
			OneCard.turn += OneCard.vector;
			break;
			case "7":
			System.out.print("?????? ??????(type)??? ???????????????:");
			OneCard.next_type = OneCard.sc.next();
			break;
		}
		if (num.equals("7") == false)
			OneCard.next_type = type;
		OneCard.total_damage += do_return.damage;
	}

	void computer_select(User u)
	{
		int i, card_idx;
		String type, num;
		for (i=0;i<u.u_deck.size();i++)
		{
            card_idx = can_return_this_card(u.u_deck.get(i), u);
            if (card_idx >= 0)
				break;
        }
		Card do_return = new Card(u.u_deck.get(i).type, u.u_deck.get(i).num);
		type = do_return.type;
		num = do_return.num;
		u.u_deck.remove(i);
		OneCard.turned_in.add(new Card(type, num));
		if (type.equals("Joker"))
            System.out.printf("%s??? %s??? ????????????.\n", u.name, type);
        else
            System.out.printf("%s??? %s %s??? ????????????.\n", u.name, type, num);
		if (OneCard.after_atack == true)
			OneCard.after_atack = false;
		switch (num)
		{
			case "K":
			OneCard.turn += OneCard.vector * (-1);
			break;
			case "Q":
			OneCard.vector *= -1;
			break;
			case "J":
			OneCard.turn += OneCard.vector;
			break;
			case "7":
			i = OneCard.rand.nextInt(4);
			if (i == 0)
				OneCard.next_type = "spade";
			else if (i == 1)
				OneCard.next_type = "dia";
			else if (i == 2)
				OneCard.next_type = "heart";
			else
			OneCard.next_type = "clover";
			break;
		}
		if (num.equals("7") == false)
			OneCard.next_type = type;
		else
			System.out.printf("%s??? ????????? ??? ??? ?????? ?????? ????????? %s??? ??????????????????.\n", u.name, OneCard.next_type);
		OneCard.total_damage += do_return.damage;
	}
}
