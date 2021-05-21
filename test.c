package project;

public class test
{
    static void exe(exercise ex)
    {
        ex.play();
    }

    static exercise select(int i)
    {
        exercise ex = new exercise();
        switch(i)
        {
            case 0:
            ex.s = "play exercise";
            break;
            case 1:
            ex.s = "play baseball";
            break;
            case 2:
            ex.s = "play soccer";
            break;
            case 3:
            ex.s = "play basketball";
            break;
        }
        return ex;
    }


    public static void main(String[] args)
    {
        exercise[] ex_list = new exercise[4];
        for(int i=0;i<4;i++)
        {
            ex_list[i] = select(i);
            exe(ex_list[i]);
        }
    }

}

class exercise
{
    String s;

    void play()
    {
        System.out.println(s);
    }
}
