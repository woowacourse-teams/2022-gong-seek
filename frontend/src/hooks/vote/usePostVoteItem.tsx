import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { checkVoteItems } from '@/api/vote';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { queryClient } from '@/index';

const usePostVoteItem = (articleId: string) => {
	const { error, mutate, isError, isSuccess, isLoading } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ articleId: string; voteItemId: string }
	>(checkVoteItems);

	useEffect(() => {
		if (isSuccess) {
			queryClient.invalidateQueries(['vote', `vote${articleId}`]);
		}
	}, [isSuccess]);

	useThrowCustomError(isError, error);

	const handleChangeVoteSelectButton = (articleId: string, idx: number) => {
		mutate({ articleId, voteItemId: String(idx) });
	};

	return { handleChangeVoteSelectButton, isLoading, isSuccess };
};

export default usePostVoteItem;
