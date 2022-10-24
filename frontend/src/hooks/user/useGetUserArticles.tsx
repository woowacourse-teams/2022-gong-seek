import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { MyPageUserArticleResponseType } from '@/api/article/articleType';
import { getUserArticles } from '@/api/user/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetUserArticles = () => {
	const { data, isSuccess, isError, isLoading, error } = useQuery<
		MyPageUserArticleResponseType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-articles', getUserArticles, { retry: 1, refetchOnWindowFocus: false });

	useThrowCustomError(isError, error);

	return {
		data,
		isSuccess,
		isLoading,
	};
};

export default useGetUserArticles;
