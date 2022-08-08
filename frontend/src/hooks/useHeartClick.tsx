import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useMutation } from 'react-query';

import { deleteLikeArticle, postLikeArticle } from '@/api/like';
import { queryClient } from '@/index';

const useHeartClick = (isLike: boolean, articleId: string) => {
	const [isHeartClick, setIsHeartClick] = useState(isLike);
	const {
		mutate: postMutate,
		isLoading: postIsLoading,
		isError: postIsError,
		error: postError,
		isSuccess: postIsSuccess,
	} = useMutation<unknown, AxiosError, string>(`like${articleId}`, postLikeArticle);
	const {
		mutate: deleteMutate,
		isLoading: deleteIsLoading,
		isError: deleteIsError,
		error: deleteError,
		isSuccess: deleteIsSuccess,
	} = useMutation<unknown, AxiosError, string>(`unlike${articleId}`, deleteLikeArticle);

	useEffect(() => {
		if (postIsError) {
			throw new Error(postError.message);
		}

		if (deleteIsError) {
			throw new Error(deleteError.message);
		}
	}, [postIsError, deleteIsError]);

	useEffect(() => {
		if (deleteIsSuccess || postIsSuccess) {
			queryClient.invalidateQueries('all-articles');
		}
	});

	const onLikeButtonClick = () => {
		setIsHeartClick(true);
		postMutate(articleId);
	};

	const onUnlikeButtonClick = () => {
		setIsHeartClick(false);
		deleteMutate(articleId);
	};

	return { postIsLoading, deleteIsLoading, onLikeButtonClick, onUnlikeButtonClick, isHeartClick };
};

export default useHeartClick;
