import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getAllArticle } from '@/api/article/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { categoryState } from '@/store/categoryState';
import { infiniteArticleResponse } from '@/types/articleResponse';

const useGetAllArticles = () => {
	const [currentCategory, setCurrentCategory] = useRecoilState(categoryState);
	const [sortIndex, setSortIndex] = useState('최신순');

	const { data, isLoading, isError, isSuccess, error, refetch, fetchNextPage } = useInfiniteQuery<
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
				cursorLikes: '',
			},
		}) => getAllArticle(pageParam),
		{
			getNextPageParam: (lastPage) => {
				const { hasNext, cursorId, cursorViews, cursorLikes } = lastPage;
				if (hasNext) {
					return {
						category: currentCategory,
						sort: sortIndex,
						cursorId,
						cursorViews,
						cursorLikes,
					};
				}
				return;
			},
			retry: 1,
			refetchOnWindowFocus: false,
		},
	);

	useThrowCustomError(isError, error);

	useEffect(() => {
		refetch();
	}, [currentCategory, sortIndex]);

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
