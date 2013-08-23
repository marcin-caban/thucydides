package net.thucydides.junit.runners.integration;

import net.thucydides.core.annotations.Pending;
import net.thucydides.junit.annotations.TestData;
import net.thucydides.junit.runners.ThucydidesParameterizedRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

@RunWith(ThucydidesParameterizedRunner.class)
public class SimpleSuccessfulParametrizedTestSample {

	protected String userRole = "ROLE";

	public SimpleSuccessfulParametrizedTestSample(String userRole) {
		this.userRole = userRole;
	}
	
	@TestData(columnNames = "User")
	public static Collection<Object[]> testData(){
		return Arrays.asList(new Object[][]{{"STAFF"}, {"EYEDEMAND_ADMIN"}});
	}
	
	@Test
	public void test1(){
	}
	
	@Test
	public void test2(){
	}

	@Test
	public void test3(){
	}

	@Test
	public void test4(){
	}
	
	
	@Test
	public void test5(){
	}
	
	@Test
	public void test6(){
	}

}