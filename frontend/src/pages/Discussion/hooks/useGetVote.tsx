import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';

import { getVoteItems } from '@/api/vote';
import { VoteItems } from '@/api/vote';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';

const useVote = (articleId: string) => {
	const { data, isLoading, isError, isSuccess, error } = useQuery<
		VoteItems[],
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(['vote', `vote${articleId}`], () => getVoteItems(articleId));
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		if (isSuccess) {
			setTotalCount(data.reduce((acc, cur) => acc + cur.count, 0));
		}
	});

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	return { data, isLoading, totalCount };
};

export default useVote;
