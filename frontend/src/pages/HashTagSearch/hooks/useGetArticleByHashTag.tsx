import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { getArticleByHashTag } from '@/api/search';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { CommonArticleType } from '@/types/articleResponse';

const useGetArticleByHashTag = () => {
	const { data, isError, isLoading, isSuccess, error, mutate } = useMutation<
		{ articles: CommonArticleType[] },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(['search-result'], getArticleByHashTag);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	return { data, isLoading, isSuccess, mutate };
};

export default useGetArticleByHashTag;
