package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("I am a test class")
class CodeTest {

	@Test
	void test() {
		// assertThat(10 > 9).isTrue();
		assertThat(10 > 99).isFalse();
	}

	@Test
	void test2() {
		String s = null;
		assertThat(s).isNull();
	}

	@Test
	void test3() {
		int actual = 10;
		int expected = 10;
		// assertThat(actual).isEqualByComparingTo(expected);
		int y = 10;
		Integer ii = new Integer(y);
		// ii++;//unboxing
		int r = ii.intValue();
		r++;

		Integer t1 = 100;
		t1++;

		Integer g1 = 12;
		Integer g2 = 12;
		System.out.println(g1 == g2);

		Float f1 = 1.1f;
		Float f2 = 1.1f;
		System.out.println(f1 == f2);

		Boolean b1 = true;
		if (b1)// unboxing
			System.out.println("I am true");
		boolean h = b1.booleanValue();
		if (h)
			System.out.println("true....");

	}

	@Test
	void test4() {
		Student s = new Student("a");
		System.out.println(s.hashCode());
		System.out.println(System.identityHashCode(s));

		Student s1 = new Student("a");
		System.out.println(System.identityHashCode(s1));
		System.out.println(s1.hashCode());
		assertThat(s).isNotSameAs(s1);

	}

	@Test
	@DisplayName("both array should contain same elements")
	void shouldContainSameIntegers() {
//		int actual[] = new int[] { 1, 2, 3 };
//		int expected[] = new int[] { 1, 2, 3 };
		int actual[] = null;
		int expected[] = null;
		assertThat(actual).isEqualTo(expected);

	}

	private Object first;
	private Object second;
	private List<Object> list;

	private List<Object> slist;

	@BeforeEach
	void createCollection() {
		first = new Object();
		second = new Object();

		Object[] ar = { first, second };
		list = Arrays.asList(ar);
		Student ss[] = { new Student(10, "sayan"), new Student(20, "Arpan") };
		slist = Arrays.asList(ss);

		System.out.println("objects ready....");

	}

	@Test
	void test6() {
		// assertThat(list).hasSize(2);
		// assertThat(slist).hasSize(2);
		assertThat(list).doesNotContain(new Student(3, "ss"));

	}

	@Disabled
	@Test
	void test7() {
		// assertThat(list).hasSize(2);
		// assertThat(slist).hasSize(2);
		assertThat(list).doesNotContain(new Student(3, "ss"));

	}

	@RepeatedTest(value = 6)
	// @Test
	void test8() {
		// assertThat(list).hasSize(2);
		// assertThat(slist).hasSize(2);
		System.out.println("-----------------------running 5 times--------------");
		assertThat(10 > 9).isTrue();

	}

	@Test
	void shouldThrowCorrectException() {
		assertThatThrownBy(() -> {

			throw new NullPointerException();

		}).isExactlyInstanceOf(NullPointerException.class);

	}

	@Test
	void shouldThrowCustomCorrectException() {
		assertThatThrownBy(() -> {

			Logic.check(new Student(1, "ab"));

		}).isExactlyInstanceOf(MyOwnException.class);

	}

}
