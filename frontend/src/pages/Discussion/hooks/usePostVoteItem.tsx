import { queryClient } from '@/index';
import { checkVoteItems } from '@/api/vote';
import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

const usePostVoteItem = () => {
	const { isLoading, isError, error, mutate, isSuccess } = useMutation<
		unknown,
		AxiosError,
		{ articleId: string; voteId: string }
	>(checkVoteItems);

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries('vote');
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	const onChangeRadio = (name: string, idx: number) => {
		mutate({ articleId: name, voteId: String(idx) });
	};

	return { onChangeRadio, isLoading };
};

export default usePostVoteItem;
