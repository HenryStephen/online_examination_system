package com.ssm.online_examination_system;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		int T = scanner.nextInt();
		long A;
		long B;
		long C;
		int lengtha,lengthb,length;
		while (T-->0) {
			C = 0;
			A=scanner.nextLong();
			B=scanner.nextLong();
			lengtha = Long.toBinaryString(A).length();
			lengthb = Long.toBinaryString(B).length();
			if(lengtha > lengthb){
				length = lengtha;
			}else{
				length = lengthb;
			}
			int ans = length;
			while (length>0) {
				if((A&1)==1&&(B&1)==1){
					C+=quickPower(2, length-ans);
				}
				A/=2;
				B/=2;
				ans--;
			}
			if(C==0){
				if((A&B)==0){
					System.out.println(1);
				}else{
					System.out.println(0);
				}
			}else{
				System.out.println(C);
			}
		}
	}
	private static long quickPower(long x, long y) {
		long ans=1;
		while(y>0){
			if((y&1)==1)
				ans=(ans*x);
			y>>=1;
			x=(x*x);
		}
		return ans;
	}
}