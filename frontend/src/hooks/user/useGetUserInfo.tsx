import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { AuthorType } from '@/api/article/articleType';
import { getUserInfo } from '@/api/user/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetUserInfo = () => {
	const { data, isSuccess, isError, isLoading, error } = useQuery<
		AuthorType,
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
