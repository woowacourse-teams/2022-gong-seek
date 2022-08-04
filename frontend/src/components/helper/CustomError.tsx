class CustomError extends Error {
	public errorCode;

	constructor(code?: string, message?: string) {
		super(message);
		this.name = 'CustomError';
		this.errorCode = code;
	}
}

export default CustomError;
