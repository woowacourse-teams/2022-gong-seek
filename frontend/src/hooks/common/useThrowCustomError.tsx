import { AxiosError } from 'axios';
import { Dispatch, SetStateAction, useEffect } from 'react';

import CustomError from '@/components/@helper/errorBoundary/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

const useThrowCustomError = (
	isError: boolean,
	error: AxiosError<{
		errorCode: keyof typeof ErrorMessage;
		message: string;
	}> | null,
	blockThrowError?: boolean,
	setBlockThrowError?: Dispatch<SetStateAction<boolean>>,
) => {
	useEffect(() => {
		if (blockThrowError && setBlockThrowError) {
			setBlockThrowError(false);
			return;
		}

		if (isError && error) {
			if (!error.response || typeof error.response.data === 'undefined') {
				throw new CustomError('0000', '네트워크에 문제가 발생하였습니다.');
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [error]);
};

export default useThrowCustomError;
