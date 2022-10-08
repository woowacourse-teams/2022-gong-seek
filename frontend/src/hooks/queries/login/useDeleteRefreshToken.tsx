import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { deleteRefreshToken } from '@/api/login';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useDeleteRefreshToken = () => {
	const { isSuccess, isError, error, mutate } = useMutation<
		AxiosResponse<null>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('refresh-delete', deleteRefreshToken);

	useThrowCustomError(isError, error);

	return { isSuccess, mutate };
};

export default useDeleteRefreshToken;
