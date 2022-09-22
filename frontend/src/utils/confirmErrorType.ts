import { ErrorMessage } from '@/constants/ErrorMessage';

export const isAuthenticatedError = (errorCode: keyof typeof ErrorMessage) =>
	errorCode === '1001' ||
	errorCode === '1002' ||
	errorCode === '1003' ||
	errorCode === '1006' ||
	errorCode === '1008' ||
	errorCode === '1009' ||
	errorCode === '2001';

export const isInValidTokenError = (errorCode: keyof typeof ErrorMessage) =>
	errorCode === '1003' || errorCode === '1004';

export const isExpiredTokenError = (errorCode: keyof typeof ErrorMessage) => errorCode === '1005';

export const isInvalidRefreshTokenError = (errorCode: keyof typeof ErrorMessage) =>
	errorCode === '1011';

export const isAlreayLoginRefreshTokenError = (errorCode: keyof typeof ErrorMessage) =>
	errorCode === '1010';

export const isVoteError = (errorCode: keyof typeof ErrorMessage) =>
	errorCode === '5002' || errorCode === '5003' || errorCode === '5009';

export const isNotAccessVoteError = (errorCode: keyof typeof ErrorMessage) => errorCode === '5005';

export const isCommentError = (errorCode: keyof typeof ErrorMessage) => errorCode === '4001';

export const isServerError = (errorCode: keyof typeof ErrorMessage) => errorCode === '0000';

export const isNotFoundArticleError = (errorCode: keyof typeof ErrorMessage) =>
	errorCode === '3001';
