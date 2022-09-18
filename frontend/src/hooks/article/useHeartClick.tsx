import { AxiosError, AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { useMutation } from 'react-query';

import { deleteLikeArticle, postAddLikeArticle } from '@/api/like';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

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
		isError: postIsError,
		error: postError,
		isSuccess: postIsSuccess,
	} = useMutation<
		AxiosResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(`like${articleId}`, postAddLikeArticle, {
		retry: 1,
	});
	const {
		mutate: deleteMutate,
		isError: deleteIsError,
		error: deleteError,
		isSuccess: deleteIsSuccess,
	} = useMutation<
		AxiosResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(`unlike${articleId}`, deleteLikeArticle, {
		retry: 1,
	});

	useEffect(() => {
		setIsLike(prevIsLike);
		setLikeCount(prevLikeCount);
	}, [prevIsLike, prevLikeCount]);

	useEffect(() => {
		if (postIsError) {
			if (!postError.response) {
				return;
			}
			throw new CustomError(postError.response.data.errorCode);
		}

		if (deleteIsError) {
			if (!deleteError.response) {
				return;
			}
			throw new CustomError(deleteError.response.data.errorCode);
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
		onLikeButtonClick,
		onUnlikeButtonClick,
		isLike,
		likeCount,
		postIsSuccess,
		deleteIsSuccess,
	};
};

export default useHeartClick;
