package com.springD.framework.exception;

/**
 * 处理业务逻辑异常
 * @Description: Service类中处理业务异常或者校验不通过等
 * @author zhaozy
 * @date 2014-11-14 下午06:10:21 
 * @version V1.0
 */
public class ServiceException extends RuntimeException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
}
