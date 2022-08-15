import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useInfiniteQuery } from 'react-query';

import { getAllArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { infiniteArticleResponse } from '@/types/articleResponse';

const useGetAllArticles = () => {
	const [currentCategory, setCurrentCategory] = useState('question');
	const [sortIndex, setSortIndex] = useState('최신순');

	const { data, isError, isLoading, isSuccess, error, refetch, fetchNextPage } = useInfiniteQuery<
		infiniteArticleResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(
		['all-articles', currentCategory],
		({
			pageParam = {
				category: currentCategory,
				sort: sortIndex,
				cursorId: '',
				cursorViews: '',
			},
		}) => getAllArticle(pageParam),
		{
			getNextPageParam: (lastPage) => {
				const { hasNext, cursorId, cursorViews } = lastPage;
				if (hasNext) {
					return {
						category: currentCategory,
						sort: sortIndex,
						cursorId,
						cursorViews,
					};
				}
				return;
			},
			retry: false,
		},
	);

	useEffect(() => {
		refetch();
	}, [currentCategory, sortIndex]);

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

	return {
		data,
		isSuccess,
		isLoading,
		currentCategory,
		setCurrentCategory,
		sortIndex,
		setSortIndex,
		fetchNextPage,
	};
};

export default useGetAllArticles;
