package com.zykspring.funnycore.message.test;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        MainTest mainTest = new MainTest();
        int[] i = {3,3,8,8};
        System.out.println(mainTest.judgePoint24(i));
    }

    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<Double>();
        for(int i:nums)
            list.add((double)i);

        return solve(list);
    }

    public boolean solve(List<Double> list){
        if(list.size() == 1){
            Double result = list.get(0);
            return result == 24;
        }

        for(int i = 0;i < list.size();i++){
            Double num1 = list.get(i);
            for(int j = 0;j < list.size();j++){
                List<Double> list1 = new ArrayList<Double>();
                if(i == j)
                    continue;
                Double num2 = list.get(j);
                for(int q = 0;q < list.size();q++){
                    if(q!=i && q!=j)
                        list1.add(list.get(q));
                }
                for(int z = 0;z < 4;z++){
                    if(z == 0) {//add
                        list1.add(num1+num2);
                    }
                    else if(z == 1){ //sub
                        list1.add(num1-num2>0?num1-num2:num2-num1);
                    }
                    else if(z == 2){//mul
                        list1.add(num1*num2);
                    }
                    else{
                        if(num2==0)
                            continue;

                        list1.add(num1/num2);
                    }

                    if(solve(list1))
                        return true;

                    list1.remove(list1.size()-1);
                }
            }
        }
        return false;
    }

}
