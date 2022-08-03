import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { checkVoteItems } from '@/api/vote';
import { queryClient } from '@/index';

const usePostVoteItem = (articleId: string) => {
	const { isLoading, isError, error, mutate, isSuccess } = useMutation<
		unknown,
		AxiosError,
		{ articleId: string; voteId: string }
	>(checkVoteItems);

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries(`vote${articleId}`);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	const onChangeRadio = (articleId: string, idx: number) => {
		mutate({ articleId, voteId: String(idx) });
	};

	return { onChangeRadio, isLoading };
};

export default usePostVoteItem;
