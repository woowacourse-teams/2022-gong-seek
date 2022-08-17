import { AxiosError, AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { useMutation } from 'react-query';

import { deleteLikeArticle, postAddLikeArticle } from '@/api/like';

const useHeartClick = ({
	prevIsLike,
	prevLikeCount,
	articleId,
}: {
	prevIsLike: boolean;
	prevLikeCount: number;
	articleId: string;
}) => {
	const [isLike, setIsLike] = useState(prevIsLike);
	const [likeCount, setLikeCount] = useState(prevLikeCount);
	const {
		mutate: postMutate,
		isLoading: postIsLoading,
		isError: postIsError,
		error: postError,
		isSuccess: postIsSuccess,
	} = useMutation<AxiosResponse, AxiosError, string>(`like${articleId}`, postAddLikeArticle);
	const {
		mutate: deleteMutate,
		isLoading: deleteIsLoading,
		isError: deleteIsError,
		error: deleteError,
		isSuccess: deleteIsSuccess,
	} = useMutation<AxiosResponse, AxiosError, string>(`unlike${articleId}`, deleteLikeArticle);

	useEffect(() => {
		setIsLike(prevIsLike);
		setLikeCount(prevLikeCount);
	}, [prevIsLike, prevLikeCount]);

	useEffect(() => {
		if (postIsError) {
			throw new Error(postError.message);
		}

		if (deleteIsError) {
			throw new Error(deleteError.message);
		}
	}, [postIsError, deleteIsError]);

	useEffect(() => {
		if (deleteIsSuccess) {
			setIsLike(false);
			setLikeCount((prevLikeCount) => prevLikeCount - 1);
		}
	}, [deleteIsSuccess]);

	useEffect(() => {
		if (postIsSuccess) {
			setIsLike(true);
			setLikeCount((prevLikeCount) => prevLikeCount + 1);
		}
	}, [postIsSuccess]);

	const onLikeButtonClick = (e: React.MouseEvent<SVGElement>) => {
		e.stopPropagation();
		postMutate(articleId);
	};

	const onUnlikeButtonClick = (e: React.MouseEvent<SVGElement>) => {
		e.stopPropagation();
		deleteMutate(articleId);
	};

	return {
		postIsLoading,
		deleteIsLoading,
		onLikeButtonClick,
		onUnlikeButtonClick,
		isLike,
		likeCount,
	};
};

export default useHeartClick;
