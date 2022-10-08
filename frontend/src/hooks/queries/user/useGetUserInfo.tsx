import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getUserInfo } from '@/api/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { Author } from '@/types/author';

const useGetUserInfo = () => {
	const { data, isSuccess, isError, isLoading, error } = useQuery<
		Author,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-info', getUserInfo, { retry: 1, refetchOnWindowFocus: false });

	useThrowCustomError(isError, error);

	return {
		data,
		isSuccess,
		isLoading,
	};
};

export default useGetUserInfo;
