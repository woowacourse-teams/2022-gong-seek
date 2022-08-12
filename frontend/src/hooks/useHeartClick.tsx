import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { deleteLikeArticle, postAddLikeArticle } from '@/api/like';
import { queryClient } from '@/index';

const useHeartClick = (articleId: string) => {
	const {
		data: postData,
		mutate: postMutate,
		isLoading: postIsLoading,
		isError: postIsError,
		error: postError,
		isSuccess: postIsSuccess,
	} = useMutation<AxiosResponse<{ isLike: boolean; likeCount: number }>, AxiosError, string>(
		`like${articleId}`,
		postAddLikeArticle,
	);
	const {
		data: deleteData,
		mutate: deleteMutate,
		isLoading: deleteIsLoading,
		isError: deleteIsError,
		error: deleteError,
		isSuccess: deleteIsSuccess,
	} = useMutation<AxiosResponse<{ isLike: boolean; likeCount: number }>, AxiosError, string>(
		`unlike${articleId}`,
		deleteLikeArticle,
	);

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
		postMutate(articleId);
	};

	const onUnlikeButtonClick = () => {
		deleteMutate(articleId);
	};

	return {
		postIsLoading,
		deleteIsLoading,
		onLikeButtonClick,
		onUnlikeButtonClick,
		isLike: postData?.data.isLike || deleteData?.data.isLike,
		likeCount: postData?.data.likeCount || deleteData?.data.likeCount,
	};
};

export default useHeartClick;
