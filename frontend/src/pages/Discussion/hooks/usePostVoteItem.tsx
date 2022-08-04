import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { checkVoteItems } from '@/api/vote';
import CustomError from '@/components/helper/CustomError';
import { queryClient } from '@/index';

const usePostVoteItem = () => {
	const { isLoading, isError, error, mutate, isSuccess } = useMutation<
		unknown,
		AxiosError<{ errorCode: string; message: string }>,
		{ articleId: string; voteId: string }
	>(checkVoteItems);

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries('vote');
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, [isError]);

	const onChangeRadio = (name: string, idx: number) => {
		mutate({ articleId: name, voteId: String(idx) });
	};

	return { onChangeRadio, isLoading };
};

export default usePostVoteItem;
