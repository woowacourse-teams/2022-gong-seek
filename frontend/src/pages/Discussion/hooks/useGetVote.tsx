import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getVoteItems, TVote } from '@/api/vote';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

const useVote = (articleId: string) => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		TVote,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['vote', `vote${articleId}`], () => getVoteItems(articleId), {
		retry: false,
		refetchOnWindowFocus: false,
	});
	const [totalCount, setTotalCount] = useState(0);
	const [isEmptyVote, setIsEmptyVote] = useState(false);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.voteItems.reduce((acc, cur) => acc + cur.amount, 0));
		}
	});

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			if (Number(error.response.data.errorCode) === 5004) {
				setIsEmptyVote(true);
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	return { data, isLoading, totalCount, isEmptyVote };
};

export default useVote;
