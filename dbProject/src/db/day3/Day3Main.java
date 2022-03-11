package db.day3;

import db.vo.Custom;

public class Day3Main {

	public static void main(String[] args) {
		Custom momo = CustomDao.selectOne("twice");
		System.out.println(momo);
		
		SingletonObj obj = SingletonObj.getInstance();
		obj.print();
		
		SingletonObj obj2 = SingletonObj.getInstance();
		System.out.println(obj == obj2);		// SingletonObj 객체는 오직 1개입니다. -> 참

		
	}

}
