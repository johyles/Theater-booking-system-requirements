package com.gy.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetRandom {
    String []code = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9"};

    public String randoms()
    {
        String rds = "";
        Random random = new Random();
        int k = 0;
        for(int i=0;i<6;i++)
        {
            k = random.nextInt(35);
            rds = rds + code[k];
        }
        return rds;
    }
}
