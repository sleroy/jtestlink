package com.tocea.corolla.users.api;

/**
 */
public interface IPasswordEncoder {


	String encodePassword(String _password);

	boolean isPasswordMatching(String _rawPassword, String _hashFunction);

}