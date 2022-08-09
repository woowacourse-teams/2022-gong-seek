import { ErrorMessage } from '@/constants/ErrorMessage';

class CustomError extends Error {
	public errorCode;

	constructor(code?: keyof typeof ErrorMessage, message?: string) {
		super(message);
		this.name = 'CustomError';
		this.errorCode = code;
	}
}

export default CustomError;
