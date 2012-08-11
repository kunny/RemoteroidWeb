package org.secmem.remoteroid.server.response;

public class Codes {
	
	public static class Result{
		public static final int OK = 0;
		public static final int FAILED = -1;
	}
	
	public static class Error{
		public static final int GENERAL = 0x000;
		
		public static class Account{
			public static final int DUPLICATE_EMAIL = 0x100;
			public static final int AUTH_FAILED = 0x101;
		}
		
		public static class Device{
			public static final int DUPLICATE_NAME = 0x200;
			public static final int DEVICE_NOT_FOUND = 0x201;
			
		}
	}
}
